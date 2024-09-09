package com.example.e_commerce.data.service

import android.util.Log
import com.example.e_commerce.domain.model.CartItemModel
import com.example.e_commerce.domain.model.CartModel
import com.example.e_commerce.domain.service.AccountService
import com.example.e_commerce.domain.service.StorageService
import com.google.firebase.firestore.FieldValue
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

    override suspend fun saveCart(cart: CartModel) {
        val updatedCart = cart.copy(userId = auth.currentUserId)
        Log.i("userACCountCreate", auth.currentUserId)
        firestore.collection(CARTS_COLLECTION).add(updatedCart).await().id
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

    override suspend fun addToCart(cartId: String, newItem: CartItemModel) {
        val productRef =
            firestore.collection(INVENTORY_COLLECTION).document(newItem.productId.toString())
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
                    firestore.collection(CARTS_COLLECTION).document(cartId)
                        .update(CART_ITEMS, FieldValue.arrayUnion(newItem)).addOnSuccessListener {
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


    override suspend fun removeFromCart(cartId: String, cartModel: CartItemModel) {
        val productRef =
            firestore.collection(INVENTORY_COLLECTION).document(cartModel.productId.toString())
        firestore.runTransaction { transaction ->
            val productDoc = transaction.get(productRef)
            val quantity = productDoc.getLong("quantity")?.toInt() ?: 0
            val newQuantity = quantity + 1
            transaction.update(productRef, "quantity", newQuantity)
            transaction.update(productRef, "inStock", true)
            firestore.collection(CARTS_COLLECTION).document(cartId)
                .update(CART_ITEMS, FieldValue.arrayRemove(cartModel)).addOnSuccessListener {
                    Log.i("StorageImpl", "Product removed successfully")
                }
        }
    }

    companion object {
        const val CARTS_COLLECTION = "carts"
        const val USER_ID_FIELD = "userId"
        const val CART_ITEMS = "cartItems"
        const val INVENTORY_COLLECTION = "inventory"
        const val STOCK_DOCUMENT = "stock"
    }
}