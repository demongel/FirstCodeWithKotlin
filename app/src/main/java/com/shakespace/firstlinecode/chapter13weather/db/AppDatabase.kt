package com.shakespace.firstlinecode.chapter13weather.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.shakespace.firstlinecode.chapter13weather.DATABASE_NAME
import com.shakespace.firstlinecode.chapter13weather.model.City
import com.shakespace.firstlinecode.chapter13weather.model.County
import com.shakespace.firstlinecode.chapter13weather.model.Province

/**
 * created by  shakespace
 * 2019/12/14  19:53
 */
@Database(
    entities = [Province::class, City::class, County::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun placeDao(): PlaceDao

    companion object {
        // For Singleton instantiation
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context.applicationContext).also { instance = it }
            }
        }

        // Create and pre-populate the database. See this article for more details:
        // https://blog.csdn.net/I13kmsteady/article/details/88935523
        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
//                .addCallback(object : RoomDatabase.Callback() {
//                    override fun onCreate(db: SupportSQLiteDatabase) {
//                        super.onCreate(db)
//                    }
//                })
                .build()
        }
    }

}