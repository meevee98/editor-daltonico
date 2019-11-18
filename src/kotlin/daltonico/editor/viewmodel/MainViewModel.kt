package daltonico.editor.viewmodel

import daltonico.editor.configs.Configs
import daltonico.editor.view.MainView
import javafx.beans.property.SimpleDoubleProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.scene.image.Image
import javafx.scene.image.WritableImage
import javafx.scene.layout.Pane
import javafx.scene.paint.Color
import javafx.stage.FileChooser
import tornadofx.ViewModel
import tornadofx.getValue
import tornadofx.setValue

class MainViewModel : ViewModel() {
    private val fileChooser = FileChooser().apply {
        extensionFilters.add(
            FileChooser.ExtensionFilter(
                "Arquivos de imagem",
                "*.jpg", "*.jpeg", "*.jpe", "*.jfif",
                "*.tif", "*.tiff",
                "*.png",
                "*.gif",
                "*.ico",
                "*.heic",
                "*.webp"
            )
        )
    }

    val loadedImageProperty = SimpleObjectProperty(Image("file:src/resources/default_img.png"))
    private var loadedImage: Image by loadedImageProperty

    val imageProperty = SimpleObjectProperty<Image>()
    private var image: Image by imageProperty

    val viewWidth = SimpleDoubleProperty(0.0)
    val viewHeight = SimpleDoubleProperty(0.0)

    init {
        image = loadedImage
    }

    fun bindSize(view: Pane) {
        // TODO
        viewWidth.set(800.0)
        viewHeight.set(600.0)
    }

    fun openFile() {
        val stage = find(MainView::class).primaryStage
        fileChooser.showOpenDialog(stage)?.let {
            try {
                fileChooser.initialDirectory = it.parentFile
                loadedImage = Image(it.toURI().toString())
                image = loadedImage
            }
            catch (e: Exception) {
                println("${Configs.lang["something_went_wrong"]} ${e.localizedMessage}")
            }
        }
    }

    fun saveFile() {
        val stage = find(MainView::class).primaryStage
        fileChooser.showSaveDialog(stage)?.let {
            try {
                fileChooser.initialDirectory = it.parentFile
                println("TODO: save ${it.absolutePath}")
            }
            catch (e: Exception) {
                println("${Configs.lang["something_went_wrong"]} ${e.localizedMessage}")
            }
        }
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
