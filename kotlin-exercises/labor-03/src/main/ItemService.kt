package main

/* business logic - allows random selection of a given number of questions*/

class ItemService(private val repository: ItemRepository) {
    fun selectRandomItems(count: Int): List<Item> {
        return repository.items.shuffled().take(count)
    }
}