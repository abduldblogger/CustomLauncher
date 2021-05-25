package com.abdulansari.customlauncher.helper

import com.abdulansari.customlauncher.sdk.data.model.AppInfo

object AppsFilter {
    @JvmStatic
    fun filterApp(
        appsList: MutableList<AppInfo>,
        query: String
    ): List<AppInfo> {
        return appsList.filter { it.appName?.contains(query, ignoreCase = true) == true }
    }
}