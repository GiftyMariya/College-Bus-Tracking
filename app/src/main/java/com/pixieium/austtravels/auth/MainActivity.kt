package com.pixieium.austtravels.auth

import android.app.Dialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.pixieium.austtravels.auth.SignInActivity
import com.pixieium.austtravels.auth.databinding.ActivityMainBinding
import com.pixieium.austtravels.auth.home.HomeActivity
import models.AppUpdate
import timber.log.Timber
import java.util.*
import kotlin.system.exitProcess


class MainActivity : AppCompatActivity() {
    private val mAuth = FirebaseAuth.getInstance()
    private lateinit var mBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        mBinding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(mBinding.root)
    }

    override fun onStart() {
        super.onStart()
        checkUpdateStatus()
    }

    private fun checkUpdateStatus() {
        try {
            val database = Firebase.database
            database.getReference("updateStatus/android").get().addOnSuccessListener {
                val versionCode = it.child("versionCode").value as? String ?: "0"  // 🔹 Default to "0"
                val isAvailable = it.child("isAvailable").value as? Boolean ?: false  // 🔹 Default to false

                val appUpdate = AppUpdate(versionCode, isAvailable)

                if (appUpdate.isAvailable) {
                    showDialog(
                        getString(
                            R.string.a_new_update_is_available,
                            appUpdate.versionCode
                        )
                    )
                } else {
                    checkUserStatus()
                }
            }.addOnFailureListener { e ->
                Timber.e(e, "Failed to fetch update status")
                checkUserStatus()  // Proceed to login even if Firebase fails
            }

        } catch (e: Exception) {
            Timber.e(e, "Unexpected error in checkUpdateStatus()")
        }
    }


    private fun showDialog(title: String) {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_update_prompt)
        val body = dialog.findViewById(R.id.prompt) as TextView
        body.text = title

        val updateBtn = dialog.findViewById(R.id.update_button) as Button
        updateBtn.setOnClickListener {
            try {
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://play.google.com/store/apps/details?id=$packageName")
                    )
                )
            } catch (e: ActivityNotFoundException) {
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://play.google.com/store/apps/details?id=$packageName")
                    )
                )
            }
            moveTaskToBack(true)
            exitProcess(-1)
        }

        dialog.show()
    }

    private fun checkUserStatus() {
        val user: FirebaseUser? = mAuth.currentUser
        if (user != null) {
            val intent = Intent(this@MainActivity, HomeActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
        } else {
            val intent = Intent(applicationContext, SignInActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
        }
    }


}