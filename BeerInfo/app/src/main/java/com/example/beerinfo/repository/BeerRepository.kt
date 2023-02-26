package com.example.beerinfo.repository

import androidx.annotation.WorkerThread
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.beerinfo.Model.BeerItem
import com.example.beerinfo.dataSource.PagingDataSource
import com.example.beerinfo.db.BeerDB
import com.example.beerinfo.retrofit.ApiService
import kotlinx.coroutines.withContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class BeerRepository @Inject constructor(
    private val apiService: ApiService,
    private val beerDb: BeerDB) {

    val getBeerItem:Flow<List<BeerItem>> = beerDb.getBeerDao().getBeer()

    fun getBeerList(): Flow<PagingData<BeerItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ), pagingSourceFactory = { PagingDataSource(apiService) }
        ).flow
    }

    suspend fun insert(beers:BeerItem) {
        beerDb.getBeerDao().addBeer(beers)
    }

    suspend fun deleteBeer(beers:BeerItem){
        beerDb.getBeerDao().delete(beers)
    }

    companion object {
        private const val NETWORK_PAGE_SIZE = 10
    }
}