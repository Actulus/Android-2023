package main


//Define an extension function that prints the monogram of a String containing the firstname and lastname
// this akire meg lesz hivva
fun String.nameMonogram(): String = this.split(" ").map { it[0] }.joinToString("") // a map utan a listaban van ket karakter, ezeket joinoljuk ossze

//Define a compact extension function that returns the elements of a strings’ list joined by a given separator
fun List<String>.joinByGivenSeparator(separator: String): String = this.joinToString(separator = separator) // a join utSeparator

//Define a compact extension function for a strings’ list that returns the longest string
fun List<String>.longestString(): String? = this.maxByOrNull { it.length }

fun extra() {
//    val inputText: String = "Now is not the time for sleep, now is the time for party!"
    val inputText: String = "In the heart of the bustling city, where skyscrapers touch the sky, this is not the place for tranquility; this is the place for endless activity and excitement!"
    val textGenerator = TextGenerator()
    textGenerator.learnWords(inputText)
    textGenerator.printWordsPairs()
    println(textGenerator.generateText())
}

fun main() {
   /* //PROBLEM 1
//    val dict: IDictionary = ListDictionary()
//    val dict: IDictionary = DictionaryProvider().createDictionary(DictionaryType.ARRAY_LIST)
    val dict: IDictionary = DictionaryProvider().createDictionary(DictionaryType.TREE_SET)
//    val dict: IDictionary = DictionaryProvider().createDictionary(DictionaryType.HASH_SET)
    println("Number of words: ${dict.size()}")
    var word: String?
    while(true){
        print("What to find? ")
        word = readLine()
        if( word.equals("quit")){
            break
        }
        println("Result: ${word?.let { dict.find(it) }}")
    }
    //END PROBLEM 1*/

    //PROBLEM 2
    /*val name = "Sam Smith"
    println("${name}'s monogram is ${name.nameMonogram()}")*/
    //END PROBLEM 2

    //PROBLEM 3
    val words = listOf("apple", "pear", "melon");
    println("the list $words joined by #: ${words.joinByGivenSeparator("#")}")
    //END PROBLEM 3

    //PROBLEM 4
    val fruits = listOf("apple", "pear", "strawberry", "melon");
    println("The longest string is ${fruits.longestString()}")
    //END PROBLEM 4

    //Today's date
    val today = Date()
    println("Today's date is ${today.year}-${today.month}-${today.day}")

    //Is this date a leap year?
    println(today.isLeapYear())

    /*val tomorrow = Date(2002, 3, 5);
    println("Tomorrow's date is ${tomorrow.year}-${tomorrow.month}-${tomorrow.day}")*/

    val invalidDate = Date(2002, 13, 5);
    println(invalidDate.isThisValid())

    //generate random dates until 10 date is valid
    val dates = mutableListOf<Date>()
    while(dates.size < 10){
        val randYear = (2000..2100).random()
        val randMonth = (1..20).random()
        val randDay = (1..40).random()
        val date = Date(randYear, randMonth, randDay)
        if(date.isThisValid()){
            dates.add(date)
        } else {
            println("Invalid $date")
        }
    }
    dates.forEach { println("${it.year}-${it.month}-${it.day}") }

    println("The dates in natural order:")
    val sortedDates = dates.sortedWith(compareBy({it.year}, {it.month}, {it.day}))
    sortedDates.forEach { println("${it.year}-${it.month}-${it.day}") }

    println("The dates in reversed order:")
    val reverseDates = sortedDates.reversed()
    reverseDates.forEach { println("${it.year}-${it.month}-${it.day}") }

    //ascending by year and day, descending by month
    val customComparator = Comparator<Date> { date1, date2 ->
        if (date1.year != date2.year) {
            date1.year - date2.year
        } else if (date1.month != date2.month) {
            date2.month - date1.month
        } else {
            date1.day - date2.day
        }
    }

    dates.sortWith(customComparator)
    println("\nCustom Sorted Dates:")
    dates.forEach { println(it) }

    println("\n")
    extra()
}