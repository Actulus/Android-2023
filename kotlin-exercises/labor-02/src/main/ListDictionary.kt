package main;

import java.io.File

object ListDictionary: IDictionary {
    private val words: MutableList<String> = mutableListOf()

    init {
        File(IDictionary.DICTIONARY_NAME).forEachLine {
            add(it)
        }
    }
    override fun size(): Int {
        return words.size
    }
    override fun find(word: String): Boolean {
        return words.contains(word)
    }
    override fun add(word: String): Boolean {
        return words.add(word)
    }
}
