package com.pixieium.austtravels.auth

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat



import android.content.Context

import android.text.TextUtils
import android.util.Patterns
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.pixieium.austtravels.auth.databinding.ActivityForgotPasswordBinding


class ForgotPasswordActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityForgotPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        mBinding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        val view: View = mBinding.root
        setContentView(view)
    }

    private fun isValidEmail(target: CharSequence): Boolean {
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target)
            .matches()
    }

    private fun hideKeyboard() {
        // hide the keyboard
        val imm: InputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(mBinding.root.windowToken, 0)
    }


    private fun isEmailCorrect(target: CharSequence): String? {
        if (!isValidEmail(target)) {
            return "Please enter a valid email"
        } else {
            if (target.split("@").toTypedArray()[1] != "aust.edu") {
                return "You must enter your institutional mail"
            }
        }
        return null
    }
}

