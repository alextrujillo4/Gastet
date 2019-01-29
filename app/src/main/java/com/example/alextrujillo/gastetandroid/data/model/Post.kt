package com.example.alextrujillo.gastetandroid.data.model

class Post constructor(val postType : String, val petType : String, val photoUrl : String,
                       val phone : String , val author : User, val comments : String?, val city : String,
                       val  municipality : String, val address : String, val timestamp : String,
                       val breed : String?, val gender : String, var  userid : String )

