package main

import java.util.Base64
import java.util.Random

fun main() {
//1. ex.
    val a = 2
    var b = 5
    sum(a,b)
    b = 7
    sum(a,b)
    println()
//2. ex.
    days()
    println()
//3. ex.
    for(i in 1..100){
        println("$i is a prime number: ${isPrime(i)}")
    }

    println( isEvenNumber(2))

//4. ex.
    println()
    val string = "alma"
    val encoded = encode(string)
    println("$string in encoded form: ${messageCoding(string, ::encode)}")
    println("$encoded in decoded form: ${messageCoding(encoded, ::decode)}")

//5. ex.
    println()
    val list = listOf(1, 2, 3, 4, 5)
    print("The even numbers from this list $list are: ")
    evenNumbersFromList(list).forEach{print("$it ")}


//6. ex.
    println()
    //a)
    print("\n$list doubled: ")
    doubleTheElements(list)
    //b)
    val daysOfWeek = listOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday")
    print("\nThe days of the week capitalized: ")
    capitalizeStringList(daysOfWeek)
    //c)
    print("\nThe first char of each day capitalized: ")
    capitalizeFirstChar(daysOfWeek)
    //d)
    print("\nThe length of each day:\n")
    lengthOfDays(daysOfWeek)
    //e)
    print("The average length of days is ${avgLengthOfDays(daysOfWeek)}")

//7. ex.
    println()
    //a)
    println("The days of the week without the letter 'n' in them")
    convertToMutableAndNoN(daysOfWeek).forEach{println(it)}
    //b)Print each element of the list in a new line together with the index of the element (convert
    //the list to list with index using the withIndex() function!)
    println("The days with their index")
    convertToMutableAndNoN(daysOfWeek).withIndex().forEach{println("Item at ${it.index} is ${it.value}")}
    //c) Sort the result list alphabetically!
    println("The days sorted alphabetically")
    convertToMutableAndNoN(daysOfWeek).sorted().forEach{println(it)}

//8.ex.
    //a)
    println()
    println("Array with random integers between 0 and 100: ")
    val array = randomInts()
    array.forEach { println(it) }
    //b)
    println("The array in sorted form")
    array.sorted().forEach{println(it)}
    //c)
    println("The array contains an even number: ${arrayContainsEven(array)}")
    //d)
    println("All the numbers in the array are even: ${areAllNumbersEven(array)}")
    //e)
    println("The avg of the numbers in the array: ${avgArray(array)}")
}

//1.ex.
fun sum(a: Int, b: Int): Int{
    val sum = a+b
    println("sum=$sum")
    println("sum=${2+5}")
    return 0
}

// 2. ex. Write a main function that declares an immutable list (listOf) daysOfWeek containing the
//days of the week.
fun days(){
    val daysOfWeek = listOf("monday", "tuesday", "wednesday", "thursday", "friday", "saturday", "sunday")
    // iterates over the list and prints the list to the standard output
    for(day in daysOfWeek){
        println(day)
    }
    /*for(day:String in daysOfWeek){
        if(day.startsWith("T")){
     		println(day)
    	}
    }*/
    // filter to print the days starting with letter ‘T’
    println("Days starting with the letter T:")
    daysOfWeek.filter{
        it.startsWith("T")
    }.forEach{
        println(it)
    }

    // filter to print the days containing the letter ‘e’
    println("Days containing the letter e:")
    daysOfWeek.filter{
        it.contains("e")
    }.forEach{
        println(it)
    }

    //filter to print all the days of length 6 (e.g. Friday)
    println("Days with 6 letters")
    daysOfWeek.filter{
        it.length == 6
    }.forEach{
        println(it)
    }
}

//Write a function that checks whether a number is prime or not
fun isPrime(num: Int): Boolean {
    for(i in 2..num/2+1){
        if(num % i == 0){
            return false
        }
    }
    return true
}

//check if number is even
fun isEvenNumber(a: Int) = a % 2 == 0

// Write an encode and a corresponding decode function that encodes and respectively
// decodes the characters of a string
fun encode(s : String): String {
    return Base64.getEncoder().encodeToString(s.toByteArray())
}

fun decode(s : String): String {
    return String(Base64.getDecoder().decode(s))
}

fun messageCoding(msg: String, func: (String) -> String): String{
    return func(msg)
}

//5. ex. Write a compact function that prints the even numbers from a list. Use a list filter!
fun evenNumbersFromList(list : List<Int>): List<Int> = list.filter{ it % 2 == 0 }

//6.The map() performs the same transformation on every list item and returns the result list
//a) Double the elements of a list of integers and print it.
fun doubleTheElements(list: List<Int>) = list.map{it * 2}.map{print("$it ")}
//b) Print the days of week capitalized (e.g. MONDAY for Monday)
fun capitalizeStringList(list: List<String>) = list.map{ it.uppercase() }.map{print("$it ")}
//c) Print the first character of each day capitalized (e.g. m for Monday)
fun capitalizeFirstChar(list: List<String>) = list.map { it.replaceFirstChar(Char::titlecase)}.map { print("$it ") }
//d) Print the length of days (number of characters, e.g. Monday → 6)
fun lengthOfDays(list: List<String>) = list.map { println("$it -> ${it.length} ") }

//e) Compute the average length of days (in number of characters)
fun avgLengthOfDays(list: List<String>): Double {
    val sum = list.sumOf { it.length }
    return sum.toDouble() / list.size
}

//7. ex. Mutable lists
//a)Convert the daysOfWeek immutable list into a mutable one. Remove all days containing
//the letter ‘n’, then print the mutable list.
fun convertToMutableAndNoN(list: List<String>): List<String>{
    return list.toMutableList().filter{ !it.contains("n") }//.forEach{println(it)}
}

//8. ex. Arrays
//a)Generate an array of 10 random integers between 0 and 100, and use forEach to print
//each element of the array in a new line.
fun randomInts(): Array<Int> {
    return Array(10) { Random().nextInt(0, 100) }
}
//c) Check whether the array contains any even number!
fun arrayContainsEven(array: Array<Int>): Boolean {
    array.forEach {
        if(isEvenNumber(it))
            return true
    }
    return false
}
//d) Check whether all the numbers are even!
fun areAllNumbersEven(array: Array<Int>): Boolean{
    array.forEach {
        if(!isEvenNumber(it))
            return false
    }
    return true
}
//e) Calculate the average of generated numbers and print it using forEach!
fun avgArray(array: Array<Int>): Double {
    return array.sumOf { it }.toDouble() / array.size
}