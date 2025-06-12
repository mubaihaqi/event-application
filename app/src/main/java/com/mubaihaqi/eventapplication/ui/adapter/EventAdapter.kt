package com.mubaihaqi.eventapplication.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mubaihaqi.eventapplication.R
import com.mubaihaqi.eventapplication.data.model.ListEventsItem

class EventAdapter(
    private var list: List<ListEventsItem>,
    private val onItemClick: (ListEventsItem) -> Unit
) : RecyclerView.Adapter<EventAdapter.EventViewHolder>() {

    inner class EventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgEvent: ImageView = itemView.findViewById(R.id.img_event)
        val tvName: TextView = itemView.findViewById(R.id.tv_event_name)
        val tvDate: TextView = itemView.findViewById(R.id.tv_event_date)
        val tvLocation: TextView = itemView.findViewById(R.id.tv_event_location)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_event, parent, false)
        return EventViewHolder(view)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = list[position]
        holder.tvName.text = event.name
        holder.tvDate.text = event.beginTime
        holder.tvLocation.text = event.cityName
        Glide.with(holder.itemView.context)
            .load(event.imageLogo)
            .placeholder(R.drawable.ic_launcher_background)
            .into(holder.imgEvent)
        holder.itemView.setOnClickListener { onItemClick(event) }
    }

    override fun getItemCount() = list.size
    fun updateData(newList: List<ListEventsItem>) {
        list = newList
        notifyDataSetChanged()
    }
}