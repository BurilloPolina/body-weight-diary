package com.example.diary.model.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.diary.model.entity.Measurement
import java.time.LocalDateTime

@Dao
interface MeasurementDao {

    @Insert
    fun addMeasurement(measurement: Measurement)

    @Update
    fun updateMeasurement(measurement: Measurement)

    @Delete
    fun deleteMeasurement(measurement: Measurement)

    @Query("SELECT * FROM measurement WHERE id = :measurementId")
    fun getMeasurement(measurementId: Int): Measurement?

    @Query("SELECT * FROM measurement ORDER BY dateOfMeasurement DESC")
    fun getAllMeasurements(): LiveData<List<Measurement>>

    @Query("SELECT * FROM measurement ORDER BY dateOfMeasurement DESC LIMIT 1")
    fun getLastMeasurement(): Measurement?

    @Query("SELECT * FROM measurement WHERE dateOfMeasurement > :startDate")
    fun getMeasurementsByDate(startDate: LocalDateTime): LiveData<List<Measurement>>
}
