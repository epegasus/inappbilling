package com.hypersoft.billing.latest.utils

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hypersoft.billing.latest.enums.ResultState

/**
 * @Author: SOHAIB AHMED
 * @Date: 21/02/2024
 * @Accounts
 *      -> https://github.com/epegasus
 *      -> https://stackoverflow.com/users/20440272/sohaib-ahmed
 */

object Result {

    private var RESULT_STATE = ResultState.NONE

    private var _resultState = MutableLiveData<ResultState>()
    val resultState: LiveData<ResultState> get() = _resultState

    fun setResultState(resultState: ResultState) {
        Log.d("BillingManager", "setResultState: $resultState")
        RESULT_STATE = resultState
        _resultState.postValue(RESULT_STATE)
    }

    fun getResultState(): ResultState {
        return RESULT_STATE
    }

    override fun toString(): String {
        return when (RESULT_STATE) {
            ResultState.NONE -> ResultState.NONE.message
            ResultState.NO_INTERNET_CONNECTION -> ResultState.NO_INTERNET_CONNECTION.message
            ResultState.EMPTY_PRODUCT_ID_LIST -> ResultState.EMPTY_PRODUCT_ID_LIST.message

            ResultState.CONNECTION_INVALID -> ResultState.CONNECTION_INVALID.message
            ResultState.CONNECTION_ESTABLISHING -> ResultState.CONNECTION_ESTABLISHING.message
            ResultState.CONNECTION_ESTABLISHING_IN_PROGRESS -> ResultState.CONNECTION_ESTABLISHING_IN_PROGRESS.message
            ResultState.CONNECTION_ALREADY_ESTABLISHED -> ResultState.CONNECTION_ALREADY_ESTABLISHED.message
            ResultState.CONNECTION_DISCONNECTED -> ResultState.CONNECTION_DISCONNECTED.message
            ResultState.CONNECTION_ESTABLISHED -> ResultState.CONNECTION_ESTABLISHED.message
            ResultState.CONNECTION_FAILED -> ResultState.CONNECTION_FAILED.message
            ResultState.FEATURE_NOT_SUPPORTED -> ResultState.FEATURE_NOT_SUPPORTED.message
            ResultState.ACTIVITY_REFERENCE_NOT_FOUND -> ResultState.ACTIVITY_REFERENCE_NOT_FOUND.message

            ResultState.CONSOLE_PURCHASE_PRODUCTS_INAPP_FETCHING -> ResultState.CONSOLE_PURCHASE_PRODUCTS_INAPP_FETCHING.message
            ResultState.CONSOLE_PURCHASE_PRODUCTS_INAPP_FETCHING_FAILED -> ResultState.CONSOLE_PURCHASE_PRODUCTS_INAPP_FETCHING_FAILED.message
            ResultState.CONSOLE_PURCHASE_PRODUCTS_INAPP_FETCHING_SUCCESS -> ResultState.CONSOLE_PURCHASE_PRODUCTS_INAPP_FETCHING_SUCCESS.message
            ResultState.CONSOLE_PURCHASE_PRODUCTS_INAPP_FOUND -> ResultState.CONSOLE_PURCHASE_PRODUCTS_INAPP_FOUND.message
            ResultState.CONSOLE_PURCHASE_PRODUCTS_INAPP_NOT_FOUND -> ResultState.CONSOLE_PURCHASE_PRODUCTS_INAPP_NOT_FOUND.message

            ResultState.CONSOLE_PURCHASE_PRODUCTS_SUB_FETCHING -> ResultState.CONSOLE_PURCHASE_PRODUCTS_SUB_FETCHING.message
            ResultState.CONSOLE_PURCHASE_PRODUCTS_SUB_FETCHING_FAILED -> ResultState.CONSOLE_PURCHASE_PRODUCTS_SUB_FETCHING_FAILED.message
            ResultState.CONSOLE_PURCHASE_PRODUCTS_SUB_FETCHING_SUCCESS -> ResultState.CONSOLE_PURCHASE_PRODUCTS_SUB_FETCHING_SUCCESS.message
            ResultState.CONSOLE_PURCHASE_PRODUCTS_SUB_FOUND -> ResultState.CONSOLE_PURCHASE_PRODUCTS_SUB_FOUND.message
            ResultState.CONSOLE_PURCHASE_PRODUCTS_SUB_NOT_FOUND -> ResultState.CONSOLE_PURCHASE_PRODUCTS_SUB_NOT_FOUND.message

            ResultState.CONSOLE_PURCHASE_PRODUCTS_RESPONSE_PROCESSING -> ResultState.CONSOLE_PURCHASE_PRODUCTS_RESPONSE_PROCESSING.message
            ResultState.CONSOLE_PURCHASE_PRODUCTS_RESPONSE_COMPLETE -> ResultState.CONSOLE_PURCHASE_PRODUCTS_RESPONSE_COMPLETE.message
            ResultState.CONSOLE_PURCHASE_PRODUCTS_CHECKED_FOR_ACKNOWLEDGEMENT -> ResultState.CONSOLE_PURCHASE_PRODUCTS_CHECKED_FOR_ACKNOWLEDGEMENT.message

            ResultState.CONSOLE_PRODUCTS_IN_APP_FETCHING -> ResultState.CONSOLE_PRODUCTS_IN_APP_FETCHING.message
            ResultState.CONSOLE_PRODUCTS_IN_APP_FETCHED_SUCCESSFULLY -> ResultState.CONSOLE_PRODUCTS_IN_APP_FETCHED_SUCCESSFULLY.message
            ResultState.CONSOLE_PRODUCTS_IN_APP_FETCHING_FAILED -> ResultState.CONSOLE_PRODUCTS_IN_APP_FETCHING_FAILED.message
            ResultState.CONSOLE_PRODUCTS_IN_APP_AVAILABLE -> ResultState.CONSOLE_PRODUCTS_IN_APP_AVAILABLE.message
            ResultState.CONSOLE_PRODUCTS_IN_APP_NOT_EXIST -> ResultState.CONSOLE_PRODUCTS_IN_APP_NOT_EXIST.message
            ResultState.CONSOLE_PRODUCTS_IN_APP_NOT_FOUND -> ResultState.CONSOLE_PRODUCTS_IN_APP_NOT_FOUND.message

            ResultState.CONSOLE_PRODUCTS_SUB_FETCHING -> ResultState.CONSOLE_PRODUCTS_SUB_FETCHING.message
            ResultState.CONSOLE_PRODUCTS_SUB_FETCHED_SUCCESSFULLY -> ResultState.CONSOLE_PRODUCTS_SUB_FETCHED_SUCCESSFULLY.message
            ResultState.CONSOLE_PRODUCTS_SUB_FETCHING_FAILED -> ResultState.CONSOLE_PRODUCTS_SUB_FETCHING_FAILED.message
            ResultState.CONSOLE_PRODUCTS_SUB_AVAILABLE -> ResultState.CONSOLE_PRODUCTS_SUB_AVAILABLE.message
            ResultState.CONSOLE_PRODUCTS_SUB_NOT_EXIST -> ResultState.CONSOLE_PRODUCTS_SUB_NOT_EXIST.message
            ResultState.CONSOLE_PRODUCTS_SUB_NOT_FOUND -> ResultState.CONSOLE_PRODUCTS_SUB_NOT_FOUND.message

            ResultState.LAUNCHING_FLOW_INVOCATION_SUCCESSFULLY -> ResultState.LAUNCHING_FLOW_INVOCATION_SUCCESSFULLY.message
            ResultState.LAUNCHING_FLOW_INVOCATION_USER_CANCELLED -> ResultState.LAUNCHING_FLOW_INVOCATION_USER_CANCELLED.message
            ResultState.LAUNCHING_FLOW_INVOCATION_EXCEPTION_FOUND -> ResultState.LAUNCHING_FLOW_INVOCATION_EXCEPTION_FOUND.message
            ResultState.PURCHASED_SUCCESSFULLY -> ResultState.PURCHASED_SUCCESSFULLY.message
            ResultState.PURCHASING_ALREADY_OWNED -> ResultState.PURCHASING_ALREADY_OWNED.message
            ResultState.PURCHASING_USER_CANCELLED -> ResultState.PURCHASING_USER_CANCELLED.message
            ResultState.PURCHASING_FAILURE -> ResultState.PURCHASING_FAILURE.message
            ResultState.PURCHASING_ERROR -> ResultState.PURCHASING_ERROR.message
        }
    }

}