package com.example.e_commerce

import android.app.Application
import com.stripe.android.PaymentConfiguration
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class CommerceApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        PaymentConfiguration.init(
            applicationContext,
            "pk_test_51RTq8SR2KirZlhYQX8t7I4ZkepEameq15Tdjv9qEEA4718p9sHpSFmPqLKhuzA7av15pW6t4QBZ6qnfE24xmWl8n00lW7IzvGp"
        )
    }
}