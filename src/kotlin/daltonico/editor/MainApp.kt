package daltonico.editor

import javafx.stage.Stage
import tornadofx.App
import daltonico.editor.view.MainView
import javafx.scene.image.Image

class MainApp : App(MainView::class) {
    override fun start(stage: Stage) {
        stage.minHeight = 600.0
        stage.minWidth = 800.0
        stage.icons += Image("file:src/resources/monitor.png")
        super.start(stage)
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            launch(MainApp::class.java)
        }
    }
}
