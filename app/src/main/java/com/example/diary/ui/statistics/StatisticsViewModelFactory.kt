package com.example.diary.ui.statistics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.diary.model.database.dao.MeasurementDao

class StatisticsViewModelFactory(
    private val measurementDao: MeasurementDao
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StatisticsViewModel::class.java)) {
            return StatisticsViewModel(measurementDao) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
