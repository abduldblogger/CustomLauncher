package com.abdulansari.customlauncher.sdk.data.model

import android.graphics.drawable.Drawable

data class AppInfo(
    var appName: CharSequence? = null,
    var packageName: CharSequence? = null,
    var icon: Drawable? = null,
    var mainActivityName: String? = null,
    var versionCode: Int? = null,
    var versionName: String? = null
)