package com.arwan.arwansa_scanmecalculator.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.arwan.arwansa_scanmecalculator.data.entity.ExpressionEntity

@Database(
    entities = [ExpressionEntity::class],
    version = AppDatabase.DATABASE_VERSION
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun expressionsDao(): ExpressionsDao

    companion object {
        const val DATABASE_VERSION: Int = 1
        @Volatile
        private var instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase =
            instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also {
                    instance = it
                }
            }

        private fun buildDatabase(appContext: Context) =
            Room.databaseBuilder(appContext, AppDatabase::class.java, "characters")
                .fallbackToDestructiveMigration()
                .build()
    }
}