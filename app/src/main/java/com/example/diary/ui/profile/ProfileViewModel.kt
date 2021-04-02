package com.example.diary.ui.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.diary.model.database.dao.MeasurementDao
import com.example.diary.model.entity.Measurement
import com.example.diary.model.prefs.Prefs
import kotlinx.coroutines.*

class ProfileViewModel(
    private val measurementDao: MeasurementDao,
    private val prefs: Prefs
) : ViewModel() {

    val lastMeasurement = MutableLiveData<Measurement>()
    val user = prefs.user

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main +  viewModelJob)

    init {
        uiScope.launch {
            lastMeasurement.value = getLastMeasurement()
        }
    }

    private suspend fun getLastMeasurement(): Measurement? {
        return withContext(Dispatchers.IO) {
            measurementDao.getLastMeasurement()
        }
    }
}
