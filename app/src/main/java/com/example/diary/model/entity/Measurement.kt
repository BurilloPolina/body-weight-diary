package com.example.diary.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "measurement")
class Measurement(
    @PrimaryKey
    val id: Int,

    var weight: Float,

    val dateOfMeasurement: LocalDateTime,

    var difference: Float,

    val indexWeight: Float
)