package com.example.frontvynils.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.frontvynils.R
import com.example.frontvynils.databinding.CollectorDetailFragmentBinding
import com.example.frontvynils.ui.adapters.CollectorsAdapter
import com.example.frontvynils.viewmodels.CollectorDetailViewModel

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class CollectorDetailFragment : Fragment() {

    private var _binding: CollectorDetailFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: CollectorDetailViewModel

    private var viewCollectorModelAdapter: CollectorsAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = CollectorDetailFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        viewCollectorModelAdapter = CollectorsAdapter()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //recyclerView = binding.tracksRv //Lista de albumes para este artista
        //recyclerView.layoutManager = LinearLayoutManager(context)
        //recyclerView.adapter = viewCollectorModelAdapter
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        activity.actionBar?.title = getString(R.string.title_tracks)
        val args: CollectorDetailFragmentArgs by navArgs()
        Log.d("Args", args.collectorId.toString())
        viewModel = ViewModelProvider(this, CollectorDetailViewModel.Factory(activity.application, args.collectorId))[CollectorDetailViewModel::class.java]


        viewModel.collector.observe(viewLifecycleOwner){
            it.apply {
                viewCollectorModelAdapter!!.collector = this
                binding.collector = viewCollectorModelAdapter!!.collector
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