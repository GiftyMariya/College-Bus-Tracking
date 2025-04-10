package com.pixieium.austtravels.auth.volunteers

import com.pixieium.austtravels.auth.R
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import models.Volunteer

class VolunteerAdapter(routeList: ArrayList<Volunteer>) :
    RecyclerView.Adapter<VolunteerAdapter.ViewHolder>() {
    private val mVolunteerList: ArrayList<Volunteer> = routeList
    private lateinit var mContext: Context

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name: TextView = itemView.findViewById(R.id.name)
        var id: TextView = itemView.findViewById(R.id.university_id)
        var imageUrl: ImageView = itemView.findViewById(R.id.profile_image)
        var timeShared: TextView = itemView.findViewById(R.id.time_shared)
        var position: TextView = itemView.findViewById(R.id.position)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_volunteer, parent, false)
        mContext = view.context
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem: Volunteer = mVolunteerList[position]
        holder.name.text = currentItem.name
        holder.id.text = currentItem.universityId
        holder.imageUrl.loadSvg(currentItem.userImage)
        holder.position.text = mContext.getString(R.string._14, (position + 2).toString())
        holder.timeShared.text = currentItem.totalContributionFormatted
    }

    /**
     * By default, ImageViews don't support SVG formats.
     * So, instead we are using the coil library to render svg files
     */
    private fun ImageView.loadSvg(url: String) {
        val imageLoader = ImageLoader.Builder(this.context)
            .componentRegistry { add(SvgDecoder(this@loadSvg.context)) }
            .build()

        val request = ImageRequest.Builder(this.context)
            .crossfade(true)
            .crossfade(2)
            .data(url)
            .target(this)
            .build()

        imageLoader.enqueue(request)
    }

    override fun getItemCount(): Int {

        return mVolunteerList.size
    }
}