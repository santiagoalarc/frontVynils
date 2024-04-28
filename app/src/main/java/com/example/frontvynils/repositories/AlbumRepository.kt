package com.example.frontvynils.repositories

import android.app.Application
import com.example.frontvynils.models.Album
import com.example.frontvynils.network.NetworkServiceAdapter

class AlbumRepository(val application: Application) {
    suspend fun refreshData(): List<Album> {
        //Determinar la fuente de datos que se va a utilizar. Si es necesario consultar la red, ejecutar el siguiente código
        return NetworkServiceAdapter.getInstance(application).getAlbums()
    }

    suspend fun refreshData2(albumId: Int): Album {
        //Determinar la fuente de datos que se va a utilizar. Si es necesario consultar la red, ejecutar el siguiente código
        return NetworkServiceAdapter.getInstance(application).getAlbum(albumId)
    }
}