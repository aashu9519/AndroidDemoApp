package com.aashutosh.appdemo.network

import com.aashutosh.appdemo.models.DemoModel
import retrofit2.Call
import retrofit2.http.GET

interface ApiServices {

    @GET("demo")
    fun getDemoData() : Call<List<DemoModel>>
}