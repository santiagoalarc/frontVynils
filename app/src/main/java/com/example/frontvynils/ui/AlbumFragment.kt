package com.example.frontvynils.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.frontvynils.R
import com.example.frontvynils.databinding.AlbumFragmentBinding
import com.example.frontvynils.ui.adapters.AlbumsAdapter
import com.example.frontvynils.viewmodels.AlbumViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class AlbumFragment : Fragment(), View.OnClickListener {
    private var _binding: AlbumFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: AlbumViewModel
    private var viewModelAdapter: AlbumsAdapter? = null
    private var navc: NavController? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = AlbumFragmentBinding.inflate(inflater, container, false)
        viewModelAdapter = AlbumsAdapter()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = binding.albumsRv
        recyclerView.layoutManager = GridLayoutManager(context, 2)
        recyclerView.adapter = viewModelAdapter
        navc = Navigation.findNavController(view)
        view.findViewById<FloatingActionButton>(R.id.floatingActionButton)?.setOnClickListener(this)

        viewModel = ViewModelProvider(
            this,
            AlbumViewModel.Factory(requireActivity().application)
        ).get(AlbumViewModel::class.java)

        viewModel.albums.observe(viewLifecycleOwner) { albums ->
            albums?.let {
                viewModelAdapter?.albums = it
            }
        }

        viewModel.eventNetworkError.observe(viewLifecycleOwner) { isNetworkError ->
            if (isNetworkError) onNetworkError()
        }

        // Llama a la función para cargar los datos cuando se crea la vista
        viewModel.refreshAlbums()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onNetworkError() {
        if (!viewModel.isNetworkErrorShown.value!!) {
            Toast.makeText(activity, "Network Error", Toast.LENGTH_LONG).show()
            viewModel.onNetworkErrorShown()
        }
    }

    override fun onClick(v: View?) {
        navc?.navigate(R.id.action_albumFragment_to_createAlbumFragment)
    }
}
