package com.example.frontvynils.repositories

import android.app.Application
import com.example.frontvynils.models.Album
import com.example.frontvynils.network.NetworkServiceAdapter
import org.json.JSONObject

class AlbumRepository(val application: Application) {
    suspend fun refreshListData(): List<Album> {

        return NetworkServiceAdapter.getInstance(application).getAlbums()
    }

    suspend fun refreshData(albumId: Int): Album {

        return NetworkServiceAdapter.getInstance(application).getAlbum(albumId)
    }

    suspend fun saveData(album: JSONObject) {

        return NetworkServiceAdapter.getInstance(application).postAlbum(album)
    }
}