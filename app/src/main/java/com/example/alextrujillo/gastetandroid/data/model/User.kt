package com.example.alextrujillo.gastetandroid.data.model

class User {

    val username : String
    val photoUrl : String
    var phone : String
    val email: String

    constructor(){
        this.username = ""
        this.photoUrl = ""
        this.phone = ""
        this.email = ""
    }

    constructor(username : String,photoUrl : String, phone : String, email : String){
        this.username = username
        this.photoUrl = photoUrl
        this.phone = phone
        this.email = email
    }
}