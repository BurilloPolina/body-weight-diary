package com.example.diary.model.database.converter

import androidx.room.TypeConverter
import java.time.LocalDateTime

class LocalDateTimeConverter {

    @TypeConverter
    fun toLocalDateTime(stringDateTime: String) = LocalDateTime.parse(stringDateTime)

    @TypeConverter
    fun fromLocalDateTime(datetime: LocalDateTime) = datetime.toString()
}