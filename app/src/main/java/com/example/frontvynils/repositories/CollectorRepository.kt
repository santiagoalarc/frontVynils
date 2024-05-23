package com.example.frontvynils.repositories

import android.app.Application
import com.example.frontvynils.models.Collector
import com.example.frontvynils.network.NetworkServiceAdapter


class CollectorRepository(val application: Application) {
    suspend fun refreshListData(): List<Collector> {

        return NetworkServiceAdapter.getInstance(application).getCollectors()
    }

    suspend fun refreshData(collectorId: Int): Collector {

        return NetworkServiceAdapter.getInstance(application).getCollector(collectorId)
    }

}