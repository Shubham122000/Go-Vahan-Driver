package com.govahanpartner.com.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class DateFormat {
    companion object {
        fun dateform(date: String?): String? {
            var result = ""
            val sourceFormat = SimpleDateFormat("yyyy-mm-dd")
            sourceFormat.timeZone = TimeZone.getTimeZone("UTC")
            var parsed: Date? = null // => Date is in UTC now
            parsed = try {
                sourceFormat.parse(date)
            } catch (e: ParseException) {
                e.printStackTrace()
                return ""
            }
            val destFormat = SimpleDateFormat("dd-MMM-yyyy")
            destFormat.timeZone = TimeZone.getDefault()
            result = destFormat.format(parsed)
            return result
        }
        fun eventdate(date: String?): String? {
            var result = ""
            val sourceFormat = SimpleDateFormat("yyyy-mm-dd")
            sourceFormat.timeZone = TimeZone.getTimeZone("UTC")
            var parsed: Date? = null // => Date is in UTC now
            parsed = try {
                sourceFormat.parse(date)
            } catch (e: ParseException) {
                e.printStackTrace()
                return ""
            }
            val destFormat = SimpleDateFormat("dd-mm-yyyy")
            destFormat.timeZone = TimeZone.getDefault()
            result = destFormat.format(parsed)
            return result
        }
        fun TimeFormat(date: String?): String? {
            var result = ""

            val sourceFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            sourceFormat.timeZone = TimeZone.getTimeZone("UTC")
            var parsed: Date? = null // => Date is in UTC now
            parsed = try {
                sourceFormat.parse(date)
            } catch (e: ParseException) {
                e.printStackTrace()
                return ""
            }
            val destFormat = SimpleDateFormat("yyyy-MM-dd ")
            destFormat.timeZone = TimeZone.getDefault()
            result = destFormat.format(parsed)
            return result
        }

    }
}