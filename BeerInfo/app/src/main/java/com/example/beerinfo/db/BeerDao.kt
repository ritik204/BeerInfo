package com.example.beerinfo.db

import androidx.room.*
import com.example.beerinfo.Model.BeerItem
import kotlinx.coroutines.flow.Flow

@Dao
interface BeerDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addBeer(beers:BeerItem)

    @Query("SELECT * FROM BeerItem")
    fun getBeer(): Flow<List<BeerItem>>


    @Delete
    suspend fun delete(beers:BeerItem)

}