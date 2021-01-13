package com.aashutosh.appdemo.repositories

import androidx.lifecycle.MutableLiveData
import com.aashutosh.appdemo.interfaces.NetworkResponseCallback
import com.aashutosh.appdemo.models.DemoModel
import com.aashutosh.appdemo.network.RestClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DataRepository private constructor() {
    private lateinit var mCallback: NetworkResponseCallback
    private var mList: MutableLiveData<List<DemoModel>> =
        MutableLiveData<List<DemoModel>>().apply { value = emptyList() }

    companion object {
        private var mInstance: DataRepository? = null
        fun getInstance(): DataRepository {
            if (mInstance == null) {
                synchronized(this) {
                    mInstance = DataRepository()
                }
            }
            return mInstance!!
        }
    }


    private lateinit var mCallBack: Call<List<DemoModel>>

    fun getCountries(callback: NetworkResponseCallback, forceFetch : Boolean): MutableLiveData<List<DemoModel>> {
        mCallback = callback
        if (mList.value!!.isNotEmpty() && !forceFetch) {
            mCallback.onNetworkSuccess()
            return mList
        }
        mCallBack = RestClient.getInstance().getApiService().getDemoData()
        mCallBack.enqueue(object : Callback<List<DemoModel>> {

            override fun onResponse(call: Call<List<DemoModel>>, response: Response<List<DemoModel>>) {
                mList.value = response.body()
                mCallback.onNetworkSuccess()
            }

            override fun onFailure(call: Call<List<DemoModel>>, t: Throwable) {
                mList.value = emptyList()
                mCallback.onNetworkFailure(t)
            }

        })
        return mList
    }
}