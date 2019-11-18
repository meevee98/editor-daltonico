package daltonico.editor.viewmodel

import daltonico.editor.configs.Configs
import daltonico.editor.language.Language
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.StringProperty
import tornadofx.ViewModel
import tornadofx.getValue
import tornadofx.setValue
import tornadofx.toProperty

class LanguageViewModel: ViewModel() {
    private val dictionaryProperty = SimpleObjectProperty<HashMap<String, StringProperty>>()
    var dictionary: HashMap<String, StringProperty> by dictionaryProperty

    init {
        dictionary = hashMapOf()
        setAllValues(Configs.lang)
    }

    fun setAllValues(language: Language) {
        language.dictionary.forEach { (key, value) ->
            if (!dictionary.containsKey(key)) {
                dictionary[key] = value.toProperty()
            }
            else {
                dictionary[key]?.set(value)
            }
        }
    }

    operator fun get(key: String): StringProperty {
        return dictionary[key] ?: key.toProperty()
    }
}