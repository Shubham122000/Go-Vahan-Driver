package com.govahanpartner.com.payment

class ZaakpayApiRequestParameter {
    private var requestUrl: String? = null

    private var checksum: String? = null

    private var requestParameters: Map<String, String> = HashMap()

    fun getRequestUrl(): String? {
        return requestUrl
    }

    fun setRequestUrl(requestUrl: String?) {
        this.requestUrl = requestUrl
    }

    fun getChecksum(): String? {
        return checksum
    }

    fun setChecksum(checksum: String?) {
        this.checksum = checksum
    }

    fun getRequestParameters(): Map<String, String>? {
        return requestParameters
    }

    fun setRequestParameters(requestParameters: Map<String, String>) {
        this.requestParameters = requestParameters
    }
}