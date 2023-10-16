package main
/*
Selects a given number of items
- Performs the quiz:
    - Shows the questions one by one to the user and requests a response.
    - Evaluate the response
- Shows the final result in the following format: Correct answers/Total number of answers
*/
class ItemController(val itemService: ItemService ) {
    fun quiz(nrOfQuestions: Int): Unit {
        val items = itemService.selectRandomItems(nrOfQuestions)
        var score = 0
        for (item in items) {
            println("${items.indexOf(item)+1}. Question: ${item.question}")
            for (i in 0 until item.answers.size) {
                println("${i+1}. ${item.answers[i]}")
            }
            println("Enter your answer (1-${item.answers.size}): " )
            val response = readln().toInt()
            if(response < 1 || response > item.answers.size) {
                println("Invalid response")
            }
            else if (response == item.correct) {
                println("Correct!")
                score++
            } else {
                println("Wrong! Correct answer is: ${item.answers[item.correct-1]}")
            }
            println()
        }
        println("Score: $score/$nrOfQuestions")
        println()
    }
}