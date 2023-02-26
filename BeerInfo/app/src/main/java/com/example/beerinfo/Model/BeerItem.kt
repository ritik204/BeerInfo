package com.example.beerinfo.Model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class BeerItem(
    val abv: Double = 0.0,
    val attenuation_level: Double = 0.0,
    val brewers_tips: String = "",
    val contributed_by: String = "",
    val description: String="",
    val first_brewed: String="",
    val ibu: Double = 0.0,
    @PrimaryKey(autoGenerate = false)
    val id: Int = 0,
    val image_url: String = "",
    val name: String="",
    val ph: Double=0.0,
    val srm: Double = 0.0,
    val tagline: String="",
    val target_og: Double = 0.0,
)