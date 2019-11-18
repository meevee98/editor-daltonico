package daltonico.editor.viewmodel

import daltonico.editor.configs.Configs
import daltonico.editor.enum.SaveConfirmationDialogResult
import daltonico.editor.view.MainView
import daltonico.editor.view.SaveConfirmationView
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleDoubleProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.scene.image.Image
import javafx.scene.image.WritableImage
import javafx.scene.layout.Pane
import javafx.scene.paint.Color
import javafx.stage.FileChooser
import javafx.stage.Modality
import tornadofx.ViewModel
import tornadofx.getValue
import tornadofx.setValue
import java.io.File

class MainViewModel : ViewModel() {
    private val fileChooser = FileChooser()
    private var defaultImage = Image("file:src/resources/default_img.png")

    val loadedImageProperty = SimpleObjectProperty<Image>()
    private var loadedImage: Image by loadedImageProperty

    val imageProperty = SimpleObjectProperty<Image>()
    private var image: Image by imageProperty

    val disableSave = SimpleBooleanProperty(true)
    val disableFilters = SimpleBooleanProperty(true)

    val viewWidth = SimpleDoubleProperty(0.0)
    val viewHeight = SimpleDoubleProperty(0.0)

    init {
        loadedImageProperty.addListener { observable, oldValue, newValue -> 
            image = newValue
        }
        loadedImage = defaultImage
        bindFileChooserDirectory()
        bindDisableSave()
        bindDisableFilters()
    }

    // region Databind

    fun bindSize(view: Pane) {
        // TODO
        viewWidth.set(800.0)
        viewHeight.set(600.0)
    }

    private fun bindDisableSave() {
        imageProperty.addListener { observable, oldValue, newValue ->
            disableSave.set(newValue == loadedImage)
        }
    }

    private fun bindDisableFilters() {
        imageProperty.addListener { observable, oldValue, newValue ->
            disableFilters.set(newValue == defaultImage)
        }
    }

    private fun bindFileChooserDirectory() {
        Configs.defaultDirectory?.let {
            val directory = File(it)

            if (directory.exists() && directory.isDirectory) {
                fileChooser.initialDirectory = directory
            }
        }

        fileChooser.initialDirectoryProperty().addListener { observable, oldValue, newValue ->
            if (newValue.isDirectory) {
                Configs.changeDefaultDirectory(newValue.absolutePath)
            }
        }
    }

    // endregion

    fun openFile() {
        if (confirmSave()) {
            val stage = find(MainView::class).primaryStage
            fileChooser.apply {
                extensionFilters.clear()
                extensionFilters.add(
                    FileChooser.ExtensionFilter(
                        "Arquivos de imagem",
                        "*.jpg", "*.jpeg",
                        "*.png",
                        "*.gif"
                    )
                )
            }.showOpenDialog(stage)?.let {
                try {
                    fileChooser.initialDirectory = it.parentFile
                    loadedImage = Image(it.toURI().toString())
                } catch (e: Exception) {
                    throw Exception("${Configs.lang["something_went_wrong"]} ${e.localizedMessage}")
                }
            }
        }
    }

    fun saveFile() {
        val stage = find(MainView::class).primaryStage
        fileChooser.apply {
            extensionFilters.clear()
            extensionFilters.add(
                FileChooser.ExtensionFilter(
                    "Arquivos de imagem (*.png)",
                    "*.png"
                )
            )
        }.showSaveDialog(stage)?.let {
            try {
                fileChooser.initialDirectory = it.parentFile
                println("TODO: save ${it.absolutePath}")
            }
            catch (e: Exception) {
                throw Exception("${Configs.lang["something_went_wrong"]} ${e.localizedMessage}")
            }
        }
    }

    fun closeFile() {
        if (confirmSave()) {
            loadedImage = defaultImage
        }
    }

    private fun confirmSave(): Boolean {
        if (!disableSave.get()) {
            val dialog = find<SaveConfirmationView>()
            dialog.openModal(modality = Modality.WINDOW_MODAL, block = true)

            val result = dialog.getResult()
            if (result == SaveConfirmationDialogResult.CANCEL) {
                return false
            }

            if (result == SaveConfirmationDialogResult.SAVE) {
                saveFile()
            }
        }
        return true
    }

    fun grayScale() {
        val h = image.height.toInt()
        val w = image.width.toInt()

        val writableImage = WritableImage(w, h)
        val reader = image.pixelReader
        val writer = writableImage.pixelWriter

        for (x in 0 until w) {
            for (y in 0 until h) {
                val color = reader.getColor(x, y)
                writer.setColor(x, y, color.grayscale())
            }
        }

        image = writableImage
    }

    fun binaryScale() {
        val h = image.height.toInt()
        val w = image.width.toInt()

        val writableImage = WritableImage(w, h)
        val reader = image.pixelReader
        val writer = writableImage.pixelWriter

        for (x in 0 until w) {
            for (y in 0 until h) {
                val color = reader.getColor(x, y)

                val newColor = if (color.brightness > 0.5) {
                    Color(1.0, 1.0, 1.0, color.opacity)
                } else {
                    Color(0.0, 0.0, 0.0, color.opacity)
                }

                writer.setColor(x, y, newColor)
            }
        }

        image = writableImage
    }

    fun showHistogram() {

    }

    // region Filters

    fun applyProjectorFilter() {
        val h = image.height.toInt()
        val w = image.width.toInt()

        val writableImage = WritableImage(w, h)
        val reader = image.pixelReader
        val writer = writableImage.pixelWriter

        for (y in 0 until h) {
            for (x in 0 until w) {
                val color = reader.getColor(x, y)
                writer.setColor(x, y, Color(
                    0.0,
                    color.green,
                    color.blue,
                    color.opacity
                ))
            }
        }

        image = writableImage
    }

    fun applyInvertFilter() {
        val h = image.height.toInt()
        val w = image.width.toInt()

        val writableImage = WritableImage(w, h)
        val reader = image.pixelReader
        val writer = writableImage.pixelWriter

        for (y in 0 until h) {
            for (x in 0 until w) {
                val color = reader.getColor(x, y)
                writer.setColor(x, y, Color(
                    1 - color.red,
                    1 - color.green,
                    1 - color.blue,
                    color.opacity
                ))
            }
        }

        image = writableImage
    }

    // endregion

    fun changeLanguage(lang: String?) {
        lang?.let {
            Configs.changeLanguage(it)
        }
    }
}
