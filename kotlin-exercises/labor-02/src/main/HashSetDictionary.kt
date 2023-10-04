package main

import java.io.File

object HashSetDictionary: IDictionary {
    private val words: HashSet<String> = HashSet()
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