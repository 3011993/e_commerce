package com.example.e_commerce.data.service

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

    override suspend fun saveCart(cart: CartModel) {
        val updatedCart = cart.copy(userId = auth.currentUserId)
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
        firestore.collection(CARTS_COLLECTION).document(cartId)
            .update(CART_ITEMS, FieldValue.arrayUnion(newItem)).await()
    }

    override suspend fun removeFromCart(cartId: String, productId: Int) {
        firestore.collection(CARTS_COLLECTION)
    }

    companion object {
        const val CARTS_COLLECTION = "carts"
        const val USER_ID_FIELD = "userId"
        const val CART_ITEMS = "cartItems"
    }
}