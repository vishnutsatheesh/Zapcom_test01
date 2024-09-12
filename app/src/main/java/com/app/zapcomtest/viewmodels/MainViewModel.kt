package com.app.zapcomtest.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.app.zapcomtest.data.Repository
import com.app.zapcomtest.data.Section
import com.app.zapcomtest.utils.NetWorkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository, application: Application
) : BaseViewModel(application) {


    private val _responsesections: MutableLiveData<NetWorkResult<List<Section>>> = MutableLiveData()

    val responseSections: LiveData<NetWorkResult<List<Section>>> = _responsesections


    fun getSectionList() = viewModelScope.launch {
        repository.getSectionList(context).collect { values ->
            _responsesections.value = values
        }
    }
}