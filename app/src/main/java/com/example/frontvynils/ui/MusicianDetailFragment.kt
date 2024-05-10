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
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.frontvynils.R
import com.example.frontvynils.databinding.MusicianDetailFragmentBinding
import com.example.frontvynils.ui.adapters.MusiciansAdapter
import com.example.frontvynils.viewmodels.MusicianDetailViewModel

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class MusicianDetailFragment : Fragment() {

    private var _binding: MusicianDetailFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: MusicianDetailViewModel

    private var viewMusicianModelAdapter: MusiciansAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MusicianDetailFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        viewMusicianModelAdapter = MusiciansAdapter()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //recyclerView = binding.tracksRv //Lista de albumes para este artista
        //recyclerView.layoutManager = LinearLayoutManager(context)
        //recyclerView.adapter = viewMusicianModelAdapter
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        activity.actionBar?.title = getString(R.string.title_tracks)
        val args: MusicianDetailFragmentArgs by navArgs()
        Log.d("Args", args.musicianId.toString())
        viewModel = ViewModelProvider(this, MusicianDetailViewModel.Factory(activity.application, args.musicianId))[MusicianDetailViewModel::class.java]
        /*viewModel.musician.observe(viewLifecycleOwner) {
            it.apply {
                viewModelAdapter!!.tracks = this
                if (this.isEmpty()) {
                    binding.txtNoTracks.visibility = View.VISIBLE
                } else {
                    binding.txtNoTracks.visibility = View.GONE
                }
            }
        }*/

        viewModel.musician.observe(viewLifecycleOwner){
            it.apply {
                viewMusicianModelAdapter!!.musician = this
                binding.musician = viewMusicianModelAdapter!!.musician
                val albumCover = viewMusicianModelAdapter!!.musician?.image
                if (albumCover != null) {
                    Glide.with(requireContext())
                        .load(
                            albumCover.toUri().buildUpon().scheme("https")?.build()
                        )
                        .apply(
                            RequestOptions()
                                .placeholder(R.drawable.loading_animation)

                                .error(R.drawable.ic_broken_image))
                        .into(binding.musicianImage)
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