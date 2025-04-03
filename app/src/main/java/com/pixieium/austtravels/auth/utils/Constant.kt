package com.pixieium.austtravels.auth.utils

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.pixieium.austtravels.auth.R



object Constant {

    const val GENERAL_NOTIFICATION = "general"
    const val USER_NOTIFY = "_USER"

    // add / at the end i.e. https://address.url/
    const val BASE_URL = "https://aust-travels.herokuapp.com/"

    // home activity
    const val REQUEST_LIVE_TRACK = 0
    const val REQUEST_DIRECTIONS = 1
    const val REQUEST_SHARE_LOCATION = 2
    const val MSG_START_TIMER = 0
    const val MSG_STOP_TIMER = 1
    const val MSG_UPDATE_TIMER = 2
    const val NOTIFICATION_ID = 12044

    const val PACKAGE_NAME = "com.pixieium.austtravels.auth"
}