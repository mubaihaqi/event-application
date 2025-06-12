package com.mubaihaqi.eventapplication.data.network

import com.mubaihaqi.eventapplication.data.model.DetailEventResponse
import com.mubaihaqi.eventapplication.data.model.EventResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("events")
    fun getEvents(
        @Query("active") active: Int = 1,
        @Query("q") keyword: String? = null,
        @Query("limit") limit: Int = 40
    ): Call<EventResponse>

    @GET("events/{id}")
    fun getEventDetail(
        @Path("id") id: Int
    ): Call<DetailEventResponse>
}