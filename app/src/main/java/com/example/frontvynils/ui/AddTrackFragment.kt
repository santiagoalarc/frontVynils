package com.example.frontvynils.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.example.frontvynils.R
import com.example.frontvynils.databinding.AddTrackFragmentBinding
import com.example.frontvynils.ui.adapters.TracksAdapter
import com.example.frontvynils.viewmodels.TrackViewModel
import org.json.JSONObject

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class AddTrackFragment : Fragment() {

    private var _binding: AddTrackFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: TrackViewModel
    private var viewModelAdapter: TracksAdapter? = null
    private var navc: NavController?= null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = AddTrackFragmentBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.saveTrack.setOnClickListener {
            val args: AddTrackFragmentArgs by navArgs()
            val trackName = binding.editTextTrackName.text.toString()
            val trackDuration = binding.editTextDurationTrack.text.toString()

            val jsonBody = JSONObject().apply {
                put("name", trackName)
                put("duration", trackDuration)
            }

            viewModel.postTrack(jsonBody, args.albumId)
            //_postAlbumResult.value = true
            navc?.navigate(R.id.albumFragment)
        }

        viewModelAdapter = TracksAdapter()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //viewModel = ViewModelProvider(this)[AlbumViewModel::class.java]
        //viewModel = ViewModelProvider(this, TrackViewModel.Factory(activity, 1))[TrackViewModel::class.java]
        navc = Navigation.findNavController(view)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        activity.actionBar?.title = getString(R.string.title_tracks)
        val args: AddTrackFragmentArgs by navArgs()
        Log.d("Args", args.albumId.toString())
        viewModel = ViewModelProvider(this, TrackViewModel.Factory(activity.application, args.albumId))[TrackViewModel::class.java]
        //viewModel = ViewModelProvider(this, TrackViewModel.Factory(activity.application, 1))[TrackViewModel::class.java]

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
