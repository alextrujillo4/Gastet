package com.ximeft29.gastetandroid.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Post (
    val photoUrl: String,
    val postType: String,
    val location : Location,
    val responsibleAdoption : String?,
    val petType : String,
    val name : String,
    val gender : String,
    val breed : String,
    val reward : String?,
    val rewardQualtity : Int?,
    val timestamp : Long,
    val phone : String,
    val comments : String,
    val userid : String,
    val address : String,
    val city : String) : Parcelable {
    constructor() : this(
            "",
        "",
        Location("","","",0.0,0.0),
        "",
        "",
        "",
        "",
        "",
        "",
        0,
        0,
        "",
        "",
        "",
        "",
        ""

    )
}



