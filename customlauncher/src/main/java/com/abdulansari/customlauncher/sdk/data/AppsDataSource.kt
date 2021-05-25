package com.abdulansari.customlauncher.sdk.data

import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import com.abdulansari.customlauncher.sdk.data.model.AppInfo
import io.reactivex.Single

internal class AppsDataSource : DataSource {
    override fun getInstalledApps(context: Context): Single<MutableList<AppInfo>> {
        val appsList: MutableList<AppInfo>?
        val pm = context.packageManager
        appsList = mutableListOf()
        val i = Intent(Intent.ACTION_MAIN, null)
        i.addCategory(Intent.CATEGORY_LAUNCHER)
        val allApps = pm.queryIntentActivities(i, 0)
        for (ri in allApps) {
            val app = AppInfo()
            app.appName = ri.loadLabel(pm)
            app.packageName = ri.activityInfo.packageName
            app.icon = ri.activityInfo.loadIcon(pm)
            app.mainActivityName = ri.activityInfo.targetActivity
            val packageInfo: PackageInfo =
                context.packageManager.getPackageInfo(ri.activityInfo.packageName, 0)
            app.versionName = packageInfo.versionName
            app.versionCode = packageInfo.versionCode
            appsList.add(app)
        }
        return Single.just(appsList)
    }
}