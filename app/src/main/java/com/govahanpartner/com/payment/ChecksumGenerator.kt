//package com.transportationapp.payment
//
//import java.util.*
//import javax.crypto.Mac
//import javax.crypto.spec.SecretKeySpec
//
//object ChecksumGenerator {
//    private fun toHex(bytes: ByteArray): String {
//        val buffer = StringBuilder(bytes.size * 2)
//        var str: String
//        for (b in bytes) {
//            str = Integer.toHexString(b.toInt())
//            val len = str.length
//            if (len == 8) {
//                buffer.append(str.substring(6))
//            } else if (str.length == 2) {
//                buffer.append(str)
//            } else {
//                buffer.append("0$str")
//            }
//        }
//        return buffer.toString()
//    }
//
//    @Throws(Exception::class)
//    fun verifyChecksum(secretKey: String, checksumString: String, checksum: String): Boolean? {
//        return calculateChecksum(secretKey, checksumString) == checksum
//    }
//
//    fun getChecksumString(requestParam: TreeMap<String?, String?>): String {
//        var checksumString = ""
//        for ((key, value): Map.Entry<String, String> in requestParam.entrySet()) {
//            checksumString = "$checksumString$key=$value"
//            checksumString = "$checksumString&"
//            //This will create the checksum string against every parameter.
//        }
//        return checksumString
//    }
//
//    @Throws(Exception::class)
//    fun calculateChecksum(secretKey: String, allParamValue: String): String {
//        val dataToEncryptByte = allParamValue.toByteArray()
//        val keyBytes = secretKey.toByteArray()
//        val secretKeySpec = SecretKeySpec(keyBytes, "HmacSHA256")
//        val mac: Mac = Mac.getInstance("HmacSHA256")
//        mac.init(secretKeySpec)
//        val checksumByte: ByteArray = mac.doFinal(dataToEncryptByte)
//        return toHex(checksumByte)
//    }
//
//
//    fun getUpdateChecksumString(requestParam: Map<String?, String>): String? {
//        var checksumString = ""
//        for ((_, value): Map.Entry<String?, String> in requestParam) {
//            checksumString = checksumString + "\'" + value + "\'"
//            //This will create the checksum string against every parameter.
//        }
//        return checksumString
//    }
//}