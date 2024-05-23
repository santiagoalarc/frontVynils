package com.example.frontvynils.ui

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.frontvynils.R
import com.example.frontvynils.databinding.CreateAlbumFragmentBinding
import com.example.frontvynils.ui.adapters.AlbumsAdapter
import com.example.frontvynils.viewmodels.AlbumViewModel
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class CreateAlbumFragment : Fragment() {

    private var _binding: CreateAlbumFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: AlbumViewModel
    private var viewModelAdapter: AlbumsAdapter? = null
    private var navc: NavController?= null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = CreateAlbumFragmentBinding.inflate(inflater, container, false)
        val view = binding.root

        val genres = arrayOf("Género", "Classical", "Salsa", "Rock", "Folk")
        val recordLabel = arrayOf("Compañía discográfica", "Sony Music", "EMI", "Discos Fuentes", "Elektra", "Fania Records")
        val adapter = ArrayAdapter(requireContext(), R.layout.spinner_item, genres)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerGenre.adapter = adapter

        val recordAdapter = ArrayAdapter(requireContext(), R.layout.spinner_item, recordLabel)
        recordAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerRecord.adapter = recordAdapter

        binding.editTextFechaLanzamiento.setOnClickListener {
            showDateSelector()
        }

        binding.saveAlbum.setOnClickListener {
            val albumName = binding.editTextName.text.toString()
            val albumDescription = binding.editTextDescription.text.toString()
            val albumGenre = binding.spinnerGenre.selectedItem.toString()
            val albumRecord = binding.spinnerRecord.selectedItem.toString()
            val albumCover = binding.editTextCover.text.toString()
            val albumReleaseDate = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX", Locale.getDefault()).format(Date())

            val jsonBody = JSONObject().apply {
                put("name", albumName)
                put("cover", albumCover)
                put("releaseDate", albumReleaseDate)
                put("description", albumDescription)
                put("genre", albumGenre)
                put("recordLabel", albumRecord)
            }

            viewModel.postAlbum(jsonBody)
            //_postAlbumResult.value = true
            navc?.navigate(R.id.albumFragment)
        }

        viewModelAdapter = AlbumsAdapter()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[AlbumViewModel::class.java]
        navc = Navigation.findNavController(view)
        // Puedes observar LiveData del viewModel aquí si es necesario
        // viewModel.albums.observe(viewLifecycleOwner, Observer { albums ->
        //     // Actualizar UI
        // })
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

    private fun showDateSelector() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(requireContext(),
            { _: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDay: Int ->
                val selectedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                binding.editTextFechaLanzamiento.setText(selectedDate)
            }, year, month, day)
        datePickerDialog.show()
    }

}
