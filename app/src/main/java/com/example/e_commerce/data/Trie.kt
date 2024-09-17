package com.example.e_commerce.data

import com.example.e_commerce.domain.model.ProductModel

class TrieNode {
    val children : MutableMap<Char, TrieNode> = HashMap()
    var isEndOfWord : Boolean = false
    var product : ProductModel? = null
}

class Trie {
    private val root = TrieNode()

    fun insert(product: ProductModel) {
        var current = root
        val words = product.title.split(" ")
        val firstTwoWords =
            if (words.size >= 2) words.subList(0, 2).joinToString(" ") else product.title
        for (char in firstTwoWords.lowercase()) {
            if (!current.children.containsKey(char)) {
                current.children[char] = TrieNode()
            }
            current = current.children[char]!!
        }
        current.isEndOfWord = true
        current.product = product
    }

    fun searchPrefix(prefix: String): List<ProductModel> {
        var current = root
        val products = mutableListOf<ProductModel>()
        for (char in prefix.lowercase()) {
            if (!current.children.containsKey(char)) {
                return emptyList()
            }
            current = current.children[char]!!
        }
        products.addAll(collectProducts(current))

        if (prefix.isNotEmpty() && prefix[0].isUpperCase()) {
            current = root
            val initial = prefix[0].lowercaseChar()
            if (current.children.containsKey(initial)) {
                products.addAll(collectProducts(current.children[initial]!!))
            }
        }
        return products.distinct()
    }

    private fun collectProducts(node: TrieNode): List<ProductModel> {
        val products = mutableListOf<ProductModel>()
        if (node.isEndOfWord) {
            node.product?.let { products.add(it) }
        }
        for (child in node.children.values) {
            products.addAll(collectProducts(child))
        }
        return products
    }

    companion object {
        fun preprocessProducts(products: List<ProductModel>): Trie {
            val trie = Trie()
            for (product in products) {
                trie.insert(product)
            }
            return trie
        }
    }
}

