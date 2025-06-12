package com.mubaihaqi.eventapplication.data.model

data class EventResponse(
    val error: Boolean,
    val message: String,
    val listEvents: List<ListEventsItem>
)

data class ListEventsItem(
    val id: Int,
    val name: String,
    val summary: String,
    val description: String,
    val imageLogo: String?,
    val mediaCover: String?,
    val category: String,
    val ownerName: String,
    val cityName: String,
    val quota: Int,
    val registrants: Int,
    val beginTime: String,
    val endTime: String,
    val link: String
)