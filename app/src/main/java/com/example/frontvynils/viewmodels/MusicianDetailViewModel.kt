package com.example.frontvynils.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.frontvynils.models.Musician
import com.example.frontvynils.repositories.MusicianRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MusicianDetailViewModel(application: Application, musicianId: Int) :
    AndroidViewModel(application) {

    private val musicianRepository = MusicianRepository(application)

    private val _musician = MutableLiveData<Musician>()

    val musician: LiveData<Musician>
        get() = _musician


    private var _eventNetworkError = MutableLiveData<Boolean>(false)

    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError

    private var _isNetworkErrorShown = MutableLiveData<Boolean>(false)

    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown

    val id: Int = musicianId

    init {
        refreshDataFromNetwork()
    }

    private fun refreshDataFromNetwork() {
        try {
            viewModelScope.launch(Dispatchers.Default) {
                withContext(Dispatchers.IO) {
                    val data = musicianRepository.refreshData(id)
                    _musician.postValue(data)
                }
                _eventNetworkError.postValue(false)
                _isNetworkErrorShown.postValue(false)
            }
        } catch (e: Exception) {
            _eventNetworkError.value = true
        }
    }

    fun onNetworkErrorShown() {
        _isNetworkErrorShown.value = true
    }

    class Factory(val app: Application, val musicianId: Int) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MusicianDetailViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MusicianDetailViewModel(app, musicianId) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}
