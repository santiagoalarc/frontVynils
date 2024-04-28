package com.example.frontvynils.repositories

import android.app.Application
import com.example.frontvynils.models.Track
import com.example.frontvynils.network.NetworkServiceAdapter

class TracksRepository(val application: Application) {
    suspend fun refreshData(albumId: Int): List<Track> {
        //var potentialResp = CacheManager.getInstance(application.applicationContext).getTracks(albumId)
        //if(potentialResp.isEmpty()){
        //    Log.d("Cache decision", "get from network")
        //    CacheManager.getInstance(application.applicationContext).addTracks(albumId, tracks)
        return NetworkServiceAdapter.getInstance(application).getTracks(albumId)
        //}
        //else{
        //    Log.d("Cache decision", "return ${potentialResp.size} elements from cache")
        //    return potentialResp
        //}
    }
}