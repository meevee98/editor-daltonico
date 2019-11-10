package daltonico.editor.viewmodel

import daltonico.editor.configs.Configs
import daltonico.editor.language.Language
import javafx.beans.property.SimpleStringProperty
import tornadofx.ViewModel

class LanguageViewModel: ViewModel() {    
    val title = SimpleStringProperty()

    val file = SimpleStringProperty()
    val openFile = SimpleStringProperty()
    val saveFile = SimpleStringProperty()

    val image = SimpleStringProperty()
    val grayScale = SimpleStringProperty()
    val blackWhite = SimpleStringProperty()
    val filters = SimpleStringProperty()
    val filter1 = SimpleStringProperty()
    val filter2 = SimpleStringProperty()
    val histogram = SimpleStringProperty()

    val options = SimpleStringProperty()
    val languages = SimpleStringProperty()

    init {
        setAllValues(Configs.lang)
    }
    
    fun setAllValues(language: Language) {
        title.set(language["title"])

        file.set(language["file"])
        openFile.set(language["open_file"])
        saveFile.set(language["save_file"])

        image.set(language["image"])
        grayScale.set(language["gray_scale"])
        blackWhite.set(language["black_white"])
        filters.set(language["filters"])
        filter1.set(language["filter_1"])
        filter2.set(language["filter_2"])
        histogram.set(language["histogram"])

        options.set(language["options"])
        languages.set(language["languages"])
    }
}