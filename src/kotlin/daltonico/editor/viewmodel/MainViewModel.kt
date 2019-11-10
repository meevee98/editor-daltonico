package daltonico.editor.viewmodel

import daltonico.editor.configs.Configs
import tornadofx.ViewModel

class MainViewModel : ViewModel() {
    fun changeLanguage(lang: String?) {
        lang?.let {
            Configs.changeLanguage(it)
        }
    }
}
