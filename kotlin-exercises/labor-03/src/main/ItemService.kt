package main

/* business logic - allows random selection of a given number of questions*/

class ItemService(private val repository: ItemRepository) {
    fun selectRandomItems(count: Int): List<Item> {
        if (count-1 > repository.items.size || count < 1) {
            throw IllegalArgumentException("Invalid number of items requested")
        }
        return repository.items.shuffled().take(count)
    }
}