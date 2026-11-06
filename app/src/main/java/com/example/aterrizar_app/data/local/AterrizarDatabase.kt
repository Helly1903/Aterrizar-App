package com.example.aterrizar_app.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [FavoritoEntity::class, ReservaEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AterrizarDatabase : RoomDatabase() {

    abstract fun aterrizarDao(): AterrizarDao

    companion object {
        @Volatile
        private var INSTANCE: AterrizarDatabase? = null

        fun getDatabase(context: Context): AterrizarDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    AterrizarDatabase::class.java,
                    "aterrizar_database"
                )
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}