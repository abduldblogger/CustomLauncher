package com.abdulansari.customlauncher.sdk.helper

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable

object IconHelper {
    fun getActivityIcon(context: Context, packageName: String, activityName: String): Drawable? {
        val packageMgr = context.packageManager
        val intent = Intent()
        intent.component = ComponentName(packageName, activityName)
        val resolveInfo = packageMgr.resolveActivity(intent, 0)
        return resolveInfo?.loadIcon(packageMgr)
    }
}