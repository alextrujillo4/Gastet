package com.example.alextrujillo.gastetandroid.data.model

class Post constructor( val photoUrl : String,  val postType : String,
                        var location : Location,val responsibleAdoption: Boolean,
                        val petType : String,   val name : String,
                        val gender : String,    val breed : String?,
                        val reward: Boolean,    val price : Double,
                        val timestamp : String, val phone : String,
                        val comments : String?,
                        var userid : String ,   val user : User)

