package com.dicoding.picodiploma.findbinar.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Topik (
    var topik: String,
    var icon: Int
    ) : Parcelable