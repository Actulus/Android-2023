package main

interface IDictionary {
    companion object {
        const val DICTIONARY_NAME = "E:\\Egyetem\\5. felev\\Android_programozas\\Android-2023\\kotlin-exercises\\labor-02\\src\\main\\dict.txt"
    }
    fun size(): Int
    fun find(word: String): Boolean
    fun add(word: String): Boolean
}