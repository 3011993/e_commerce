package com.example.e_commerce.presentation.order_confirmation.stripe


import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.e_commerce.common.Constants.BACKEND_URL
import com.example.e_commerce.common.snackbar.SnackBarManager
import com.example.e_commerce.domain.model.CartModel
import com.example.e_commerce.domain.model.PaymentRequestBody
import com.example.e_commerce.domain.model.ServerCartItem
import com.example.e_commerce.presentation.order_confirmation.stripe.components.ErrorAlert
import com.example.e_commerce.presentation.order_confirmation.stripe.components.PayButton
import com.example.e_commerce.ui.theme.E_commerceTheme
import com.google.gson.Gson
import com.stripe.android.paymentsheet.PaymentSheet
import com.stripe.android.paymentsheet.PaymentSheetResult
import com.stripe.android.paymentsheet.rememberPaymentSheet
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


@Composable
fun CheckOutScreen(
    carts: List<CartModel>,
    isUserAnonymous: Boolean,
    onCheckOutClicked : () -> Unit,
    modifier: Modifier = Modifier
) {
    var paymentIntentClientSecret by remember { mutableStateOf<String?>(null) }

    var error by remember { mutableStateOf<String?>(null) }

    val paymentSheet = rememberPaymentSheet { paymentResult ->
        when (paymentResult) {
            is PaymentSheetResult.Completed -> SnackBarManager.showMessage("Payment complete!")
            is PaymentSheetResult.Canceled -> SnackBarManager.showMessage("Payment canceled!")
            is PaymentSheetResult.Failed -> {
                error = paymentResult.error.localizedMessage ?: paymentResult.error.message
            }
        }
    }

    error?.let { errorMessage ->
        ErrorAlert(
            errorMessage = errorMessage, onDismiss = {
                error = null
            })
    }

    LaunchedEffect(Unit) {
        fetchPaymentIntent(carts).onSuccess { clientSecret ->
            paymentIntentClientSecret = clientSecret
        }.onFailure { paymentIntentError ->
            error = paymentIntentError.localizedMessage ?: paymentIntentError.message
        }
    }

    PayButton(
        enabled = paymentIntentClientSecret != null, onClick = {
            if (!isUserAnonymous) {
                paymentIntentClientSecret?.let {
                    onPayClicked(
                        paymentSheet = paymentSheet,
                        paymentIntentClientSecret = it,
                    )
                }
            } else {
                onCheckOutClicked()
            }
        }, modifier
    )
}

private suspend fun fetchPaymentIntent(carts: List<CartModel>): Result<String> =
    suspendCoroutine { continuation ->
        val url = "$BACKEND_URL/create-payment-intent"
        val mediaType = "application/json; charset=utf-8".toMediaType()
        val serverCartItems = carts.map { cartModel ->
            val itemPriceInCents = (cartModel.price * 100).toLong()
            ServerCartItem(
                id = cartModel.productId.toString(),
                amount = itemPriceInCents
            )
        }
        val requestBodyObject = PaymentRequestBody(serverCartItems)
        val gson = Gson()
        val shoppingCartJson = gson.toJson(requestBodyObject)
        val body = shoppingCartJson.toRequestBody(mediaType)
        val request = Request.Builder()
            .url(url)
            .post(body)
            .build()

        OkHttpClient()
            .newCall(request)
            .enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    continuation.resume(Result.failure(e))
                }

                override fun onResponse(call: Call, response: Response) {
                    if (!response.isSuccessful) {
                        continuation.resume(Result.failure(Exception(response.message)))
                    } else {
                        val clientSecret = extractClientSecretFromResponse(response)

                        clientSecret?.let { secret ->
                            continuation.resume(Result.success(secret))
                        } ?: run {
                            val error =
                                Exception("Could not find payment intent client secret in response!")

                            continuation.resume(Result.failure(error))
                        }
                    }
                }
            })
    }

private fun extractClientSecretFromResponse(response: Response): String? {
    return try {
        val responseData = response.body?.string()
        val responseJson = responseData?.let { JSONObject(it) } ?: JSONObject()

        responseJson.getString("clientSecret")
    } catch (exception: JSONException) {
        null
    }
}

private fun onPayClicked(
    paymentSheet: PaymentSheet,
    paymentIntentClientSecret: String,
) {
    val configuration = PaymentSheet.Configuration.Builder(merchantDisplayName = "Example, Inc.")
        .build()

    // Present Payment Sheet
    paymentSheet.presentWithPaymentIntent(paymentIntentClientSecret, configuration)
}

@Preview
@Composable
private fun CheckOutScreePreview() {
    E_commerceTheme {
        CheckOutScreen(emptyList(), true,{})
    }
}