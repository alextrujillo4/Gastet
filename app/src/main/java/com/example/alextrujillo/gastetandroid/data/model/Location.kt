package com.example.alextrujillo.gastetandroid.data.model

class Location {

    val city : String
    val adress : String
    var municipality : String
    val latitude: Double
    val longitude: Double


    constructor(){
        this.city = ""
        this.adress = ""
        this.municipality = ""
        this.latitude = 0.0
        this.longitude = 0.0
    }

    constructor(city : String, adress: String, municipality : String,latitude: Double , longitude : Double){
        this.city = city
        this.adress = adress
        this.municipality = municipality
        this.latitude = latitude
        this.longitude = longitude
    }

}

