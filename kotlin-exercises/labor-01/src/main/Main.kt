package main

import java.util.Base64

fun main() {
//1. ex.
    val a = 2
    var b = 5
    sum(a,b)
    b = 7
    sum(a,b)
//2. ex.
    days()
//3. ex.
    for(i in 1..100){
        println("$i is a prime number: ${isPrime(i)}")
    }

    println( isEvenNumber(2))

//4. ex.
    val string = "alma"
    val encoded = encode(string)
    println("$string in encoded form: ${messageCoding(string, ::encode)}")
    println("$encoded in decoded form: ${messageCoding(encoded, ::decode)}")
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
    val daysOfWeek = listOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday")
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
//fun evenNumbersFromList(list: List) = println(list)