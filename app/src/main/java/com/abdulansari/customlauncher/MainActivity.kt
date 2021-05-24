package com.abdulansari.customlauncher

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.abdulansari.customlauncher.adapter.AppsAdapter
import com.abdulansari.customlauncher.sdk.CustomLauncherMain
import com.abdulansari.customlauncher.sdk.data.model.AppInfo
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), AppsAdapter.OnItemClickListener, CustomLauncherMain.LauncherListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    private fun init() {
        CustomLauncherMain.init(context = this)
        CustomLauncherMain.setOnListeners(launcherListener = this)
        CustomLauncherMain.getInstalledApps()
    }

    override fun onItemClicked(appInfo: AppInfo) {
        val intent =
                packageManager.getLaunchIntentForPackage(appInfo.packageName.toString())
        startActivity(intent)
    }

    override fun onInstalledAppsReceived(appsList: List<AppInfo>) {
        activity_main_tv_message?.visibility = View.GONE
        activity_main_progress_bar?.visibility = View.GONE
        activity_main_rv_apps?.visibility = View.VISIBLE
        val adapter = AppsAdapter(this, appsList)
        val manager = LinearLayoutManager(this)
        adapter.setOnItemClickListener(this)
        activity_main_rv_apps?.layoutManager = manager
        activity_main_rv_apps?.adapter = adapter
    }

    override fun errorFetchingApps(error: Throwable) {
        activity_main_rv_apps?.visibility = View.GONE
        activity_main_progress_bar?.visibility = View.GONE
        activity_main_tv_message?.visibility = View.VISIBLE
    }
}