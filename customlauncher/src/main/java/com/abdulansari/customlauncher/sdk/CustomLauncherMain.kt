package com.abdulansari.customlauncher.sdk

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import com.abdulansari.customlauncher.sdk.data.model.AppInfo
import com.abdulansari.customlauncher.sdk.manager.AppsManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


//  default constructor private to avoid initialization
class CustomLauncherMain private constructor(private val context: Context?) {
    private var listener: LauncherListener? = null
    private val appListener = AppInstallUninstallReceiver()

    fun getInstalledApps() {
        context?.let {
            AppsManager.getInstalledApps(it)
                .subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe({ onSuccessResponse -> onSuccess(onSuccessResponse) },
                    { onErrorResponse -> handleError(onErrorResponse) })
        }
    }

    private fun handleError(error: Throwable) {
        listener?.onErrorFetchingApps(error)
    }

    private fun onSuccess(appsList: MutableList<AppInfo>) {
        val sortedAppsList = appsList.sortedBy { appInfo -> appInfo.appName.toString() }
        listener?.onInstalledAppsReceived(sortedAppsList)
    }

    fun setOnListeners(launcherListener: LauncherListener) {
        listener = launcherListener
        addAppInstallUninstallListener()
    }

    private fun addAppInstallUninstallListener() {
        val filter = IntentFilter(Intent.ACTION_PACKAGE_ADDED)
        filter.addAction(Intent.ACTION_PACKAGE_REMOVED)
        filter.addDataScheme("package")
        context?.registerReceiver(appListener, filter)
    }

    fun removeListeners() {
        listener = null
        removeAppInstallUninstallListener()
    }

    private fun removeAppInstallUninstallListener() {
        context?.unregisterReceiver(appListener)
    }

    companion object {
        @JvmStatic // adding to support calling from Java class
        fun getInstance(context: Context): CustomLauncherMain {
            return CustomLauncherMain(context)
        }
    }

    interface LauncherListener {
        fun onInstalledAppsReceived(appsList: List<AppInfo>)
        fun onErrorFetchingApps(error: Throwable)
        fun onAppInstalledOrUninstalled()
    }

    inner class AppInstallUninstallReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            listener?.onAppInstalledOrUninstalled()
        }
    }
}