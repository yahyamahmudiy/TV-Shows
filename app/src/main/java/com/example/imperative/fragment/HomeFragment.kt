package com.example.imperative.fragment

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.imperative.R
import com.example.imperative.adapter.TVShowAdapter
import com.example.imperative.databinding.FragmentHomeBinding
import com.example.imperative.model.TVShow
import com.example.imperative.utils.Logger
import com.example.imperative.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {
    private val TAG = HomeFragment::class.java.simpleName
    private val binding by viewBinding(FragmentHomeBinding::bind)
    val viewModel: MainViewModel by viewModels()
    lateinit var adapter: TVShowAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
    }

    private fun initViews() {
        initObservers()

        val gridLayout = GridLayoutManager(requireContext(), 2)
        binding.recyclerView.layoutManager = gridLayout

        refreshAdapter(ArrayList())

        binding.apply {
            recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (gridLayout.findLastCompletelyVisibleItemPosition() == adapter.itemCount - 1) {
                        val nextPage = viewModel.tvShowPopular.value!!.page + 1
                        val totalPage = viewModel.tvShowPopular.value!!.pages
                        if (nextPage <= totalPage)
                            viewModel.apiTVShowPopular(nextPage)
                    }
                }
            })

            btnFab.setOnClickListener {
                recyclerView.smoothScrollToPosition(0)
            }
        }

        viewModel.apiTVShowPopular(1)
    }

    private fun initObservers() {
        //Retrofit related
        viewModel.tvShowsFromApi.observe(viewLifecycleOwner) {
            Logger.d(TAG, it.size.toString())
            //refresh Adapter
            adapter.setNewTVShows(it)
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

    fun refreshAdapter(items: ArrayList<TVShow>) {
        adapter = TVShowAdapter(this, items)
        binding.recyclerView.adapter = adapter
    }

    fun callDetailsFragment(tvShow: TVShow, imageView: ImageView) {

        val bundle = Bundle().apply {
            putLong("show_id", tvShow.id!!)
            putString("show_img", tvShow.image_thumbnail_path)
            putString("show_name", tvShow.name)
            putString("show_network", tvShow.network)
            //putString("iv_movie",ViewCompat.getTransitionName(imageView))
        }

        val extras = FragmentNavigatorExtras(imageView to "okmi")
        findNavController().navigate(
            R.id.action_homeFragment_to_detailsFragment,
            bundle,
            null,
            extras
        )
    }
}