package main

/* data access layer with some CRUD operations; for simplicity initialize items here*/

class ItemRepository {
    val items: MutableList<Item> = mutableListOf<Item>(
        Item("What is Kotlin's primary use case?", listOf("Web development", "Mobile app development", "Data science", "Game development"), 2),
        Item("Which of the following is true about Kotlin's null safety?", listOf("All variables in Kotlin can be null by default.", "Kotlin doesn't allow null values at all.", "Kotlin distinguishes between nullable and non-nullable types.", "Null safety is not a concept in Kotlin."), 3),
        Item("In Kotlin, which keyword is used to define a class?", listOf("class", "Class", "struct", "Type"), 1),
        Item("What is the main advantage of using Kotlin's data classes?", listOf("They can extend multiple classes.", "They generate useful methods like `equals`, `hashCode`, and `toString`.", "They are only used for working with databases.", "They cannot have properties."), 2),
        Item("Which of the following is the correct way to declare a read-only (immutable) property in Kotlin?", listOf("val property: Int", "readonly property: Int", "var property: Int", "immutable property: Int"), 1),
        Item("What is the purpose of Kotlin's `when` expression?", listOf("It is used to define loops in Kotlin.", "It is used to create anonymous functions.", "It is used for pattern matching and branching based on values.", "It is used to define extension functions."), 3),
        Item("What is the Kotlin standard library function for filtering elements in a collection?", listOf("map", "forEach", "filter", "reduce"), 3),
        Item("Which Kotlin feature is used for defining an extension function on a class without modifying its source code?", listOf("Inheritance", "Polymorphism", "Extension functions", "Overriding"), 3),
        Item("What is the default visibility modifier in Kotlin for a class, function, or property?", listOf("public", "private", "protected", "internal"), 4),
        Item("In Kotlin, what is the purpose of the `init` block in a class?", listOf("It is used for primary constructor initialization.", "It is used to define extension functions.", "It is used for property initialization.", "It is used for secondary constructor initialization."), 1)
    )

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