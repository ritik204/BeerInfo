package com.example.beerinfo.dataSource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.beerinfo.Model.BeerItem
import com.example.beerinfo.retrofit.ApiService
import retrofit2.HttpException
import java.io.IOException


class PagingDataSource(private val apiService: ApiService):PagingSource<Int, BeerItem>() {
    override fun getRefreshKey(state: PagingState<Int, BeerItem>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, BeerItem> {
        val page = params.key ?: 1
        return try {
            val response = apiService.getBeers(page,params.loadSize)
            val result = response.body()!!
            LoadResult.Page(
                data = result,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (result.isEmpty()) null else page + 1
            )
        }catch (exception: IOException) {
            val error = IOException("Please Check Internet Connection")
            LoadResult.Error(error)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }

    }
}