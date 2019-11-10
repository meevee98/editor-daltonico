package daltonico.editor.configs

import com.fasterxml.jackson.databind.ObjectMapper
import daltonico.editor.language.EnUs
import daltonico.editor.language.Language
import daltonico.editor.language.PtBr
import java.io.File

object Configs {
    val configFile = "src\\references\\config.json"

    private val languages = mapOf(
        "PtBr" to PtBr(),
        "EnUs" to EnUs()
    )
    lateinit var lang: Language private set
    private val defaultLang = PtBr()

    init {
        readConfig()
    }

    fun availableLanguages(): List<String> {
        return languages.keys.toList()
    }

    fun changeLanguage(lang: String): Boolean {
        languages[lang]?.let {
            this.lang = it
            updateConfigFile()
            return true
        }
        return false
    }

    private fun readConfig() {
        val file = File(configFile)
        if (!file.parentFile.exists()) {
            file.parentFile.mkdirs()
        }

        val mapper = ObjectMapper()

        mapper.readTree(file).let {
            lang = it?.get("lang")?.asText()?.run {
                languages[this]
            } ?: defaultLang
        }
    }

    private fun updateConfigFile() {
        val file = File(configFile)
        if (!file.parentFile.exists()) {
            file.parentFile.mkdirs()
        }

        val mapper = ObjectMapper()
        val config = mapper.createObjectNode()

        config.put("lang", lang.name)

        mapper.writer().writeValue(file, config)
    }
}
