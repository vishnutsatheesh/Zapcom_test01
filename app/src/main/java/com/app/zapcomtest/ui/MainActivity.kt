package com.app.zapcomtest.ui

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.app.zapcomtest.R
import com.app.zapcomtest.data.ApiResultHandler
import com.app.zapcomtest.data.Section
import com.app.zapcomtest.databinding.ActivityMainBinding
import com.app.zapcomtest.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val mainViewModel by viewModels<MainViewModel>()
    lateinit var activityMainBinding: ActivityMainBinding
    lateinit var sectionAdapter: SectionAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding =
            DataBindingUtil.setContentView(this@MainActivity, R.layout.activity_main)
        init()
        getSections()
        observeSectionData()
    }

    private fun init() {
        try {
            sectionAdapter = SectionAdapter()
            activityMainBinding.list.apply { adapter = sectionAdapter }
            activityMainBinding.swipeRefreshLayout.setOnRefreshListener { observeSectionData() }
        } catch (e: Exception) {
            e.stackTrace
        }
    }


    private fun observeSectionData() {
        try {
            mainViewModel.responseSections.observe(this) { response ->
                val apiResultHandler = ApiResultHandler<List<Section>>(this@MainActivity,
                    onLoading = {
                        activityMainBinding.progress.visibility = View.VISIBLE
                    },
                    onSuccess = { data ->
                        activityMainBinding.progress.visibility = View.GONE
                        data?.let { sectionAdapter.setSections(it) }
                        activityMainBinding.swipeRefreshLayout.isRefreshing = false
                    },
                    onFailure = {
                        activityMainBinding.progress.visibility = View.GONE
                    })
                apiResultHandler.handleApiResult(response)
            }
        } catch (e: Exception) {
            e.stackTrace
        }
    }

    private fun getSections() {
        mainViewModel.getSectionList()
    }

}