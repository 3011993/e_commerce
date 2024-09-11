package com.example.e_commerce.data.service

import android.util.Log
import com.example.e_commerce.domain.model.CartModel
import com.example.e_commerce.domain.service.AccountService
import com.example.e_commerce.domain.service.StorageService
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.dataObjects
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class StorageServiceImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: AccountService,
) : StorageService {

    private val cartsCollection
        get() = firestore.collection(CARTS_COLLECTION).whereEqualTo(
            USER_ID_FIELD,
            auth.currentUserId
        )


    @OptIn(ExperimentalCoroutinesApi::class)
    override val carts: Flow<List<CartModel>>
        get() = auth.currentUser.flatMapLatest { user ->
            firestore.collection(CARTS_COLLECTION).whereEqualTo(USER_ID_FIELD, user.id)
                .dataObjects()
        }

    suspend fun getCart(cartId: String): CartModel? =
        firestore.collection(CARTS_COLLECTION).document(cartId).get().await()
            .toObject(CartModel::class.java)

    suspend fun addCart(cart: CartModel) {
        firestore.collection(CARTS_COLLECTION).add(cart).await().id
    }

    override suspend fun saveCart(cart: CartModel) {
        val updatedCart = cart.copy(userId = auth.currentUserId)
        val productRef =
            firestore.collection(INVENTORY_COLLECTION).document(cart.productId.toString())
        firestore.runTransaction { transaction ->
            val productDoc = transaction.get(productRef)
            if (productDoc.exists()) {
                val quantity = productDoc.getLong("quantity")?.toInt() ?: 0
                val inStock = productDoc.getBoolean("inStock") ?: false
                if (inStock && quantity > 0) {
                    val newQuantity = quantity - 1
                    transaction.update(productRef, "quantity", newQuantity)
                    if (newQuantity == 0) {
                        transaction.update(productRef, "inStock", false)
                    }
                    firestore.collection(CARTS_COLLECTION).add(updatedCart).addOnSuccessListener {
                        Log.i("StorageImpl", "Product added to cart successfully")
                    }

                } else {
                    Log.i("StorageImpl", "issue in quantity and stock ")
                }
            } else {
                Log.i("StorageImpl", "product doesn't exist ")
            }
        }
    }

    override suspend fun updateCart(cart: CartModel) {
        firestore.collection(CARTS_COLLECTION).document(cart.cartId).set(cart).await()
    }

    override suspend fun deleteCart(cartId: String) {
        firestore.collection(CARTS_COLLECTION).document(cartId).delete().await()
    }

    override suspend fun checkIfCartExists(userId: String): Boolean {
        return try {
            firestore.collection(CARTS_COLLECTION).document(userId).get().await().exists()
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun addToCart(cart: CartModel) {
        val updatedCart = cart.copy(userId = auth.currentUserId)
        val productRef =
            firestore.collection(INVENTORY_COLLECTION).document(cart.productId.toString())
        val cartRef = firestore.collection(CARTS_COLLECTION).document(cart.cartId)
        firestore.runTransaction { transaction ->
            val productDoc = transaction.get(productRef)
            val cartDoc = transaction.get(cartRef)
            if (productDoc.exists()) {
                val quantity = productDoc.getLong("quantity")?.toInt() ?: 0
                val inStock = productDoc.getBoolean("inStock") ?: false
                if (inStock && quantity > 0) {
                    val newQuantity = quantity - 1
                    transaction.update(productRef, "quantity", newQuantity)
                    if (newQuantity == 0) {
                        transaction.update(productRef, "inStock", false)
                    }
                    if (cartDoc.exists()) {
                        // Update existing cart item
                        val existingQuantity = cartDoc.getLong("quantity") ?: 0
                        val increasedQuantity = existingQuantity + 1
                        val increasedPrice = cart.originalPrice * increasedQuantity
                        Log.i("storageImpl", increasedPrice.toString())
                        Log.i("storageImpl", existingQuantity.toString())
                        Log.i("storageImpl", increasedQuantity.toString())

                        transaction.update(cartRef, "quantity", increasedQuantity)
                        transaction.update(cartRef, "price", increasedPrice)
                    } else {
                        // Create new cart item
                        transaction.set(cartRef, updatedCart)
                    }

                } else {
                    Log.i("StorageImpl", "Issue in quantity and stock")
                }
            } else {
                Log.i("StorageImpl", "Product doesn't exist")
            }
            null // You need to return null from the transaction
        }

    }

    override suspend fun removeFromCart(cart: CartModel) {
        val productRef =
            firestore.collection(INVENTORY_COLLECTION).document(cart.productId.toString())
        val cartRef = firestore.collection(CARTS_COLLECTION).document(cart.cartId)
        firestore.runTransaction { transaction ->
            val productDoc = transaction.get(productRef)
            val cartDoc = transaction.get(cartRef)
            val quantity = productDoc.getLong("quantity")?.toInt() ?: 0
            val newQuantity = quantity + 1
            transaction.update(productRef, "quantity", newQuantity)
            transaction.update(productRef, "inStock", true)
            val existingQuantity = cartDoc.getLong("quantity") ?: 0
            if (existingQuantity > 1) {
                val decreasedQuantity = existingQuantity - 1
                val decreasedPrice = cart.originalPrice * decreasedQuantity
                transaction.update(
                    cartRef,
                    mapOf("quantity" to decreasedQuantity, "price" to decreasedPrice)
                )
            } else if (existingQuantity.toInt() == 1) {
                transaction.delete(cartRef)
            } else {

            }
        }
    }


    companion object {
        const val CARTS_COLLECTION = "carts"
        const val USER_ID_FIELD = "userId"
        const val PRODUCT_ID_FIELD = "productId"
        const val INVENTORY_COLLECTION = "inventory"
    }
}