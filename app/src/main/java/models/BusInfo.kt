package models

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.pixieium.austtravels.auth.R



data class BusInfo(
    var name: String,
    var timing: ArrayList<BusTiming>,
) {
    constructor() : this(
        "",
        ArrayList()
    )
}