package com.eatmybrain.smoketracker.data.api

import retrofit2.http.GET

interface StrainsApi {

    @GET("/strains")
    fun strain(name:String)
}