package com.mubaihaqi.eventapplication.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mubaihaqi.eventapplication.R
import com.mubaihaqi.eventapplication.data.model.ListEventsItem
import com.mubaihaqi.eventapplication.databinding.ItemEventUpcomingBinding

class UpcomingEventAdapter(
    private var events: List<ListEventsItem>,
    private val onClick: (ListEventsItem) -> Unit
) : RecyclerView.Adapter<UpcomingEventAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemEventUpcomingBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(event: ListEventsItem) {
            binding.tvEventName.text = event.name
            Glide.with(binding.imgEvent.context)
                .load(event.imageLogo)
                .placeholder(R.drawable.ic_launcher_background)
                .into(binding.imgEvent)
            binding.root.setOnClickListener { onClick(event) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemEventUpcomingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = events.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(events[position])
    }

    fun updateData(newList: List<ListEventsItem>) {
        events = newList
        notifyDataSetChanged()
    }
}