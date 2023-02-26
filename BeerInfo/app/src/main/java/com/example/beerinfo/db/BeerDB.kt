package com.example.beerinfo.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.beerinfo.Model.BeerItem

@Database(entities = [BeerItem::class], version = 1)
abstract class BeerDB :RoomDatabase(){

   abstract fun getBeerDao():BeerDao
}