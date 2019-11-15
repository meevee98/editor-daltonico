package daltonico.editor.viewmodel

import daltonico.editor.configs.Configs
import daltonico.editor.view.MainView
import javafx.beans.property.SimpleDoubleProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.scene.image.Image
import javafx.scene.layout.Pane
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

    val viewWidth = SimpleDoubleProperty(0.0)
    val viewHeight = SimpleDoubleProperty(0.0)

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

    }

    fun binaryScale() {

    }

    fun showHistogram() {

    }

    // region Filters

    fun applyFilter1() {

    }

    fun applyFilter2() {

    }

    // endregion

    fun changeLanguage(lang: String?) {
        lang?.let {
            Configs.changeLanguage(it)
        }
    }
}
