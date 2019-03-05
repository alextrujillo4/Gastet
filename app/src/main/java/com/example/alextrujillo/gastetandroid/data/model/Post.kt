package com.example.alextrujillo.gastetandroid.data.model
class Post {
    val photoUrl: String
    val postType: String
    val address : String?
    val city : String ?
    var location: Location
    val responsibleAdoption : String?//Boolean
    val petType: String
    val name: String
    val gender: String
    val breed: String?
    val reward: String?//Boolean
    val rewardQualtity: Int?
    val timestamp: Long
    val phone: String
    val comments: String?
    var userid: String


    constructor()  {
        this.photoUrl = ""
        this.postType = ""
        this.location= Location("","","",0.0,0.0)
        this.responsibleAdoption = null
        this.petType = ""
        this.name = ""
        this.gender = ""
        this.breed = ""
        this.reward = ""
        this.rewardQualtity = 0
        this.timestamp = 0
        this.phone = ""
        this.comments = ""
        this.userid = ""
        this.address = ""
        this.city = ""
    }
    constructor(petname : String)  {
        this.photoUrl = "https://firebasestorage.googleapis.com/v0/b/gastet-4cfd2.appspot.com/o/imagesPrueba%2Fcustom_dog.png?alt=media&token=d1c0653e-fd01-44c5-a200-4ac2fdf01e31"
        this.postType = "lost"
        this.location= Location("","","",0.0,0.0)
        this.responsibleAdoption = "no"
        this.petType = "Dog Name"
        this.name = petname
        this.gender = "Male"
        this.breed = "Pug"
        this.reward = "withReward"
        this.rewardQualtity = 100
        this.timestamp = 0
        this.phone = "81XXXXXXXX"
        this.comments = "Este es un comentario custom"
        this.userid = "12345"
        this.address = ""
        this.city = ""
    }
    constructor(photoUrl: String, postType: String, location : Location, responsibleAdoption : String?,
                petType : String, name : String, gender : String, breed : String, reward : String?,
                rewardQualtity : Int?, timestamp : Long, phone : String, comments : String,
                userid : String, address : String, city : String)  {
        this.photoUrl = photoUrl
        this.postType = postType
        this.location = location
        this. responsibleAdoption = responsibleAdoption
        this.petType = petType
        this.name = name
        this.gender = gender
        this.breed = breed
        this.reward = reward
        this.rewardQualtity = rewardQualtity
        this.timestamp = timestamp
        this.phone = phone
        this.comments = comments
        this.userid = userid
        this.address = address
        this.city = city
    }
}


