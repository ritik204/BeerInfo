package com.example.beerinfo.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.paging.map
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.beerinfo.Model.BeerItem
import com.example.beerinfo.adaptor.BeerListAdaptor
import com.example.beerinfo.adaptor.LoadStateAdapter
import com.example.beerinfo.databinding.BeerListFragmentBinding
import com.example.beerinfo.viewModels.PageViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BeerListFragment : Fragment() {

    private val pageViewModel: PageViewModel by activityViewModels()
    private var _binding: BeerListFragmentBinding? = null
    private val adapter =BeerListAdaptor{data:BeerItem->navigateToDetail(data)}

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BeerListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpBinding()
        setUpAdaptor()
        observerStateAdaptor()
        setObserver()
    }

    private fun setUpBinding() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            adapter.refresh()
        }
    }

    private fun setObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            pageViewModel.getAllBeerData.collectLatest {
                adapter.submitData(it)
            }
        }
    }

    private fun setUpAdaptor() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = this@BeerListFragment.adapter.withLoadStateFooter(
                footer = LoadStateAdapter{retry()}
            )
        }
    }

    private fun observerStateAdaptor() {
        adapter.addLoadStateListener { combinedLoadStates ->
            when(combinedLoadStates.source.refresh){
                is LoadState.Loading ->{
                    binding.apply {
                        shimmer.isVisible = true
                        recyclerView.isVisible = false
                        errorTxtView.isVisible = false
                    }
                }
                is LoadState.NotLoading ->{
                    binding.apply {
                        shimmer.isVisible = false
                        errorTxtView.isVisible = false
                        recyclerView.isVisible = true
                        swipeRefreshLayout.isRefreshing = false
                    }
                }
                is LoadState.Error ->{
                    binding.apply {
                        shimmer.isVisible = false
                        recyclerView.isVisible = false
                        errorTxtView.isVisible = true
                        swipeRefreshLayout.isRefreshing = false
                    }
                }

                else -> {}
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun retry() {
        adapter.retry()
    }

    private fun navigateToDetail(data: BeerItem) {
        if(pageViewModel.beerItem.contains(data)){
            Snackbar.make(requireActivity().findViewById(android.R.id.content),"Item is Already added to Fav",Snackbar.LENGTH_SHORT).show()
        }else{
            Snackbar.make(requireActivity().findViewById(android.R.id.content),"Item is added to Fav",Snackbar.LENGTH_SHORT).show()
            pageViewModel.insert(data)
        }
    }
}