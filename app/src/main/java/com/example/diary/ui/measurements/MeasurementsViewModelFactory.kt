package com.example.diary.ui.measurements

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.diary.model.database.dao.MeasurementDao
import com.example.diary.model.prefs.Prefs

class MeasurementsViewModelFactory(
    private val measurementDao: MeasurementDao,
    private val prefs: Prefs
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MeasurementsViewModel::class.java)) {
            return MeasurementsViewModel(measurementDao, prefs) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
