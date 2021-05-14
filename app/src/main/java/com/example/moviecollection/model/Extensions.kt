package com.example.moviecollection.model

import android.view.View
import com.google.android.material.snackbar.Snackbar


fun View.showSnackBar(
        text: String,
        actionText: String,
        action: ((View) -> Unit)? = null,
        length: Int = Snackbar.LENGTH_INDEFINITE
) {
    val newSnackBar = Snackbar.make(this, text, length)
    action?.let { newSnackBar.setAction(actionText, it) }
    newSnackBar.show()
}
