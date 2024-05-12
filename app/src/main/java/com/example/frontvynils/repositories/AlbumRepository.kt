package com.example.frontvynils.repositories

import android.app.Application
import com.example.frontvynils.models.Album
import com.example.frontvynils.network.NetworkServiceAdapter

class AlbumRepository(val application: Application) {
    suspend fun refreshListData(): List<Album> {

        return NetworkServiceAdapter.getInstance(application).getAlbums()
    }

    suspend fun refreshData(albumId: Int): Album {

        return NetworkServiceAdapter.getInstance(application).getAlbum(albumId)
    }
}