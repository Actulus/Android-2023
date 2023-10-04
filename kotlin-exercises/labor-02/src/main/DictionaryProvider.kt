package main

class DictionaryProvider {
    fun createDictionary(type: DictionaryType): IDictionary {
        return when (type) {
            DictionaryType.ARRAY_LIST -> ListDictionary
            DictionaryType.TREE_SET -> TreeSetDictionary
            DictionaryType.HASH_SET -> HashSetDictionary
        }
    }
}
