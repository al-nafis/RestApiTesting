package com.mnafis.restapitesting

import android.app.Application

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        RoomDatabaseBuilder.setContext(this)
    }
}