package com.example.beerinfo.viewModels

import android.util.Log
import androidx.lifecycle.*
import androidx.paging.cachedIn
import com.example.beerinfo.Model.BeerItem
import com.example.beerinfo.db.BeerDB
import com.example.beerinfo.repository.BeerRepository
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PageViewModel @Inject constructor(
    private val beerRepository: BeerRepository) : ViewModel() {

    private val beerItemList= MutableSharedFlow<List<BeerItem>>()
    val _beerItemList = beerItemList.asSharedFlow()

    var beerItem :List<BeerItem> = emptyList()

    val getAllBeerData = beerRepository.getBeerList().cachedIn(viewModelScope)

    fun insert(user:BeerItem){
        viewModelScope.launch(Dispatchers.IO) {
            beerRepository.insert(user)
        }
    }

    fun deleteFromDb(beerItem: BeerItem){
        viewModelScope.launch(Dispatchers.IO) {
            beerRepository.deleteBeer(beerItem)
        }
    }

    fun getItemToDb(){
        viewModelScope.launch(Dispatchers.IO) {
            beerRepository.getBeerItem.collect {
                Log.d("Ritik","$it")
                beerItem = it
                beerItemList.emit(it)
            }
        }
    }

}