package com.abdulansari.customlauncher

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.abdulansari.customlauncher.adapter.AppsAdapter
import com.abdulansari.customlauncher.helper.AppsFilter
import com.abdulansari.customlauncher.sdk.CustomLauncherMain
import com.abdulansari.customlauncher.sdk.data.model.AppInfo
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), AppsAdapter.OnItemClickListener,
    CustomLauncherMain.LauncherListener {
    private val appsList: MutableList<AppInfo> = mutableListOf()
    private val customLauncherMain = CustomLauncherMain.getInstance(this)
    private lateinit var adapter: AppsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    private fun init() {
        activity_main_searchview?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!appsList.isNullOrEmpty() && !query.isNullOrEmpty()) {
                    val apps = AppsFilter.filterApp(appsList, query)
                    if (::adapter.isInitialized) {
                        adapter.setData(apps)
                    }
                } else if (query?.isEmpty() == true) {
                    customLauncherMain.getInstalledApps()
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (!appsList.isNullOrEmpty() && !newText.isNullOrEmpty()) {
                    val apps = AppsFilter.filterApp(appsList, newText)
                    if (::adapter.isInitialized) {
                        adapter.setData(apps)
                    }
                } else if (newText?.isEmpty() == true) {
                    customLauncherMain.getInstalledApps()
                }
                return false
            }
        })
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

    override fun onInstalledAppsReceived(appsList: MutableList<AppInfo>) {
        this.appsList.clear()
        this.appsList.addAll(appsList)
        activity_main_tv_message?.visibility = View.GONE
        activity_main_progress_bar?.visibility = View.GONE
        activity_main_rv_apps?.visibility = View.VISIBLE
        activity_main_searchview?.visibility = View.VISIBLE
        adapter = AppsAdapter(this)
        adapter.setData(appsList)
        val manager = LinearLayoutManager(this)
        adapter.setOnItemClickListener(this)
        activity_main_rv_apps?.layoutManager = manager
        activity_main_rv_apps?.adapter = adapter
    }

    override fun onErrorFetchingApps(error: Throwable) {
        activity_main_rv_apps?.visibility = View.GONE
        activity_main_progress_bar?.visibility = View.GONE
        activity_main_searchview?.visibility = View.GONE
        activity_main_tv_message?.visibility = View.VISIBLE
    }

    override fun onAppInstalledOrUninstalled() {
        customLauncherMain.getInstalledApps()
    }
}