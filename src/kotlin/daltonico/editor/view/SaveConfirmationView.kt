package daltonico.editor.view

import daltonico.editor.configs.Configs
import daltonico.editor.enum.SaveConfirmationDialogResult
import javafx.geometry.Pos
import javafx.scene.text.Font
import tornadofx.*

class SaveConfirmationView : View(Configs.lang["save_file"]) {
    private lateinit var result: SaveConfirmationDialogResult
    val lang = Configs.lang

    override val root = vbox {
        padding = insets(10.0)
        spacing = 10.0
        alignment = Pos.CENTER

        label(lang["save_changes"]) {
            padding = insets(10.0, 0.0)
            font = Font.font(16.0)
        }
        hbox {
            spacing = 10.0
            alignment = Pos.CENTER

            button(lang["save"]) {
                action { setResult(SaveConfirmationDialogResult.SAVE) }
            }
            button(lang["dont_save"]) {
                action { setResult(SaveConfirmationDialogResult.DONT_SAVE) }
            }
            button(lang["cancel"]) {
                action { setResult(SaveConfirmationDialogResult.CANCEL) }
            }
        }
    }

    private fun setResult(value: SaveConfirmationDialogResult) {
        result = value
        close()
    }

    fun getResult(): SaveConfirmationDialogResult {
        return result
    }
}
