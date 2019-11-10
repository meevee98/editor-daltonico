package daltonico.editor.language

abstract class Language {
    abstract val dictionary: Map<String, String>
    val name = this.javaClass.simpleName

    operator fun get(key: String): String {
        return dictionary[key] ?: key
    }
}
