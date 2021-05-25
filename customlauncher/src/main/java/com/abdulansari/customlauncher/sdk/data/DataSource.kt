package com.abdulansari.customlauncher.sdk.data

import android.content.Context
import com.abdulansari.customlauncher.sdk.data.model.AppInfo
import io.reactivex.Single

internal interface DataSource {
    fun getInstalledApps(context: Context): Single<MutableList<AppInfo>>
}