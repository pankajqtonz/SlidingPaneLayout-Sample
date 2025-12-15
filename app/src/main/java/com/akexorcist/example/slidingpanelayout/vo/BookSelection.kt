package com.akexorcist.example.slidingpanelayout.vo

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class BookSelection(val book: Book, var isShowing: Boolean) : Parcelable