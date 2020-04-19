package com.ximeft29.gastetandroid.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Location (
    val city : String,
    val adress: String,
    val municipality : String,
    val latitude: Double ,
    val longitude : Double) : Parcelable {
    constructor() : this(
        "",
        "",
        "",
        0.0,
        0.0

    )
}