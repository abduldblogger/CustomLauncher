package com.abdulansari.customlauncher.sdk.data.model

import android.graphics.drawable.Drawable

data class AppInfo(
    var label: CharSequence? = null,
    var packageName: CharSequence? = null,
    var icon: Drawable? = null
)