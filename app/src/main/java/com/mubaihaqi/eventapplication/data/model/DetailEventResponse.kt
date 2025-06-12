package com.mubaihaqi.eventapplication.data.model

data class DetailEventResponse(
    val error: Boolean,
    val message: String,
    val event: ListEventsItem
)