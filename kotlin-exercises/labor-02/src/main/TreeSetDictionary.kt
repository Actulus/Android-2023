package main

import java.io.File
import java.util.*

object TreeSetDictionary: IDictionary {
    private val words: TreeSet<String> = TreeSet()
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