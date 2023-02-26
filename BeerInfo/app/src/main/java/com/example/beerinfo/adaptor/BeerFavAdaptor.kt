package com.example.beerinfo.adaptor

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.beerinfo.Model.BeerItem
import com.example.beerinfo.databinding.BeerItemLayoutBinding

class BeerFavAdaptor(): RecyclerView.Adapter<BeerViewHolder>() {

    var listOfItems:List<BeerItem> = emptyList()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BeerViewHolder {
        val binding = BeerItemLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return BeerViewHolder(binding,null)
    }

    override fun getItemCount(): Int {
        return listOfItems.size
    }

    override fun onBindViewHolder(holder: BeerViewHolder, position: Int) {
        holder.bind(listOfItems[position])
    }

    fun submitList(data:List<BeerItem>){
        listOfItems =data
        notifyDataSetChanged()    }
}