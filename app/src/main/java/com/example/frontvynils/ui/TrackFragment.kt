package com.example.frontvynils.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.frontvynils.R
import com.example.frontvynils.databinding.TrackFragmentBinding
import com.example.frontvynils.models.Album
import com.example.frontvynils.ui.adapters.AlbumsAdapter
import com.example.frontvynils.ui.adapters.TracksAdapter
import com.example.frontvynils.viewmodels.TrackViewModel

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class TrackFragment : Fragment() {
    private var _binding: TrackFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: TrackViewModel
    private var viewModelAdapter: TracksAdapter? = null
    private var viewAlbumModelAdapter: AlbumsAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = TrackFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        viewModelAdapter = TracksAdapter()
        viewAlbumModelAdapter = AlbumsAdapter()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = binding.tracksRv
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = viewModelAdapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        activity.actionBar?.title = getString(R.string.title_tracks)
        val args: TrackFragmentArgs by navArgs()
        Log.d("Args", args.albumId.toString())
        viewModel = ViewModelProvider(this, TrackViewModel.Factory(activity.application, args.albumId))[TrackViewModel::class.java]
        viewModel.tracks.observe(viewLifecycleOwner) {
            it.apply {
                viewModelAdapter!!.tracks = this
                if (this.isEmpty()) {
                    binding.txtNoTracks.visibility = View.VISIBLE
                } else {
                    binding.txtNoTracks.visibility = View.GONE
                }
            }
        }

        viewModel.album.observe(viewLifecycleOwner){
            it.apply {
                viewAlbumModelAdapter!!.album = this
                binding.album = viewAlbumModelAdapter!!.album
                val album_cover = viewAlbumModelAdapter!!.album?.cover
                if (album_cover != null) {
                    Glide.with(requireContext())
                        .load(
                            album_cover.toUri().buildUpon().scheme("https")
                                ?.build()
                        )
                        .apply(
                            RequestOptions()
                                .placeholder(R.drawable.loading_animation)

                                .error(R.drawable.ic_broken_image))
                        .into(binding.albumCover)
                }
            }
        }
        viewModel.eventNetworkError.observe(viewLifecycleOwner) { isNetworkError ->
            if (isNetworkError) onNetworkError()
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onNetworkError() {
        if(!viewModel.isNetworkErrorShown.value!!) {
            Toast.makeText(activity, "Network Error", Toast.LENGTH_LONG).show()
            viewModel.onNetworkErrorShown()
        }
    }
}