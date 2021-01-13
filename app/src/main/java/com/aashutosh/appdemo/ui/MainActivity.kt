package com.aashutosh.appdemo.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.ahmadrosid.svgloader.SvgLoader
import com.aashutosh.appdemo.R
import com.aashutosh.appdemo.adapters.DemoListAdapter
import com.aashutosh.appdemo.databinding.ActivityMainBinding
import com.aashutosh.appdemo.utils.CommonPref
import com.aashutosh.appdemo.viewmodels.DemoListViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var mAdapter: DemoListAdapter
    private lateinit var mViewModel: DemoListViewModel
    private lateinit var mActivityBinding: ActivityMainBinding
    private lateinit var prefs: CommonPref

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivityBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        mViewModel = ViewModelProviders.of(this).get(DemoListViewModel::class.java)

        mActivityBinding.viewModel = mViewModel
        mActivityBinding.lifecycleOwner = this
        prefs = CommonPref(this)

        initializeRecyclerView()
        initializeObservers()
    }

    private fun initializeRecyclerView() {
        mAdapter = DemoListAdapter()
        mActivityBinding.recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = mAdapter
        }
    }

    private fun initializeObservers() {
        if (prefs.demoModelList == null){
            mViewModel.fetchDataFromServer(false).observe(this, Observer { kt ->
                mAdapter.setData(kt)
                if (kt.isNotEmpty()) {
                    prefs.demoModelList = kt
                    Log.d("AASHU", "Data saved to shared pref")
                }
            })
        }else{
            Log.d("AASHU", "Data loaded from shared pref")
            mAdapter.setData(prefs.demoModelList!!)
            mViewModel.mShowProgressBar.value = false
        }
        mViewModel.mShowApiError.observe(this, Observer {
            AlertDialog.Builder(this).setMessage(it).show()
        })
        mViewModel.mShowProgressBar.observe(this, Observer { bt ->
            if (bt) {
                mActivityBinding.progressBar.visibility = View.VISIBLE
                mActivityBinding.floatingActionButton.hide()
            } else {
                mActivityBinding.progressBar.visibility = View.GONE
                mActivityBinding.floatingActionButton.show()
            }
        })
        mViewModel.mShowNetworkError.observe(this, Observer {
            AlertDialog.Builder(this).setMessage(R.string.app_no_internet_msg).show()
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        SvgLoader.pluck().close()
    }
}