package com.abdulansari.customlauncher.sdk.data

import android.content.Context
import com.abdulansari.customlauncher.sdk.data.model.AppInfo
import io.reactivex.Single

interface DataSource {
    fun getInstalledApps(context: Context): Single<MutableList<AppInfo>>
}