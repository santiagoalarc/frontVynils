package com.example.frontvynils.repositories

import android.app.Application
import android.util.Log
import com.example.frontvynils.models.Track
import com.example.frontvynils.network.CacheManager
import com.example.frontvynils.network.NetworkServiceAdapter
import org.json.JSONObject

class TracksRepository(val application: Application) {
    suspend fun refreshData(albumId: Int): List<Track> {

        val potentialResp = CacheManager.getInstance(application.applicationContext).getTracks(albumId)
        return if(potentialResp.isEmpty()){
            Log.d("Cache decision", "get from network")
            val comments = NetworkServiceAdapter.getInstance(application).getTracks(albumId)
            CacheManager.getInstance(application.applicationContext).addTracks(albumId, comments)
            comments
        } else{
            Log.d("Cache decision", "return ${potentialResp.size} elements from cache")
            potentialResp
        }
    }

    suspend fun saveData(trackJson: JSONObject, albumId: Int) {

        return NetworkServiceAdapter.getInstance(application).postTrack(trackJson, albumId)
    }
}