package com.app.zapcomtest.data

import android.content.Context
import com.app.zapcomtest.data.remote.RemoteDataSource
import com.app.zapcomtest.data.remote.toResultFlow
import com.app.zapcomtest.utils.NetWorkResult
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ActivityRetainedScoped
class Repository @Inject constructor(private val remoteDataSource: RemoteDataSource) {

    suspend fun getSectionList(context: Context): Flow<NetWorkResult<List<Section>>> {
        return toResultFlow(context) {
            remoteDataSource.getSections()
        }
    }
}