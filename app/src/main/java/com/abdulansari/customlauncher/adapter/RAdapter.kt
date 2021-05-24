package com.abdulansari.customlauncher.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.abdulansari.customlauncher.R
import com.abdulansari.customlauncher.sdk.data.model.AppInfo
import kotlinx.android.synthetic.main.app_row.view.*


class RAdapter(
    private val context: Context,
    private val appsList: MutableList<AppInfo>
) : RecyclerView.Adapter<RAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        val textView: AppCompatTextView = view.app_row_tv_app_title
        val img: AppCompatImageView = view.app_row_img_app
        override fun onClick(p0: View?) {
            val position = adapterPosition
            val intent =
                context.packageManager.getLaunchIntentForPackage(appsList[position].packageName.toString())
            context.startActivity(intent)
            Toast.makeText(context, appsList[position].label.toString(), Toast.LENGTH_LONG)
                .show()
        }

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
            val appLabel = appsList[position].label.toString()
            val appIcon = appsList[position].icon
            holder.textView.text = appLabel
            holder.img.setImageDrawable(appIcon)
        }
    }
}