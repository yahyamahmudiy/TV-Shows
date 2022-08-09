package com.example.imperative.fragment

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.ActivityNavigator
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.example.imperative.R
import com.example.imperative.adapter.TVEpisodeAdapter
import com.example.imperative.adapter.TVShortAdapter
import com.example.imperative.databinding.FragmentDetailsBinding
import com.example.imperative.model.Episode
import com.example.imperative.utils.Logger
import com.example.imperative.viewmodel.DetailsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : Fragment(R.layout.fragment_details) {
    private val TAG = DetailsFragment::class.java.simpleName
    private val binding by viewBinding(FragmentDetailsBinding::bind)
    private val viewModel: DetailsViewModel by viewModels()
    lateinit var adapter: TVShortAdapter
    lateinit var adapterEpisode: TVEpisodeAdapter
    private var showId = 0
    companion object {
        var showImg = ""
    }
    private var showName = ""
    private var showNetwork = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            showId = it.getLong("show_id").toInt()
            showImg = it.getString("show_img")!!
            showName = it.getString("show_name")!!
            showNetwork = it.getString("show_network")!!
        }

        val transition =
            TransitionInflater.from(requireContext()).inflateTransition(android.R.transition.move)
        sharedElementReturnTransition = transition
        sharedElementEnterTransition = transition
        viewModel.apiTVShowDetails(showId)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
    }

    private fun initViews() {
        initObservers()

        binding.apply {

            recyclerView.layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL, false
            )

            tvName.text = showName
            tvtype.text = showNetwork
            Glide.with(requireContext()).load(showImg).into(ivDetail)

            ivClose.setOnClickListener {
                //ActivityCompat.finishAfterTransition(requireActivity())
                findNavController().popBackStack()
            }
        }
    }

    private fun initObservers() {
        //Retrofit related
        viewModel.tvShowDetails.observe(viewLifecycleOwner) {
            Logger.d(TAG, it.toString())
            //refresh Adapter
            refreshAdapter(it.tvShow.pictures)
            refreshAdapterEpisode(it.tvShow.episodes)
            binding.tvDetails.text = it.tvShow.description

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

    private fun refreshAdapter(items: List<String>) {
        adapter = TVShortAdapter(this, items)
        binding.recyclerView.adapter = adapter
    }

    private fun refreshAdapterEpisode(items: List<Episode>) {
        adapterEpisode = TVEpisodeAdapter(this, items)
        binding.recyclerViewEpisode.adapter = adapterEpisode
    }


}