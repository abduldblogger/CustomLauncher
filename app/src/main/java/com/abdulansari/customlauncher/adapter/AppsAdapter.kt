package com.abdulansari.customlauncher.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abdulansari.customlauncher.R
import com.abdulansari.customlauncher.sdk.data.model.AppInfo
import kotlinx.android.synthetic.main.app_row.view.*


class AppsAdapter(
    private val context: Context,
    private val appsList: List<AppInfo>
) : RecyclerView.Adapter<AppsAdapter.ViewHolder>() {
    private var onItemClickListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClicked(appInfo: AppInfo)
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val appTitle = view.app_row_tv_app_title
        val appIcon = view.app_row_img_app
        val appPackageNmae = view.app_row_tv_app_packageName
        val appMainActivityName = view.app_row_tv_app_main_activity
        val appVersionCode = view.app_row_tv_app_version_code
        val appVersionName = view.app_row_tv_app_version_name
        val rootView = view.app_row_root
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.app_row, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return appsList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (!appsList.isNullOrEmpty()) {
            val appInfo = appsList[position]
            holder.appTitle.text = appInfo.appName
            holder.appIcon.setImageDrawable(appInfo.icon)
            holder.appMainActivityName.text = appInfo.mainActivityName
            holder.appPackageNmae.text = appInfo.packageName
            holder.appVersionName.text = appInfo.versionName
            holder.appVersionCode.text = appInfo.versionCode.toString()
            holder.rootView.setOnClickListener { onItemClickListener?.onItemClicked(appInfo) }
        }
    }
}