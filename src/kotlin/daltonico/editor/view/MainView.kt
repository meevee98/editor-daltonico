package daltonico.editor.view

import daltonico.editor.configs.Configs
import daltonico.editor.viewmodel.LanguageViewModel
import daltonico.editor.viewmodel.MainViewModel
import javafx.scene.input.KeyCombination
import tornadofx.*

class MainView : View() {
    val vm: MainViewModel by inject()
    val lang: LanguageViewModel by inject()

    init {
        titleProperty.bind(lang.title)
    }

    override val root = borderpane {
        top = menubar {
            menu {
                textProperty().bind(lang.file)
                item(lang.openFile, KeyCombination.valueOf("Ctrl + O"))
                item(lang.saveFile, KeyCombination.valueOf("Ctrl + S"))
            }
            menu {
                textProperty().bind(lang.image)
                item(lang.grayScale)
                item(lang.blackWhite)
                menu {
                    textProperty().bind(lang.filters)
                    item(lang.filter1)
                    item(lang.filter2)
                }
                item(lang.histogram)
            }
            menu {
                textProperty().bind(lang.options)
                menu {
                    textProperty().bind(lang.languages)
                    for (language in Configs.availableLanguages()) {
                        item(language) {
                            action {
                                vm.changeLanguage(language)
                                lang.setAllValues(Configs.lang)
                            }
                        }
                    }
                }
            }
        }
        center = imageview {

        }
    }
}
