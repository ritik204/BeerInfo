package com.example.beerinfo.adaptor

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CenterInside
import com.bumptech.glide.load.resource.bitmap.FitCenter
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.beerinfo.Model.BeerItem
import com.example.beerinfo.databinding.BeerItemLayoutBinding

class BeerListAdaptor(private val clicked:(BeerItem) ->Unit) :
    PagingDataAdapter<BeerItem, BeerViewHolder>(COMPARATOR) {


    override fun onBindViewHolder(holder: BeerViewHolder, position: Int) {
        val data = getItem(position)
        holder.bind(data)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BeerViewHolder {
        val binding = BeerItemLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return BeerViewHolder(binding,clicked)
    }


    companion object{
        private val COMPARATOR = object : DiffUtil.ItemCallback<BeerItem>(){
            override fun areItemsTheSame(oldItem: BeerItem, newItem: BeerItem): Boolean {
               return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: BeerItem, newItem: BeerItem): Boolean {
                return oldItem == newItem
            }

        }
    }

}

class BeerViewHolder(private val binding: BeerItemLayoutBinding, private val clicked: ((BeerItem) -> Unit)?): RecyclerView.ViewHolder(binding.root){
    fun bind(data: BeerItem?) {
        binding.apply {
            title.text = data?.name
            tagline.text = data?.tagline
            newsItemSource.text = data?.description
            if (clicked == null){
                addfav.visibility = View.GONE
            }else{
                addfav.visibility = View.VISIBLE
                addfav.setOnClickListener {
                    clicked.invoke(data!!)
                }
            }

        }

        Glide.with(itemView.context)
            .load(data?.image_url)
            .transform(CenterInside())
            .placeholder(androidx.drawerlayout.R.drawable.notification_bg)
            .into(binding.imageView)

    }
}
