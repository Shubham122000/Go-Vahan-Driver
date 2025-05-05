package com.gvpartner.com.payment

object Config {

    var ENVIRONMENT = "https://zaakstaging.zaakpay.com"
    //Use https://api.zaakpay.com/ for live transactions

    //Use https://api.zaakpay.com/ for live transactions
    //Replace with your credentials that you get from dashboard for live transactions
    var ZAAKPAY_SECRET_KEY = "0678056d96914a8583fb518caf42828a"
    var ZAAKPAY_MERCHANT_IDENTIFIER = "b6415a6443604ec59644a70c8b25a0f6"


    var TRANSACTION_API_URL = "/api/paymentTransact/V8"

    var UPDATE_API_URL = "/updatetransaction"

    var TRANSACTION_STATUS_API_URL = "/checkTxn?v=5"


    var RETURN_URL = "" //Enter your response page URL here


}