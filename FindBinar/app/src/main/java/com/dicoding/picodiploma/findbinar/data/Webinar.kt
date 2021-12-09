package com.dicoding.picodiploma.findbinar.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Webinar(
    var title: String,
    var date: String,
    var topik: String,
    var photo: Int,
) : Parcelable
