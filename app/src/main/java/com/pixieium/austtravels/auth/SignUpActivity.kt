package com.pixieium.austtravels.auth

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.content.Intent
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserInfo
import com.pixieium.austtravels.auth.databinding.ActivitySignUpBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.*



class SignUpActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivitySignUpBinding
    private val mDatabase: AuthRepository = AuthRepository()
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivitySignUpBinding.inflate(layoutInflater)
        val view: View = mBinding.root
        setContentView(view)
        mAuth = FirebaseAuth.getInstance()



        mBinding.signup.setOnClickListener {
            try {
                hideKeyboard()
            } catch (e: Exception) {
                Timber.e(e, e.localizedMessage)
            }
            createNewUser()
        }

    }

    private fun startLoading() {
        mBinding.progressBar.visibility = View.VISIBLE
        mBinding.signup.isEnabled = false
    }

    private fun stopLoading() {
        mBinding.progressBar.visibility = View.GONE
        mBinding.signup.isEnabled = true
    }


    private fun hideKeyboard() {
        // hide the keyboard
        val imm: InputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(mBinding.root.windowToken, 0)
    }

    private fun createNewUser() {
        val email = mBinding.eduMail.editText?.text.toString()
        val password = mBinding.password.editText?.text.toString()
        val userName = mBinding.name.editText?.text.toString()

        val universityId = mBinding.universityId.editText?.text.toString()
        val userImage = "https://avatars.dicebear.com/api/bottts/${userName}.svg"
        val userInfo =
            models.UserInfo(email, password, userName,  universityId, userImage)

        val errorMessage = userInfo.validateInput()

        if (errorMessage != null) {
            Toast.makeText(this, "Please enter your information correctly", Toast.LENGTH_SHORT)
                .show()
        } else {
            startLoading()
            mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(
                    this
                ) { task ->
                    if (task.isSuccessful) {
                        val user = mAuth.currentUser
                        // uses the dicebears http api to get an image
                        // refer to https://avatars.dicebear.com/docs/http-api
                        if (user != null) {
                            lifecycleScope.launch {
                                if (mDatabase.saveNewUserInfo(userInfo, user.uid,user)) {
                                    sentVerificationEmail()

                                } else {
                                    Toast.makeText(
                                        this@SignUpActivity,
                                        "Something went wrong! Couldn't create your profile",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                                stopLoading()
                            }
                        }
                    }
                }
                .addOnFailureListener(this) {
                    stopLoading()
                    Toast.makeText(
                        applicationContext, it.localizedMessage,
                        Toast.LENGTH_SHORT
                    ).show()
                }
        }


    }

    private fun sentVerificationEmail() {
        val currentUser: FirebaseUser? = mAuth.currentUser
        currentUser?.sendEmailVerification()?.addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                Toast.makeText(
                    this@SignUpActivity,
                    "Email verification link sent to your email",
                    Toast.LENGTH_SHORT
                ).show()

                Firebase.auth.signOut()
                val intent =
                    Intent(this@SignUpActivity, SignInActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                startActivity(intent)
            }

        }
    }







}
