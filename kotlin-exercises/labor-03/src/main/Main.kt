package main

fun main(args: Array<String>) {
    val itemController = ItemController(ItemService(ItemRepository()))
    itemController.quiz(10)
}