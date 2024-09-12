package com.app.zapcomtest.data.remote

import com.app.zapcomtest.data.Section
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("b/5BEJ")
    suspend fun getSections(): Response<List<Section>>
}