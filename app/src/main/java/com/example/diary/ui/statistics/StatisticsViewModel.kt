package com.example.diary.ui.statistics

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.diary.model.database.dao.MeasurementDao
import com.example.diary.model.entity.Measurement
import kotlinx.coroutines.*
import java.time.LocalDateTime

class StatisticsViewModel(
    private val measurementDao: MeasurementDao
) : ViewModel() {

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main +  viewModelJob)

    val lastWeekMeasurements = measurementDao.getMeasurementsByDate(LocalDateTime.now().minusWeeks(1))
    val lastMonthMeasurements = measurementDao.getMeasurementsByDate(LocalDateTime.now().minusWeeks(1))
    val lastQuarterMeasurements = measurementDao.getMeasurementsByDate(LocalDateTime.now().minusMonths(3))
    val lastYearMeasurements = measurementDao.getMeasurementsByDate(LocalDateTime.now().minusYears(1))

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
