package com.gvpartner.com.utils


import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.Uri
import android.util.Log
import android.util.Patterns
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import android.view.inputmethod.InputMethodManager
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.google.android.material.snackbar.Snackbar
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern


object CommonUtils {

    private val VALID_NUMBER = Pattern.compile("[0-9]+")


    fun setFragment(
        fragment: Fragment,
        removeStack: Boolean,
        activity: FragmentActivity?,
        mContainer: Int
    ) {
        val fragmentManager = activity?.supportFragmentManager
        val ftTransaction = fragmentManager?.beginTransaction()
        if (removeStack) {
            fragmentManager?.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
            ftTransaction?.replace(mContainer, fragment)
            ftTransaction?.addToBackStack(null)
        } else {
            ftTransaction?.replace(mContainer, fragment)
            ftTransaction?.addToBackStack(null)
        }
        ftTransaction?.commit()
    }


    fun isValidEmail(target: CharSequence?): Boolean {
        if (target == null) {
            return false
        } else {

            val pattern: Pattern
            val matcher: Matcher
            val EMAIL_PATTERN =
                "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"
            pattern = Pattern.compile(EMAIL_PATTERN)
            matcher = pattern.matcher(target)
            return matcher.matches()
            // return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    fun hideSoftKeyboard(activity: Activity) {
        try {
            val inputMethodManager =
                activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(activity.currentFocus!!.windowToken, 0)
        } catch (e: Throwable) {
            e.printStackTrace()
        }

    }


    fun View.snackbar(message: String) {
        Snackbar.make(this, message, Snackbar.LENGTH_LONG).show()
    }

    /*createCustomDialog() is used to create dialog*/
    fun createCustomDialog(context: Context, layoutResourceId: View): Dialog {
        val dialog = Dialog(context, android.R.style.Theme_Translucent_NoTitleBar)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(layoutResourceId)
        val layoutParams = dialog.window!!.attributes
        layoutParams.dimAmount = .7f
        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
        dialog.window!!.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        dialog.window!!.attributes = layoutParams
        return dialog
    }
    fun isValidMail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun isPasswordConfirmPasswordSame(password : String,confirmPassword : String): Boolean {
        return password.equals(confirmPassword)
    }

//
//    fun exitDialog(mContext: Context) {
//        val dialogLogoutBinding = DataBindingUtil.inflate<ViewDataBinding>(LayoutInflater.from(mContext), R.layout.dialog_logout, null, false)
//        val exitDialog = CommonUtils.createCustomDialog(mContext, dialogLogoutBinding.getRoot())
//        dialogLogoutBinding.tvMessage.setText(R.string.exit_from_app)
//        dialogLogoutBinding.tvYes.setOnClickListener(View.OnClickListener {
//            val intent = Intent(Intent.ACTION_MAIN)
//            intent.addCategory(Intent.CATEGORY_HOME)
//            mContext.startActivity(intent)
//            System.exit(0)
//        })
//        dialogLogoutBinding.tvNo.setOnClickListener(View.OnClickListener { exitDialog.dismiss() })
//        exitDialog.show()
//    }


    /* public static void exitDialog(final Context mContext) {
        DialogLogoutBinding dialogLogoutBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.dialog_logout, null, false);
        final Dialog exitDialog = CommonUtils.createCustomDialog(mContext, dialogLogoutBinding.getRoot());
        dialogLogoutBinding.tvMessage.setText(R.string.exit_from_app);
        dialogLogoutBinding.tvYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                mContext.startActivity(intent);
                System.exit(0);
            }
        });
        dialogLogoutBinding.tvNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exitDialog.dismiss();
            }
        });
        exitDialog.show();
    }*/


    fun isVin(s: String): Boolean {
        return VALID_NUMBER.matcher(s).matches()
    }


    /**
     * checking internet connection
     */
    @SuppressLint("MissingPermission")
    fun isOnline(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = cm.activeNetworkInfo
        return netInfo != null && netInfo.isConnected
    }

    fun isValidPhone(target: String?): Boolean {
        return if (target == null) {
            false
        } else {
            Patterns.PHONE.matcher(target).matches()
        }
    }

    fun printLog(context: Context?, msg: String) {
        if (context != null) {
            Log.e(context.javaClass.name, msg)
        } else {
            Log.e("DEBUG", msg)
        }
    }

    fun getTimeStamp(str_date: String): String {
        var date: Date? = null
        try {
            date = SimpleDateFormat("MM-dd-yyyy HH:mm").parse(str_date) as Date
        } catch (e1: ParseException) {
            e1.printStackTrace()
        }

        assert(date != null)
        return date!!.time.toString()
    }


    fun getTimeStampDate(str_date: String): String {
        var str_date = str_date
        val format = SimpleDateFormat("dd-MMM-yy HH:mm")
        if (str_date.length < 13) {
            str_date = (java.lang.Long.valueOf(str_date) * 1000).toString() + ""
        }
        var date: Date? = null
        try {
            date = Date(java.lang.Long.valueOf(str_date))
        } catch (e: Exception) {
            e.printStackTrace()
        }

        assert(date != null)
        var timestampValue = ""
        try {
            timestampValue = format.format(date)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return timestampValue
    }


    fun showSnack(view: View, message: String) {
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show()
    }


//    fun commonAlert(mContext: Context, message: String, fragment: Fragment) {
//        val dialogBinding = DataBindingUtil.inflate<ViewDataBinding>(LayoutInflater.from(mContext), R.layout.normal_alert, null, false)
//        val commonDialog = CommonUtils.createCustomDialog(mContext, dialogBinding.getRoot())
//        dialogBinding.tvMessage.setText(message)
//        dialogBinding.tvYes.setOnClickListener(View.OnClickListener {
//            commonDialog.dismiss()
//            CommonUtils.setFragment(fragment, true, mContext as FragmentActivity, R.id.frameContainer)
//        })
//
//        commonDialog.show()
//    }


//    fun commonAlertBackStack(mContext: Activity, message: String) {
//        val dialogBinding = DataBindingUtil.inflate<ViewDataBinding>(LayoutInflater.from(mContext), R.layout.normal_alert, null, false)
//        val commonDialog = CommonUtils.createCustomDialog(mContext, dialogBinding.getRoot())
//        dialogBinding.tvMessage.setText(message)
//        dialogBinding.tvYes.setOnClickListener(View.OnClickListener {
//            commonDialog.dismiss()
//            (mContext as DashboardFragment).onBackPressed()
//        })
//
//        commonDialog.show()
//    }


    fun call(context: Context, phone: String) {
        try {
            val callIntent = Intent(Intent.ACTION_CALL)
            callIntent.data = Uri.parse("tel:$phone")
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.CALL_PHONE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }
            context.startActivity(callIntent)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun animate(): Animation {
        val anim = AlphaAnimation(0.0f, 1.0f)
        anim.duration = 80 //You can manage the time of the blink with this parameter
        anim.startOffset = 30
        anim.repeatMode = Animation.REVERSE
        anim.repeatCount = Animation.INFINITE
        return anim
    }

    /* public static void alertMessage(final Context mContext, String message, final Fragment fragment) {
        DialogLogoutBinding dialogLogoutBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.dialog_logout, null, false);
        final Dialog exitDialog = CommonUtils.createCustomDialog(mContext, dialogLogoutBinding.getRoot());
        dialogLogoutBinding.tvMessage.setText(message);
        dialogLogoutBinding.tvYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               exitDialog.dismiss();
            }
        });
        dialogLogoutBinding.tvNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exitDialog.dismiss();
                CommonUtils.setFragment(fragment, true, (FragmentActivity) mContext, R.id.flContainerHome);

            }
        });
        exitDialog.show();
    }*/


    /**
     * Here is the key method to apply the animation
     */
    fun setAnimation(viewToAnimate: View, context: Context) {
        /* Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
        viewToAnimate.startAnimation(animation);*/

        val anim = ScaleAnimation(
            0.0f,
            1.0f,
            0.0f,
            1.0f,
            Animation.RELATIVE_TO_SELF,
            0.5f,
            Animation.RELATIVE_TO_SELF,
            0.5f
        )
        anim.duration =
            Random().nextInt(501).toLong()//to make duration random number between [0,501)
        viewToAnimate.startAnimation(anim)

    }






}