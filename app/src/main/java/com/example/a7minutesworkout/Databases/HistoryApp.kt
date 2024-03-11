package com.example.a7minutesworkout.Databases

import android.app.Application

class HistoryApp : Application() {
    val db by lazy {
        HistoryDatabase.getInstance(this)
    }
}