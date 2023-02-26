package com.example.beerinfo.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.beerinfo.Model.BeerItem
import com.example.beerinfo.R
import com.example.beerinfo.adaptor.BeerFavAdaptor
import com.example.beerinfo.adaptor.BeerListAdaptor
import com.example.beerinfo.databinding.FragmentFavouriteBinding
import com.example.beerinfo.viewModels.PageViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.toList

class FavouriteFragment : Fragment() {

    private val pageViewModel :PageViewModel by activityViewModels()
    private var _binding:FragmentFavouriteBinding? = null
    private val binding get() = _binding!!
    private lateinit var beerFavAdaptor: BeerFavAdaptor


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFavouriteBinding.inflate(inflater,container,false)
        return _binding?.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pageViewModel.getItemToDb()
        setUpAdaptor()
        setUpObserver()
        itemTouch()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setUpAdaptor() {
        beerFavAdaptor = BeerFavAdaptor()
        binding.favRecyclerview.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = beerFavAdaptor
        }
    }

    private fun setUpObserver() {
        lifecycleScope.launchWhenStarted {
            pageViewModel._beerItemList.collect {
                if (it.isEmpty()){
                    binding.apply {
                        favRecyclerview.visibility = View.GONE
                        text.visibility =View.VISIBLE
                    }

                }else{
                    binding.favRecyclerview.visibility = View.VISIBLE
                    binding.text.visibility =View.GONE
                    beerFavAdaptor.submitList(it)
                }

            }
        }
    }

    private fun itemTouch(){
        ItemTouchHelper(object :ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val deletedBeer: BeerItem = beerFavAdaptor.listOfItems[viewHolder.bindingAdapterPosition]
                pageViewModel.deleteFromDb(deletedBeer)
                beerFavAdaptor.notifyItemRemoved(viewHolder.bindingAdapterPosition)
                Snackbar.make(binding.favRecyclerview,"Deleted ${deletedBeer.name}",Snackbar.LENGTH_SHORT).show()
            }

        }).attachToRecyclerView(binding.favRecyclerview)
    }



}