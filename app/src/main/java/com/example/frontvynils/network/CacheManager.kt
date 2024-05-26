package com.example.frontvynils.network

import android.content.Context
import com.example.frontvynils.models.Track

class CacheManager(context: Context) {
    companion object{
        var instance: CacheManager? = null
        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                instance ?: CacheManager(context).also {
                    instance = it
                }
            }
    }
    private var tracks: HashMap<Int, List<Track>> = hashMapOf()

    fun addTracks(albumId: Int, comment: List<Track>){
        if (tracks.containsKey(albumId)){
            tracks[albumId] = comment
        }
    }
    fun getTracks(albumId: Int) : List<Track>{
        return if (tracks.containsKey(albumId)) tracks[albumId]!! else listOf()
    }

}