package com.example.frontvynils.network

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.frontvynils.constants.StaticConstants
import com.example.frontvynils.models.Album
import com.example.frontvynils.models.Collector
import com.example.frontvynils.models.Track
import org.json.JSONArray
import org.json.JSONObject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class NetworkServiceAdapter(context: Context) {

    companion object {
        private var instance: NetworkServiceAdapter? = null
        fun getInstance(context: Context) = instance ?: synchronized(this) {
            instance ?: NetworkServiceAdapter(context).also {
                instance = it
            }
        }
    }

    private val requestQueue: RequestQueue by lazy {
        // applicationContext keeps you from leaking the Activity or BroadcastReceiver if someone passes one in.
        Volley.newRequestQueue(context.applicationContext)
    }

    suspend fun getAlbums() = suspendCoroutine<List<Album>> { cont ->
        requestQueue.add(getRequest(
            path = "albums",
            responseListener = { response ->
                val responseArray = JSONArray(response)
                val list = mutableListOf<Album>()
                for (i in 0 until responseArray.length()) {
                    val item = responseArray.getJSONObject(i)
                    list.add(
                        i, Album(
                            albumId = item.getInt("id"),
                            name = item.getString("name"),
                            cover = item.getString("cover"),
                            recordLabel = item.getString("recordLabel"),
                            releaseDate = item.getString("releaseDate"),
                            genre = item.getString("genre"),
                            description = item.getString("description")
                        )
                    )
                }
                cont.resume(list)
            },
            errorListener = {
                cont.resumeWithException(it)
            }
        ))
    }

    suspend fun getAlbum(albumId: Int) = suspendCoroutine { cont ->
        requestQueue.add(
            getRequest("albums/$albumId", { response ->
                val resp = JSONObject(response)
                val album = Album(
                    albumId = resp.getInt("id"),
                    name = resp.getString("name"),
                    cover = resp.getString("cover"),
                    recordLabel = resp.getString("recordLabel"),
                    releaseDate = resp.getString("releaseDate"),
                    genre = resp.getString("genre"),
                    description = resp.getString("description")
                )

                cont.resume(album)
            }, {
                cont.resumeWithException(it)
            })
        )
    }

    suspend fun getCollectors() = suspendCoroutine<List<Collector>> { cont ->
        requestQueue.add(getRequest(
            path = "collectors",
            responseListener = { response ->
                val responseArray = JSONArray(response)
                val list = mutableListOf<Collector>()
                for (i in 0 until responseArray.length()) { //inicializado como variable de retorno
                    val item = responseArray.getJSONObject(i)
                    val collector = Collector(
                        collectorId = item.getInt("id"),
                        name = item.getString("name"),
                        telephone = item.getString("telephone"),
                        email = item.getString("email")
                    )
                    list.add(collector) //se agrega a medida que se procesa la respuesta
                }
                cont.resume(list)
            },
            errorListener = {
                cont.resumeWithException(it)
            }
        ))
    }

    suspend fun getTracks(albumId: Int) = suspendCoroutine<List<Track>> { cont ->
        requestQueue.add(
            getRequest("albums/$albumId/tracks", { response ->
                val resp = JSONArray(response)
                val list = mutableListOf<Track>()
                var item: JSONObject?
                for (i in 0 until resp.length()) {
                    item = resp.getJSONObject(i)
                    Log.d("Response", item.toString())
                    list.add(
                        i, Track(
                            id = item.getInt("id"),
                            name = item.getString("name"),
                            duration = item.getString("duration")
                        )
                    )
                }
                cont.resume(list)
            }, {
                cont.resumeWithException(it)
            })
        )
    }

    private fun getRequest(
        path: String,
        responseListener: Response.Listener<String>,
        errorListener: Response.ErrorListener
    ): StringRequest {
        return StringRequest(
            Request.Method.GET,
            "${StaticConstants.API_BASE_URL}${path}",
            responseListener,
            errorListener
        )
    }

}