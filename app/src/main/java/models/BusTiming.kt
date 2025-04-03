package models

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.pixieium.austtravels.auth.R


data class BusTiming(
    val startTime: String,
    val departureTime: String
) {
    constructor() : this(
        "",
        ""
    )
}
