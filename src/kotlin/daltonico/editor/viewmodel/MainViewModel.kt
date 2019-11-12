package daltonico.editor.viewmodel

import daltonico.editor.configs.Configs
import daltonico.editor.view.MainView
import javafx.stage.FileChooser
import javafx.stage.Stage
import tornadofx.ViewModel

class MainViewModel : ViewModel() {
    private val fileChooser = FileChooser()

    fun openFile() {
        val stage = find(MainView::class).primaryStage
        fileChooser.showOpenDialog(stage)?.let {
            try {
                fileChooser.initialDirectory = it.parentFile
                println("TODO: open ${it.absolutePath}")
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
