package daltonico.editor.configs

import com.fasterxml.jackson.databind.ObjectMapper
import daltonico.editor.language.EnUs
import daltonico.editor.language.Language
import daltonico.editor.language.PtBr
import daltonico.editor.language.ZhCn
import java.io.File

object Configs {
    private val configFile = "src\\resources\\config.json"

    private val languages = mapOf(
        "PtBr" to PtBr(),
        "EnUs" to EnUs(),
        "ZhCn" to ZhCn()
    )
    lateinit var lang: Language internal set
    private val defaultLang = PtBr()

    var defaultDirectory: String? = null
        internal set

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

    fun changeDefaultDirectory(directory: String): Boolean {
        defaultDirectory = directory
        updateConfigFile()
        return true
    }

    private fun readConfig() {
        val file = File(configFile)
        if (!file.parentFile.exists()) {
            file.parentFile.mkdirs()
        }
        if (!file.exists()) {
            file.createNewFile()
        }

        val mapper = ObjectMapper()

        mapper.readTree(file).let {
            lang = it?.get("lang")?.asText()?.run {
                languages[this]
            } ?: defaultLang
            defaultDirectory = it?.get("directory")?.asText()
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
        defaultDirectory?.let {
            config.put("directory", it)
        }

        mapper.writer().writeValue(file, config)
    }
}
