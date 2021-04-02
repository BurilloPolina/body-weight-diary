package com.example.diary.model.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.diary.model.database.converter.LocalDateTimeConverter
import com.example.diary.model.database.dao.MeasurementDao
import com.example.diary.model.entity.Measurement

@Database(entities = [Measurement::class], version = 1)
@TypeConverters(LocalDateTimeConverter::class)
abstract class AppDataBase : RoomDatabase() {

    abstract fun getMeasurementDao(): MeasurementDao

    companion object {

        @Volatile
        private var INSTANCE: AppDataBase? = null

        fun getInstance(context: Context): AppDataBase {
            var instance = INSTANCE

            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDataBase::class.java,
                    "body_weight_diary_db"
                )
                    .build()

                INSTANCE = instance
            }

            return instance
        }
    }
}
