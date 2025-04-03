package com.pixieium.austtravels.auth.utils

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.pixieium.austtravels.auth.R



import retrofit2.http.GET
import retrofit2.http.Query


interface NotificationApi {

    @GET("send-volunteer")
    suspend fun notifyVolunteers(
        @Query("bus") busName: String,
        @Query("title") title: String,
        @Query("message") message: String
    )

    @GET("send-users")
    suspend fun notifyUsers(
        @Query("bus") busName: String,
        @Query("title") title: String,
        @Query("message") message: String
    )
}