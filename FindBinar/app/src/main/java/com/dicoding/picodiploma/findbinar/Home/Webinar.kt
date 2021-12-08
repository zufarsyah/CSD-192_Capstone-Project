package com.dicoding.picodiploma.findbinar.Home

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Webinar(
    var title: String,
    var university: String,
    var photo: Int
) : Parcelable
