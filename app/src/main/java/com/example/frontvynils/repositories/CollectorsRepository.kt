package com.example.frontvynils.repositories

import android.app.Application
import com.example.frontvynils.models.Collector
import com.example.frontvynils.network.NetworkServiceAdapter


class CollectorsRepository(val application: Application) {
    suspend fun refreshData(): List<Collector> {
        //Determinar la fuente de datos que se va a utilizar. Si es necesario consultar la red, ejecutar el siguiente código
        return NetworkServiceAdapter.getInstance(application).getCollectors()
    }

}