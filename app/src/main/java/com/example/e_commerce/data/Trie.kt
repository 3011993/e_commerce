package com.example.e_commerce.data

import com.example.e_commerce.domain.model.ProductModel

class TrieNode {
    val children: MutableMap<Char, TrieNode> = HashMap()
    var isEndOfWord: Boolean = false
    var products: MutableList<ProductModel> = mutableListOf()
}

class Trie {
    private val root = TrieNode()

    fun insert(product: ProductModel) {
        val words = product.title.split(" ")
        val titleFourWords = if (words.size >= 4) words.subList(0, 4) else words
        for (word in titleFourWords) {
            var current = root
            for (char in word.lowercase()) {
                if (!current.children.containsKey(char)) {
                    current.children[char] = TrieNode()
                }
                current = current.children[char]!!
            }
            current.isEndOfWord = true
            current.products.add(product)
        }
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
        return collectProducts(current)
    }

    private fun collectProducts(node: TrieNode): List<ProductModel> {
        val products = mutableListOf<ProductModel>()
        if (node.isEndOfWord) {
            products.addAll(node.products)
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

