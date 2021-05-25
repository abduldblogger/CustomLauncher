package com.abdulansari.customlauncher

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.abdulansari.customlauncher.adapter.AppsAdapter
import com.abdulansari.customlauncher.sdk.CustomLauncherMain
import com.abdulansari.customlauncher.sdk.data.model.AppInfo
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), AppsAdapter.OnItemClickListener,
    CustomLauncherMain.LauncherListener {
    private val customLauncherMain = CustomLauncherMain.getInstance(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    private fun init() {
        customLauncherMain.setOnListeners(launcherListener = this)
        customLauncherMain.getInstalledApps()
    }

    override fun onResume() {
        super.onResume()
        customLauncherMain.setOnListeners(launcherListener = this)
    }

    override fun onPause() {
        super.onPause()
        customLauncherMain.removeListeners()
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

    override fun onErrorFetchingApps(error: Throwable) {
        activity_main_rv_apps?.visibility = View.GONE
        activity_main_progress_bar?.visibility = View.GONE
        activity_main_tv_message?.visibility = View.VISIBLE
    }

    override fun onAppInstalledOrUninstalled() {
        customLauncherMain.getInstalledApps()
    }
}