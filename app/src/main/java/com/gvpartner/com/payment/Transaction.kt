//package com.transportationapp.payment
//
//class Transaction {
//    @Throws(Exception::class)
//    fun processPayment(): ZaakpayApiRequestParameters? {
//        val zaakpayApiRequestParameters = ZaakpayApiRequestParameters()
//        val requestUrl: String = Config.ENVIRONMENT + Config.TRANSACTION_API_URL
//        zaakpayApiRequestParameters.setRequestUrl(requestUrl)
//
//        //You can pass these values dynamically through function arguments
//        val orderId = "ZPLive" + System.currentTimeMillis()
//        val amount = "100" //In Paisa
//        val order = RequestParameters(
//            Config,
//            Config.RETURN_URL,
//            orderId,
//            amount
//        )
//        zaakpayApiRequestParameters.setRequestParameters(order.getPaymentRequestParameters())
//        val checksumString: String? = order.getPaymentRequestParameters()
//            ?.let { ChecksumGenerator.getChecksumString(it) }
//        // System.out.println(checksumString);
//        // You can check the checksum string generated here.
//        val checksum: String? =
//            checksumString?.let {
//                ChecksumGenerator.calculateChecksum(Config.ZAAKPAY_SECRET_KEY,
//                    it
//                )
//            }
//        zaakpayApiRequestParameters.setChecksum(checksum)
//        return zaakpayApiRequestParameters
//    }
//
//    //***********************************************************************************************************************//
//
//
//    //****************************************** HANDLE ZAAKPAY CALLBACK RESPONSE *******************************************//
//
//    //***********************************************************************************************************************//
//    //****************************************** HANDLE ZAAKPAY CALLBACK RESPONSE *******************************************//
//    fun getResponseParameters(): Array<String>? {
//
//        //These are the response parameters you will get in the final callback from Zaakpay.
//        return arrayOf(
//            "amount", "bank", "bankid", "cardId",
//            "cardScheme", "cardToken", "cardhashid", "doRedirect", "orderId",
//            "paymentMethod", "paymentMode", "responseCode", "responseDescription",
//            "productDescription", "product1Description", "product2Description",
//            "product3Description", "product4Description", "pgTransId", "pgTransTime"
//        )
//    }
//
//    //***********************************************************************************************************************//
//
//
//    //************************************************ CHECK PAYMENT STATUS *************************************************//
//
//    //***********************************************************************************************************************//
//    //************************************************ CHECK PAYMENT STATUS *************************************************//
//    @Throws(Exception::class)
//    fun checkStatus(orderId: String?): ZaakpayApiRequestParameters? {
//        val zaakpayApiRequestParameters = ZaakpayApiRequestParameters()
//        val requestUrl: String = Config.ENVIRONMENT + Config.TRANSACTION_STATUS_API_URL
//        zaakpayApiRequestParameters.setRequestUrl(requestUrl)
//        val checkStatus = RequestParameters(orderId, Config.ZAAKPAY_MERCHANT_IDENTIFIER)
//        zaakpayApiRequestParameters.setRequestParameters(checkStatus.getTransactionStatusRequestParameters())
//        val checksumString =
//            checkStatus.getTransactionStatusRequestParameters()!!["formRequestData"]
//        // System.out.println(checksumString);
//        // You can check the checksum string generated here.
//        val checksum: String =
//            ChecksumGenerator.calculateChecksum(Config.ZAAKPAY_SECRET_KEY, checksumString)
//        zaakpayApiRequestParameters.setChecksum(checksum)
//        return zaakpayApiRequestParameters
//    }
//
//    //***********************************************************************************************************************//
//
//
//    //************************************************* INITIATE REFUND ***************************************************//
//
//    //***********************************************************************************************************************//
//    //************************************************* INITIATE REFUND ***************************************************//
//    @Throws(Exception::class)
//    fun initiateFullRefund(orderId: String?): ZaakpayApiRequestParameters? {
//        val zaakpayApiRequestParameters = ZaakpayApiRequestParameters()
//        val requestUrl: String = Config.ENVIRONMENT + Config.UPDATE_API_URL
//        zaakpayApiRequestParameters.setRequestUrl(requestUrl)
//        val updateReason = "Test Reason"
//        val refund =
//            RequestParameters(Config.ZAAKPAY_MERCHANT_IDENTIFIER, null, orderId, "14", updateReason)
//        zaakpayApiRequestParameters.setRequestParameters(refund.getFullRefundRequestParameters())
//        val checksumString: String =
//            ChecksumGenerator.getUpdateChecksumString(refund.getFullRefundRequestParameters())
//        // System.out.println(checksumString);
//        // You can check the checksum string generated here.
//        val checksum: String =
//            ChecksumGenerator.calculateChecksum(Config.ZAAKPAY_SECRET_KEY, checksumString)
//        zaakpayApiRequestParameters.setChecksum(checksum)
//        return zaakpayApiRequestParameters
//    }
//
//
//    @Throws(Exception::class)
//    fun initiatePartialRefund(orderId: String?, amount: String?): ZaakpayApiRequestParameters? {
//        val zaakpayApiRequestParameters = ZaakpayApiRequestParameters()
//        val requestUrl: String = Config.ENVIRONMENT + Config.UPDATE_API_URL
//        zaakpayApiRequestParameters.setRequestUrl(requestUrl)
//        val updateReason = "Test Reason"
//        val refund = RequestParameters(
//            Config.ZAAKPAY_MERCHANT_IDENTIFIER,
//            amount,
//            orderId,
//            "22",
//            updateReason
//        )
//        zaakpayApiRequestParameters.setRequestParameters(refund.getPartialRefundRequestParameters())
//        val checksumString: String =
//            ChecksumGenerator.getUpdateChecksumString(refund.getPartialRefundRequestParameters())
//        // System.out.println(checksumString);
//        // You can check the checksum string generated here.
//        val checksum: String =
//            ChecksumGenerator.calculateChecksum(Config.ZAAKPAY_SECRET_KEY, checksumString)
//        zaakpayApiRequestParameters.setChecksum(checksum)
//        return zaakpayApiRequestParameters
//    }
//
//    //***********************************************************************************************************************//
//
//
//    //ZaakpayApiRequestParameters contains the request parameters for Zaakpay's API that can be submitted directly through Forms.
//
//    //You can print the generated request by below function or you can use your own method also you can refer the demo sdk for more clarification.
//
//
////    public static void main(String[] args) throws Exception {
////        Transaction transaction = new Transaction() ;
////        ZaakpayApiRequestParameters zaakpayApiRequestParameters = transaction.processPayment();
////        System.out.println(zaakpayApiRequestParameters.getRequestUrl());
////        System.out.println(zaakpayApiRequestParameters.getRequestParameters());
////        System.out.println(zaakpayApiRequestParameters.getChecksum());
////    }
//}