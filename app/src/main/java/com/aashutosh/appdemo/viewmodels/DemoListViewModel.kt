package com.aashutosh.appdemo.viewmodels

import android.app.Application
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.aashutosh.appdemo.interfaces.NetworkResponseCallback
import com.aashutosh.appdemo.models.DemoModel
import com.aashutosh.appdemo.repositories.DataRepository
import com.aashutosh.appdemo.utils.NetworkHelper

class DemoListViewModel(private val app: Application) : AndroidViewModel(app) {

    private var mList: MutableLiveData<List<DemoModel>> =
        MutableLiveData<List<DemoModel>>().apply { value = emptyList() }

    val mShowProgressBar = MutableLiveData(true)
    val mShowNetworkError: MutableLiveData<Boolean> = MutableLiveData()
    val mShowApiError = MutableLiveData<String>()
    private var mRepository = DataRepository.getInstance()


    fun fetchDataFromServer(forceFetch: Boolean): MutableLiveData<List<DemoModel>> {
        if (NetworkHelper.isOnline(app.baseContext)) {
            mShowProgressBar.value = true
            mList = mRepository.getCountries(object : NetworkResponseCallback {
                override fun onNetworkFailure(th: Throwable) {
                    mShowApiError.value = th.message
                }

                override fun onNetworkSuccess() {
                    mShowProgressBar.value = false
                }
            }, forceFetch)
        } else {
            mShowNetworkError.value = true
        }
        return mList
    }

    fun onRefreshClicked(view: View) {
        fetchDataFromServer(true)
    }
}