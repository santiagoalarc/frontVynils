package com.example.frontvynils.repositories

import android.app.Application
import com.example.frontvynils.models.Musician
import com.example.frontvynils.network.NetworkServiceAdapter

class MusicianRepository(val application: Application) {
    suspend fun refreshData(): List<Musician> {

        return NetworkServiceAdapter.getInstance(application).getMusicians()
    }

}