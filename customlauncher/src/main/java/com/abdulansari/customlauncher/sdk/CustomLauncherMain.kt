package com.abdulansari.customlauncher.sdk

import android.content.Context
import com.abdulansari.customlauncher.sdk.data.model.AppInfo
import com.abdulansari.customlauncher.sdk.manager.AppsManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

object CustomLauncherMain {
    private var listener: LauncherListener? = null
    private var context: Context? = null

    @JvmStatic
    fun init(context: Context) {
        this.context = context
    }

    @JvmStatic // adding JvmStatic to support calling from Java code
    fun getInstalledApps() {
        context?.let {
            AppsManager.getInstalledApps(it)
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe({ onSuccessResponse -> onSuccess(onSuccessResponse) },
                    { onErrorResponse -> handleError(onErrorResponse) })
        }
    }

    private fun handleError(error: Throwable) {
        listener?.errorFetchingApps(error)
    }

    private fun onSuccess(appsList: MutableList<AppInfo>) {
        val sortedAppsList = appsList.sortedBy { appInfo -> appInfo.appName.toString() }
        listener?.onInstalledAppsReceived(sortedAppsList)
    }

    @JvmStatic // adding JvmStatic to support calling from Java code
    fun setOnListeners(launcherListener: LauncherListener) {
        listener = launcherListener
    }

    interface LauncherListener {
        fun onInstalledAppsReceived(appsList: List<AppInfo>)
        fun errorFetchingApps(error: Throwable)
    }
}