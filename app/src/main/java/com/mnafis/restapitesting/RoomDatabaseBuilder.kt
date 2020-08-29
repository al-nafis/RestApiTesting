package com.mnafis.restapitesting

import android.content.Context
import androidx.room.Room
import com.mnafis.restapitesting.room_database.AppDatabase

class RoomDatabaseBuilder {
    companion object {
        private var applicationContext: Context? = null
        private var dataBase: AppDatabase? = null

        fun getDatabase(): AppDatabase? {
            return dataBase?.let {
                dataBase
            } ?: run {
                applicationContext?.let {
                    Room.databaseBuilder(
                        applicationContext!!,
                        AppDatabase::class.java,
                        "app_database"
                    ).allowMainThreadQueries().build()
                } ?: run {
                    null
                }
            }
        }

        fun setContext(applicationContext: Context) {
            this.applicationContext = applicationContext
        }
    }
}