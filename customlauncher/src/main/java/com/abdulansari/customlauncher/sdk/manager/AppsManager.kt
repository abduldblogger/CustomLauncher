package com.abdulansari.customlauncher.sdk.manager

import android.content.Context
import com.abdulansari.customlauncher.sdk.data.AppsDataSource
import com.abdulansari.customlauncher.sdk.data.DataSource
import com.abdulansari.customlauncher.sdk.data.model.AppInfo
import io.reactivex.Single

internal object AppsManager {
    private var appsDataSource: DataSource = AppsDataSource()

    fun getInstalledApps(context: Context): Single<MutableList<AppInfo>> {
        return appsDataSource.getInstalledApps(context)
    }
}