package com.govahanpartner.com.payment

import java.util.*
import kotlin.collections.HashMap

class RequestParameters() {
    //You can use Getter/Setter function to set these values dynamically
    var amount //In Paisa
            : String? = null
    var buyerEmail = "abc@gmail.com"
    var currency = "INR"
    var mode = "0"

    var orderId: String? = null
    var merchantIdentifier: String? = null
    var returnUrl: String? = null

    //Required for updating the transaction
    var updateDesired //14 for Full Refund and 22 For Partial
            : String? = null
    var updateReason: String? = null


    //For Initiating Payment
    fun RequestParameters(
        merchantIdentifier: String?,
        returnUrl: String?,
        orderId: String?,
        amount: String?
    ) {
        this.merchantIdentifier = merchantIdentifier
        this.returnUrl = returnUrl
        this.orderId = orderId
        this.amount = amount
    }

    //For Checking Payment Status
    fun RequestParameters(orderId: String?, merchantIdentifier: String?) {
        this.orderId = orderId
        this.merchantIdentifier = merchantIdentifier
    }

    //For Refund
    fun RequestParameters(
        merchantIdentifier: String?,
        amount: String?,
        orderId: String?,
        updateDesired: String?,
        updateReason: String?
    ) {
        this.merchantIdentifier = merchantIdentifier
        this.amount = amount
        this.orderId = orderId
        this.updateDesired = updateDesired
        this.updateReason = updateReason
    }

    fun getPaymentRequestParameters(): TreeMap<String?, String?>? {
        val requestParams: TreeMap<String?, String?> = TreeMap<String?, String?>()
        //Using TreeMap to pass the parameters in a sorted way to ensure the checksum sequence

        //***********Mandatory Parameters************//
        requestParams.put("orderId", orderId)
        requestParams.put("amount", amount)
        requestParams.put("buyerEmail", buyerEmail)
        requestParams.put("currency", currency)
        requestParams.put("merchantIdentifier", merchantIdentifier)
        requestParams.put("returnUrl", returnUrl)
        //*******************************************//

        //You can add optional parameters in a similar way
        return requestParams
    }


    fun getTransactionStatusRequestParameters(): Map<String, String?>? {
        val requestParams: MutableMap<String, String?> = HashMap()
        requestParams["merchantIdentifier"] = merchantIdentifier
        requestParams["orderId"] = orderId

        //You can also pass JSON object here to submit in final form request or you can use the below format string
        requestParams["formRequestData"] =
            "{merchantIdentifier:$merchantIdentifier,mode:$mode,orderDetail:{orderId:$orderId}}"
        return requestParams
    }

    fun getFullRefundRequestParameters(): Map<String, String?>? {
        val requestParams: MutableMap<String, String?> = HashMap()
        requestParams["merchantIdentifier"] = merchantIdentifier
        requestParams["orderId"] = orderId
        requestParams["mode"] = mode
        requestParams["updateDesired"] = updateDesired
        requestParams["updateReason"] = updateReason
        return requestParams
    }

    fun getPartialRefundRequestParameters(): Map<String, String?>? {
        val requestParams: MutableMap<String, String?> = HashMap()
        requestParams["merchantIdentifier"] = merchantIdentifier
        requestParams["orderId"] = orderId
        requestParams["mode"] = mode
        requestParams["updateDesired"] = updateDesired
        requestParams["updateReason"] = updateReason
        requestParams["amount"] = amount //Mandatory for partial refund
        return requestParams
    }

}