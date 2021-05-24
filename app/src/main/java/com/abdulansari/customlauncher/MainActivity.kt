package com.abdulansari.customlauncher

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.abdulansari.customlauncher.adapter.RAdapter
import com.abdulansari.customlauncher.sdk.data.model.AppInfo
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val adapter = RAdapter(this, getApps())
        val manager = LinearLayoutManager(this)
        rv_apps?.layoutManager = manager
        rv_apps?.adapter = adapter
    }

    private fun getApps(): MutableList<AppInfo> {
        val appsList: MutableList<AppInfo>?
        val pm = packageManager
        appsList = mutableListOf()
        val i = Intent(Intent.ACTION_MAIN, null)
        i.addCategory(Intent.CATEGORY_LAUNCHER)
        val allApps = pm.queryIntentActivities(i, 0)
        for (ri in allApps) {
            val app = AppInfo()
            app.label = ri.loadLabel(pm)
            app.packageName = ri.activityInfo.packageName
            app.icon = ri.activityInfo.loadIcon(pm)
            appsList.add(app)
        }
        return appsList
    }
}