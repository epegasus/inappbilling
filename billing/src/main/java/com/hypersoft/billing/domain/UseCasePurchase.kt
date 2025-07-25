package com.hypersoft.billing.domain

import android.app.Activity
import android.util.Log
import com.android.billingclient.api.BillingFlowParams
import com.android.billingclient.api.Purchase
import com.hypersoft.billing.data.repository.BillingRepository
import com.hypersoft.billing.presentation.states.BillingState
import com.hypersoft.billing.presentation.states.QueryResponse
import com.hypersoft.billing.utilities.constants.Constants.TAG
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Created by: Sohaib Ahmed
 * Date: 7/7/2025
 * <p>
 * Links:
 * - LinkedIn: <a href="https://linkedin.com/in/epegasus">Linkedin</a>
 * - GitHub: <a href="https://github.com/epegasus">Github</a>
 */

internal class UseCasePurchase(private val repository: BillingRepository) {

    suspend fun purchaseInApp(activity: Activity?, productId: String): QueryResponse<String> = withContext(Dispatchers.Default) {
        if (activity == null) {
            repository.currentState = BillingState.ACTIVITY_REFERENCE_NOT_FOUND
            return@withContext QueryResponse.Error("Activity Ref is null")
        }

        if (productId.isEmpty()) {
            repository.currentState = BillingState.CONSOLE_BUY_PRODUCT_EMPTY_ID
            return@withContext QueryResponse.Error("Product Id can't be empty")
        }

        /* ─── Guard clauses ─────────────────────────── */
        if (!repository.isBillingClientReady) {
            repository.currentState = BillingState.CONNECTION_INVALID
            return@withContext QueryResponse.Error("Play Billing not ready. Try again.")
        }

        val response = repository.queryInAppProductDetails(listOf(productId))
        if (response.isNullOrEmpty()) {
            repository.currentState = BillingState.CONSOLE_PRODUCTS_IN_APP_NOT_EXIST
            return@withContext QueryResponse.Error("Product Details are found")
        }

        val productDetails = response[0]
        val offerToken = productDetails.oneTimePurchaseOfferDetailsList?.get(0)?.offerToken

        val productDetailsParamsList = listOf(
            when (offerToken != null) {
                true -> BillingFlowParams.ProductDetailsParams.newBuilder().setProductDetails(productDetails).setOfferToken(offerToken).build()
                false -> BillingFlowParams.ProductDetailsParams.newBuilder().setProductDetails(productDetails).build()
            }
        )

        val params = BillingFlowParams.newBuilder()
            .setProductDetailsParamsList(productDetailsParamsList)
            .build()

        repository.purchaseFlow(activity, params)
        repository.currentState = BillingState.BILLING_FLOW_LAUNCHED_SUCCESSFULLY
        QueryResponse.Success("Billing flow launched successfully")
    }

    suspend fun purchaseSubs(activity: Activity?, productId: String, planId: String): QueryResponse<String> = withContext(Dispatchers.Default) {
        if (activity == null) {
            repository.currentState = BillingState.ACTIVITY_REFERENCE_NOT_FOUND
            return@withContext QueryResponse.Error("Activity Ref is null")
        }

        if (productId.isEmpty()) {
            repository.currentState = BillingState.CONSOLE_BUY_PRODUCT_EMPTY_ID
            return@withContext QueryResponse.Error("Product Id can't be empty")
        }

        /* ─── Guard clauses ─────────────────────────── */
        if (!repository.isBillingClientReady) {
            repository.currentState = BillingState.CONNECTION_INVALID
            return@withContext QueryResponse.Error("Play Billing not ready. Try again.")
        }

        val response = repository.querySubsProductDetails(listOf(productId))
        if (response.isNullOrEmpty()) {
            repository.currentState = BillingState.CONSOLE_PRODUCTS_SUB_NOT_EXIST
            return@withContext QueryResponse.Error("Product Details are found")
        }

        val productDetails = response[0]
        val offerToken = productDetails.subscriptionOfferDetails?.find { it.basePlanId == planId }?.offerToken

        if (offerToken == null) {
            repository.currentState = BillingState.CONSOLE_PRODUCTS_SUB_NOT_EXIST
            return@withContext QueryResponse.Error("Product Details are found")
        }

        val productDetailsParamsList = listOf(
            BillingFlowParams.ProductDetailsParams
                .newBuilder()
                .setProductDetails(productDetails)
                .setOfferToken(offerToken)
                .build()
        )

        val params = BillingFlowParams.newBuilder()
            .setProductDetailsParamsList(productDetailsParamsList)
            .build()

        repository.purchaseFlow(activity, params)
        repository.currentState = BillingState.BILLING_FLOW_LAUNCHED_SUCCESSFULLY
        QueryResponse.Success("Billing flow launched successfully")
    }

    suspend fun updateSubs(activity: Activity?, oldProductId: String, productId: String, planId: String): QueryResponse<String> = withContext(Dispatchers.Default) {
        if (activity == null) {
            repository.currentState = BillingState.ACTIVITY_REFERENCE_NOT_FOUND
            return@withContext QueryResponse.Error("Activity Ref is null")
        }

        if (oldProductId.isEmpty()) {
            repository.currentState = BillingState.CONSOLE_PRODUCTS_OLD_SUB_NOT_FOUND
            return@withContext QueryResponse.Error("Old Product Id can't be empty")
        }

        if (productId.isEmpty()) {
            repository.currentState = BillingState.CONSOLE_BUY_PRODUCT_EMPTY_ID
            return@withContext QueryResponse.Error("Product Id can't be empty")
        }

        /* ─── Guard clauses ─────────────────────────── */
        if (!repository.isBillingClientReady) {
            repository.currentState = BillingState.CONNECTION_INVALID
            return@withContext QueryResponse.Error("Play Billing not ready. Try again.")
        }

        val response = repository.querySubsProductDetails(listOf(productId))
        if (response.isNullOrEmpty()) {
            repository.currentState = BillingState.CONSOLE_PRODUCTS_SUB_NOT_EXIST
            return@withContext QueryResponse.Error("Product Details are found")
        }

        val purchaseList = repository.querySubsPurchases()
        val oldPurchase = purchaseList.find { it.products.any { it == oldProductId } }

        if (oldPurchase == null) {
            repository.currentState = BillingState.CONSOLE_PRODUCTS_SUB_NOT_EXIST
            return@withContext QueryResponse.Error("Product Details are found")
        }

        val productDetails = response[0]
        val offerToken = productDetails.subscriptionOfferDetails?.find { it.basePlanId == planId }?.offerToken

        if (offerToken == null) {
            repository.currentState = BillingState.CONSOLE_PRODUCTS_SUB_NOT_EXIST
            return@withContext QueryResponse.Error("Product Details are found")
        }

        val productDetailsParamsList = listOf(
            BillingFlowParams.ProductDetailsParams
                .newBuilder()
                .setProductDetails(productDetails)
                .setOfferToken(offerToken)
                .build()
        )
        val productUpdateParams = BillingFlowParams.SubscriptionUpdateParams.newBuilder()
            .setOldPurchaseToken("old_purchase_token")
            .setSubscriptionReplacementMode(BillingFlowParams.SubscriptionUpdateParams.ReplacementMode.CHARGE_FULL_PRICE)
            .build()

        val params = BillingFlowParams.newBuilder()
            .setProductDetailsParamsList(productDetailsParamsList)
            .setSubscriptionUpdateParams(productUpdateParams)
            .build()

        repository.purchaseFlow(activity, params)
        repository.currentState = BillingState.BILLING_FLOW_LAUNCHED_SUCCESSFULLY
        QueryResponse.Success("Billing flow launched successfully")
    }

    suspend fun handlePurchase(purchases: List<Purchase>?, consumableIds: List<String>) = withContext(Dispatchers.Default) {
        if (purchases.isNullOrEmpty()) {
            repository.currentState = BillingState.PURCHASES_NOT_FOUND
            return@withContext
        }
        checkForAcknowledgedPurchases(purchases)
        checkForConsumablePurchases(purchases, consumableIds)
    }

    private suspend fun checkForAcknowledgedPurchases(purchases: List<Purchase>) {
        repository.currentState = BillingState.ACKNOWLEDGE_PURCHASE
        val unAcknowledgeList = purchases.filter { it.isAcknowledged.not() }
        Log.i(TAG, "BillingService: checkForAcknowledgedPurchases: ${unAcknowledgeList.size} purchase(s) needs to be acknowledge")

        if (unAcknowledgeList.isNotEmpty()) {
            repository.currentState = BillingState.ACKNOWLEDGE_PURCHASE
            repository.acknowledgePurchases(unAcknowledgeList)
            repository.currentState = BillingState.ACKNOWLEDGE_PURCHASE_SUCCESS
        } else {
            repository.currentState = BillingState.ACKNOWLEDGE_PURCHASE_FAILURE
        }
    }

    private suspend fun checkForConsumablePurchases(purchases: List<Purchase>, consumableIds: List<String>) {
        val consumablePurchases = purchases.filter { purchase -> purchase.products.any(consumableIds::contains) }
        Log.i(TAG, "BillingService: checkForConsumablePurchases: ${consumablePurchases.size} purchase(s) needs to be consumed")

        if (consumablePurchases.isNotEmpty()) {
            repository.currentState = BillingState.CONSUME_PURCHASE
            repository.consumePurchases(consumablePurchases)
            repository.currentState = BillingState.CONSUME_PURCHASE_SUCCESS
        } else {
            repository.currentState = BillingState.CONSUME_PURCHASE_FAILURE
        }
    }
}