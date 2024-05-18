package com.example.frontvynils.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.frontvynils.databinding.CreateAlbumFragmentBinding
import com.example.frontvynils.ui.adapters.AlbumsAdapter
import com.example.frontvynils.viewmodels.AlbumViewModel

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class CreateAlbumFragment : Fragment() {

        private var _binding: CreateAlbumFragmentBinding? = null
        private val binding get() = _binding!!
        private lateinit var recyclerView: RecyclerView
        //private lateinit var viewModel: CreateAlbumViewModel
        private lateinit var viewModel: AlbumViewModel
        //private var viewModelAdapter: CreateAlbumsAdapter? = null
        private var viewModelAdapter: AlbumsAdapter? = null

        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {

            _binding = CreateAlbumFragmentBinding.inflate(inflater, container, false)
            val view = binding.root

            val genres = arrayOf("Classical", "Salsa", "Rock", "Folk")
            val recordLabel = arrayOf("Sony Music", "EMI", "Discos Fuentes", "Elektra", "Fania Records")
            val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, genres)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinnerGenre.adapter = adapter

            val recordAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, recordLabel)
            recordAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinnerRecord.adapter = recordAdapter


            viewModelAdapter = AlbumsAdapter()
            return view
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            //recyclerView = binding.fragmentsRv
            //recyclerView.layoutManager = LinearLayoutManager(context)
            //recyclerView.adapter = viewModelAdapter
        }

        @Deprecated("Deprecated in Java")
        override fun onActivityCreated(savedInstanceState: Bundle?) {
            super.onActivityCreated(savedInstanceState)
            val activity = requireNotNull(this.activity) {
                "You can only access the viewModel after onActivityCreated()"
            }
            /*activity.actionBar?.title = getString(R.string.title_createAlbums)
            viewModel = ViewModelProvider(this, CreateAlbumViewModel.Factory(activity.application))[CreateAlbumViewModel::class.java]
            viewModel.createAlbums.observe(viewLifecycleOwner) {
                it.apply {
                    viewModelAdapter!!.createAlbums = this
                }
            }
            viewModel.eventNetworkError.observe(viewLifecycleOwner) { isNetworkError ->
                if (isNetworkError) onNetworkError()
            }*/
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