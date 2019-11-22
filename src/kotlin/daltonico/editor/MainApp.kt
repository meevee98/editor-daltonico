package daltonico.editor

import javafx.stage.Stage
import tornadofx.App
import daltonico.editor.view.MainView
import javafx.scene.Scene
import javafx.scene.image.Image
import javafx.stage.StageStyle
import tornadofx.UIComponent

class MainApp : App(MainView::class) {
    override fun start(stage: Stage) {
        stage.minWidth = 816.0
        stage.minHeight = 664.0
        stage.icons += Image("file:src/resources/icon.png")
        super.start(stage)
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            launch(MainApp::class.java)
        }
    }
}
