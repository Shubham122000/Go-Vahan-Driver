package com.prefers

//open class SharedPreference(var context: Context) {
//    private var pref: SharedPreference? = null
//    private  var editor: SharedPreferences.Editor
//
//    var preferences: SharedPreferences =
//        context.getSharedPreferences(ApiConstant.MY_PREFERENCES,Context.MODE_PRIVATE)
//
//
//    init {
//        editor = preferences.edit()
//    }
//
//    fun getInstance(ctx: Context): SharedPreference {
//        if (pref == null) {
//            pref = SharedPreference(ctx)
//        }
//        return pref as SharedPreference
//    }
//
//    fun putString(key: String, value: String) {
//        editor.putString(key, value)
//        editor.commit()
//    }
//
//    fun putLong(key: String, value: Long?) {
//
//        editor.putLong(key, value!!)
//        editor.commit()
//    }
//
//
//    fun putBoolean(key: String, value: Boolean?) {
//        editor.putBoolean(key, value!!)
//        editor.commit()
//    }
//
//    fun putInteger(key: String, value: Int) {
//        editor.putInt(key, value)
//        editor.commit()
//    }
//
//    fun putFloat(key: String, value: Float?) {
//        editor.putFloat(key, value!!)
//        editor.commit()
//    }
//
//
//    fun getString(key: String, defValue: String): String? {
//        return preferences.getString(key, defValue)
//    }
//
//    fun getLong(key: String, defValue: Long?): Long {
//        return preferences.getLong(key, defValue!!)
//    }
//
//    fun getBoolean(key: String, defValue: Boolean?): Boolean {
//        return preferences.getBoolean(key, defValue!!)
//    }
//
//    fun getInteger(key: String, defValue: Int): Int {
//        return preferences.getInt(key, defValue)
//    }
//
//    fun getFloat(key: String, defValue: Float?): Float {
//        return preferences.getFloat(key, defValue!!)
//    }
//
//    fun deletePreference() {
//        editor.clear()
//        editor.commit()
//    }
//
//    fun deletePreference(key: String) {
//        editor.remove(key)
//        editor.commit()
//    }
//
//    fun ClearSharprefence() {
//        editor.putBoolean(Constants.SESSION, false).commit()
//        editor.putString(Constants.USER_ID, "").commit()
//        editor.putString(Constants.USER_NAME, "").commit()
//        editor.putString(Constants.EMAIL, "").commit()
//        editor.putString(Constants.PROFILE_IMAGE, "").commit()
////                preferences.edit().clear()
//        editor.commit()
//    }
//
//
//}