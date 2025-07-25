package com.hypersoft.billing.presentation.interfaces

import com.hypersoft.billing.data.entities.purchase.PurchaseDetail

/**
 * Created by: Sohaib Ahmed
 * Date: 7/4/2025
 * <p>
 * Links:
 * - LinkedIn: <a href="https://linkedin.com/in/epegasus">Linkedin</a>
 * - GitHub: <a href="https://github.com/epegasus">Github</a>
 */

interface BillingPurchaseHistoryListener {
    fun onSuccess(purchaseDetails: List<PurchaseDetail>)
    fun onError(message: String) {}
}