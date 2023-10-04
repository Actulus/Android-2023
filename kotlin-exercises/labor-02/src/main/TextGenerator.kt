package main

import java.util.*
import kotlin.collections.HashMap

class TextGenerator() {
    private val wordPairs: MutableMap<Pair<String, String>, MutableList<String>> = LinkedHashMap()

    fun learnWords(text: String){
        val words = text.split("\\s+".toRegex()) // Split input text into words
//        println(words)
        if (words.size < 3) {
            throw IllegalArgumentException("Input text should contain at least three words.")
        }

        for (i in 0 until words.size - 2) {
            val prefix = Pair(words[i], words[i + 1])
            val postfix = words[i + 2]

            wordPairs.computeIfAbsent(prefix) { mutableListOf() }.add(postfix)
//            println(wordPairs)
        }
    }

    fun generateText(): List<String> {
        if (wordPairs.isEmpty()) {
            throw IllegalStateException("TextGenerator has not been trained.")
        }

        val random = Random()
        val generatedLines = mutableListOf<String>()
        val generatedWords = mutableListOf<String>()

        // Ensure generatedWords is populated with the first two words of the original text
        var initialPrefix = wordPairs.keys.random()
        generatedWords.add(initialPrefix.first)
        generatedWords.add(initialPrefix.second)
        generatedLines.add("1. ${generatedWords.joinToString(" ")} (the first two words)")

        // Generate text until the last word pair of the original text is reached
        var lineCounter = 2 // Start counting from line 2
        while (wordPairs.containsKey(initialPrefix)) {
            val possiblePostfixes = wordPairs[initialPrefix]!!
            val randomIndex = random.nextInt(possiblePostfixes.size)
            val chosenPostfix = possiblePostfixes[randomIndex]

            generatedWords.add(chosenPostfix)
            initialPrefix = Pair(initialPrefix.second, chosenPostfix)

            val generatedLine = "\n$lineCounter. ${generatedWords.joinToString(" ")}"
            generatedLines.add(generatedLine)
            lineCounter++
        }

        return generatedLines
    }

    fun printWordsPairs(){
        println("Prefixes and postfixes:")
        wordPairs.forEach() { (prefix, postfixes) -> println("$prefix -> $postfixes") }
        println()
    }
}