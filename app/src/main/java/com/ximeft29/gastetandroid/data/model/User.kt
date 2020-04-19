package com.ximeft29.gastetandroid.data.model

class User {

    val username : String
    val photoUrl : String?
    val email: String

    constructor(){
        this.username = ""
        this.photoUrl = ""
        this.email = ""
    }

    constructor(username : String, email : String){
        this.username = username
        this.photoUrl = "https://firebasestorage.googleapis.com/v0/b/gastet-4cfd2.appspot.com/o/imagesPrueba%2Fdefault_porfile_img.png?alt=media&token=6ef2d554-ec38-464d-8344-270709fe2d1a"
        this.email = email
    }
}