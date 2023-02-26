package com.example.beerinfo.diModules

import android.content.Context
import androidx.room.Room
import com.example.beerinfo.db.BeerDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class DataBaseModule {

    @Singleton
    @Provides
    fun providesBeerDB(
        @ApplicationContext app: Context
    )=Room.databaseBuilder(
        app,
        BeerDB::class.java,
        "BeerDB"
    ).build()


}