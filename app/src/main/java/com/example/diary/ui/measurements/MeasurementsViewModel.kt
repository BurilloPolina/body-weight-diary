package com.example.diary.ui.measurements

import androidx.lifecycle.ViewModel
import com.example.diary.model.database.dao.MeasurementDao
import com.example.diary.model.entity.Measurement
import com.example.diary.model.prefs.Prefs
import kotlinx.coroutines.*
import java.time.LocalDateTime

class MeasurementsViewModel(
    private val measurementDao: MeasurementDao,
    private val prefs: Prefs
) : ViewModel() {

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main +  viewModelJob)

    val measurements = measurementDao.getAllMeasurements()

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun addMeasurement(weight: Float) {
        uiScope.launch {
            addNewMeasurement(weight)
        }
    }

    fun deleteMeasurement(measurement: Measurement) {
        uiScope.launch {
            removeMeasurement(measurement)
        }
    }

    fun editMeasurement(measurement: Measurement, newWeight: Float) {
        uiScope.launch {
            updateMeasurement(measurement, newWeight)
        }
    }

    private suspend fun addNewMeasurement(weight: Float) {
        withContext(Dispatchers.IO) {
            val lastMeasurement = measurementDao.getLastMeasurement()

            val id: Int
            val difference: Float
            if (lastMeasurement != null) {
                id = lastMeasurement.id + 1
                difference = weight - lastMeasurement.weight
            } else {
                id = 1
                difference = 0F
            }

            prefs.user?.let {
                measurementDao.addMeasurement(
                    Measurement(
                        id,
                        weight,
                        LocalDateTime.now(),
                        difference,
                        weight / (it.height * it.height)
                    )
                )
            }
        }
    }

    private suspend fun updateMeasurement(measurement: Measurement, newWeight: Float) {
        withContext(Dispatchers.IO) {
            val newDiff = measurement.difference + newWeight - measurement.weight
            measurement.apply {
                weight = newWeight
                difference = newDiff
            }

            measurementDao.updateMeasurement(measurement)
        }
    }

    private suspend fun removeMeasurement(measurement: Measurement) {
        withContext(Dispatchers.IO) {
            measurementDao.deleteMeasurement(measurement)
        }
    }
}
