package com.example.imperative.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.imperative.R
import com.example.imperative.adapter.SavedAdapter
import com.example.imperative.adapter.TVShowAdapter
import com.example.imperative.databinding.FragmentHomeBinding
import com.example.imperative.databinding.FragmentSavedBinding
import com.example.imperative.model.TVShow
import com.example.imperative.utils.Logger
import com.example.imperative.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SavedFragment : Fragment(R.layout.fragment_saved) {
    private val TAG = SavedFragment::class.java.simpleName
    private val adapter by lazy { SavedAdapter() }
    private val binding by viewBinding(FragmentSavedBinding::bind)
    val viewModel: MainViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getTVShowsFromDB()

        initViews()
    }

    private fun initViews() {
        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(),2)
        binding.recyclerView.adapter = adapter

        initObservers()
    }

    private fun initObservers() {
        //Room related
        viewModel.tvShowsFromDB.observe(viewLifecycleOwner) {
            Logger.d(TAG, it.toString())
            //refresh Adapter
            adapter.submitList(it)

            binding.pbLoading.visibility = View.GONE
        }
        viewModel.errorMessage.observe(viewLifecycleOwner) {
            Logger.d(TAG, it.toString())
        }
        viewModel.isLoading.observe(viewLifecycleOwner) {
            Logger.d(TAG, it.toString())
            if (it) {
                binding.pbLoading.visibility = View.VISIBLE
            } else {
                binding.pbLoading.visibility = View.GONE
            }
        }
    }

}