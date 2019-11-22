package daltonico.editor.view

import daltonico.editor.configs.Configs
import daltonico.editor.viewmodel.LanguageViewModel
import daltonico.editor.viewmodel.MainViewModel
import javafx.scene.input.KeyCombination
import javafx.scene.layout.Priority
import tornadofx.*

class MainView : View() {
    val vm: MainViewModel by inject()
    val lang: LanguageViewModel by inject()

    init {
        titleProperty.bind(lang["title"])
    }

    override val root = vbox {
        menubar {
            menu {
                textProperty().bind(lang["file"])
                item(lang["open_file"], KeyCombination.valueOf("Ctrl + O")) {
                    action { vm.openFile() }
                }
                item(lang["save_file"], KeyCombination.valueOf("Ctrl + S")) {
                    action { vm.saveFile() }
                    disableWhen(vm.disableSave)
                }
                item(lang["close_file"], KeyCombination.valueOf("Ctrl + W")) {
                    action { vm.closeFile() }
                    disableWhen(vm.disableFilters)
                }
            }
            menu {
                textProperty().bind(lang["image"])
                item(lang["gray_scale"], KeyCombination.valueOf("Ctrl + U")) {
                    action { vm.grayScale() }
                    disableWhen(vm.disableFilters)
                }
                item(lang["black_white"], KeyCombination.valueOf("Ctrl + B")) {
                    action { vm.binaryScale() }
                    disableWhen(vm.disableFilters)
                }
                menu {
                    textProperty().bind(lang["filters"])
                    item(lang["filter_1"], KeyCombination.valueOf("Ctrl + R")) {
                        action { vm.applyProjectorFilter() }
                        disableWhen(vm.disableFilters)
                    }
                    item(lang["filter_2"], KeyCombination.valueOf("Ctrl + I")) {
                        action { vm.applyInvertFilter() }
                        disableWhen(vm.disableFilters)
                    }
                }
                item(lang["histogram"], KeyCombination.valueOf("Ctrl + H")) {
                    action { vm.showHistogram() }
                    disableWhen(vm.disableFilters)
                }
            }
            menu {
                textProperty().bind(lang["options"])
                menu {
                    textProperty().bind(lang["languages"])
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
        borderpane {
            vm.bindSize(this)
            vgrow = Priority.ALWAYS
            center = imageview(vm.imageProperty) {
                isPreserveRatio = true
                fitHeightProperty().bind(vm.viewHeight)
                fitWidthProperty().bind(vm.viewWidth)
            }
        }
    }
}
