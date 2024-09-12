package com.app.zapcomtest.data.remote

import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val apiService: ApiService) {
    suspend fun getSections() = apiService.getSections()
}