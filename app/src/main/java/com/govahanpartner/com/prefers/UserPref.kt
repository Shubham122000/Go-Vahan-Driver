package com.prefers
import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.govahanpartner.com.model.RegisterResponseModelUser
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class UserPref @Inject constructor(@ApplicationContext context: Context){

    private val preferences: SharedPreferences =
        context.getSharedPreferences("userPref", Context.MODE_PRIVATE)
    private val gson: Gson = Gson()

    var user: RegisterResponseModelUser
        get() = gson.fromJson<Any>(preferences.getString("user", null), RegisterResponseModelUser::class.java) as RegisterResponseModelUser
        set(user) {
            val gson = Gson()
            val loginRes = gson.toJson(user)
            preferences.edit().putString("user", loginRes).apply()
        }

    var isLogin: Boolean
        get() = preferences.getBoolean("isLoginA", false)
        set(login) = preferences.edit().putBoolean("isLoginA", login).apply()

    var isFirstTime: Boolean
        get() = preferences.getBoolean("isFirstTime", false)
        set(login) = preferences.edit().putBoolean("isFirstTime", login).apply()

    var isContactFirstTime: Boolean
        get() = preferences.getBoolean("isContactFirstTime", false)
        set(login) = preferences.edit().putBoolean("isContactFirstTime", login).apply()

//    var board_id: String?
//        get() = preferences.getString("board_id", "")
//        set(login) = preferences.edit().putString("isContactFirstTime", board_id).apply()

    fun setBoardId(board_id: String) {
        preferences.edit().putString("board_id", board_id).apply()
    }

    var firebaseToken: String?
        get() = preferences.getString("firebaseToken", null)
        set(firebase_token) = preferences.edit().putString("firebaseToken", firebase_token).apply()

    fun getBoardId(): String? {
        return preferences.getString("board_id", null)
    }

    fun setLanguageId(language_id: String) {
        preferences.edit().putString("language_id", language_id).apply()
    }

    fun getLanguageId(): String? {
        return preferences.getString("language_id", null)
    }

    fun setImage(class_id: String) {
        preferences.edit().putString("image", class_id).apply()
    }


    fun setRole(role: String?) {
        preferences.edit().putString("role", role).apply()
    }

    fun getRole(): String? {
        return preferences.getString("role", null)
    }


    fun setName(name: String) {
        preferences.edit().putString("name", name).apply()
    }
    fun setToken(token: String?) {
        preferences.edit().putString("token", token).apply()
    }

    fun getToken(): String? {
        return preferences.getString("token", null)
    }
        fun setusertype(user_type:String?) {
            preferences.edit().putString("user_type",user_type.toString()).apply()
        }

        fun getusertype(): String? {
            return preferences.getString("user_type", null)
        }
        fun setusertype1(user_type:String?) {
            preferences.edit().putString("user_type",user_type.toString()).apply()
        }

    fun getusertype1(): String? {
        return preferences.getString("user_type", null)
    }

    fun setid(token: String?) {
        preferences.edit().putString("id", token).apply()
    }

    fun getid(): String? {
        return preferences.getString("id", null)
    }

    fun setdriver_license(token: String?) {
        preferences.edit().putString("driving_licence", token).apply()
    }

    fun getdriver_license(): String? {
        return preferences.getString("driving_licence", "")
    }

    fun setUserId(user_id: String) {
        preferences.edit().putString("user_id", user_id).apply()
    }

    fun getuserid(): String? {
        return preferences.getString("user_id", null)
    }

    fun getImage(): String? {
        return preferences.getString("image", null)
    }

    fun setEmail(email: String?) {
        preferences.edit().putString("email", email).apply()
    }
    fun getEmail(): String? {
        return preferences.getString("email", null)
    }
    fun setMobile(mobile: String?) {
        preferences.edit().putString("mobile", mobile).apply()
    }
    fun getMobile(): String? {
        return preferences.getString("mobile", null)
    }
    fun getName(): String? {
        return preferences.getString("name", null)
    }

    fun getServiceStatus(): Int {
        return preferences.getInt("serviceStatus", 1)
    }

    fun setServiceStatus(status: Int) {
        preferences.edit().putInt("serviceStatus", status).apply()
    }


    fun setHelpNumber(helpNumber: String) {
        preferences.edit().putString("helpNumber", helpNumber).apply()
    }

    fun getHelpNumber(): String? {
        return preferences.getString("HelpNumber", null)
    }


    fun clearPref() {
        preferences.edit().clear().apply()
    }

}