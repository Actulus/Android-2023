package main

import java.io.File

/* data access layer with some CRUD operations; for simplicity initialize items here*/

class ItemRepository {
    val items: MutableList<Item> = mutableListOf<Item>()
    init{
        //read data from file in the format of 1 question, 4 answers, number of the correct answer line by line
        try {
            val file = File("E:\\Egyetem\\5. felev\\Android_programozas\\Android-2023\\kotlin-exercises\\labor-03\\src\\main\\quizzes.txt")
            val lines = file.useLines { it.toList() }

            // Initialize variables to store the question, answers, and correct answer
            var question: String? = null
            val answers = mutableListOf<String>()
            var correctAnswer: Int = -1
            var emptyLine = false

            for ((lineNumber, line) in lines.withIndex()) {
                if (question == null) {
                    question = line
                } else if (answers.size < 4) {
                    answers.add(line)
                } else if (correctAnswer == -1) {
                    try {
                        correctAnswer = line.toInt()
                    } catch (e: NumberFormatException) {
                        println("Error parsing correct answer on line $lineNumber: $line")
                    }
                } else if (line.isBlank()) {
                    // An empty line indicates the end of a question-answer set
                    emptyLine = true
                }

                if (emptyLine || lineNumber == lines.size - 1) {
                    items.add(Item(question, answers.toList(), correctAnswer))
                    question = null
                    answers.clear()
                    correctAnswer = -1
                    emptyLine = false
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun save(item: Item) {
        items.add(item)
    }

    fun randomItem(): Item {
        return items.random()
    }

    fun size(): Int {
        return items.size
    }
}