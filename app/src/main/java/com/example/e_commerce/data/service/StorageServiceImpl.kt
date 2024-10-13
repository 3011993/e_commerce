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

    @OptIn(ExperimentalCoroutinesApi::class)
    override val carts: Flow<List<CartModel>>
        get() = auth.currentUser.flatMapLatest { user ->
            firestore.collection(CARTS_COLLECTION).whereEqualTo(USER_ID_FIELD, user.id)
                .dataObjects()
        }

    override suspend fun addOrUpdateCart(cart: CartModel,onResult: (Boolean) -> Unit) {
        val document = firestore.collection(CARTS_COLLECTION).document(cart.cartId).get().await()
        if(document.exists()){
            updateCart(cart,onResult)
        } else {
            addCart(cart, onResult = onResult)
        }
    }

    override fun addCart(cart: CartModel,onResult :(Boolean) -> Unit) {
        val updatedCart = cart.copy(userId = auth.currentUserId)
        val productRef =
            firestore.collection(INVENTORY_COLLECTION).document(cart.productId.toString())
        val cartRef = firestore.collection(CARTS_COLLECTION).document(cart.cartId)
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
                    transaction.set(cartRef, updatedCart)
                    return@runTransaction true
                } else {
                    Log.i("StorageImpl", "issue in quantity and stock ")
                    return@runTransaction  false
                }
            }
            return@runTransaction false
        }.addOnSuccessListener { result ->
            onResult(result)
        }.addOnFailureListener {
            onResult(false)
        }
    }

    override fun updateCart(cart: CartModel, onResult: (Boolean) -> Unit) {
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
                        val existingQuantity = cartDoc.getLong("quantity") ?: 0
                        val increasedQuantity = existingQuantity + 1
                        val increasedPrice = cart.originalPrice * increasedQuantity
                        transaction.update(cartRef, "quantity", increasedQuantity)
                        transaction.update(cartRef, "price", increasedPrice)
                        return@runTransaction true
                    } else {
                        transaction.set(cartRef, updatedCart)
                        return@runTransaction false
                    }

                } else {
                    Log.i("StorageImpl", "Issue in quantity and stock")
                    return@runTransaction false
                }
            } else {
                Log.i("StorageImpl", "Product doesn't exist")
                return@runTransaction false
            }
        }.addOnSuccessListener { result ->
            onResult(result)
        }.addOnFailureListener {
            onResult(false)
        }
    }

    override fun removeFromCart(cart: CartModel,onResult: (Boolean) -> Unit) {
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
                return@runTransaction true
            } else if (existingQuantity.toInt() == 1) {
                transaction.delete(cartRef)
                return@runTransaction true
            } else {
                return@runTransaction false
            }
        }.addOnSuccessListener { result->
            onResult(result)
        }.addOnFailureListener{
            onResult(false)
        }
    }
    override fun getInStockStatus(productId: String, callBack :(Boolean) -> Unit) {
        val productDoc =firestore.collection(INVENTORY_COLLECTION).document(productId)
        productDoc.get().addOnSuccessListener { snapShot ->
            if (snapShot != null && snapShot.exists()){
                 callBack(snapShot.getBoolean("inStock") ?: false)
            } else {
                callBack(false)
            }
        }
    }
    companion object {
        const val CARTS_COLLECTION = "carts"
        const val USER_ID_FIELD = "userId"
        const val INVENTORY_COLLECTION = "inventory"
    }
}