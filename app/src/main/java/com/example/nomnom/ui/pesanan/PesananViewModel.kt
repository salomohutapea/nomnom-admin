package com.example.nomnom.ui.pesanan

import android.annotation.SuppressLint
import android.os.Parcelable
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.nomnom.handlers.NetworkHandler
import com.example.nomnom.models.OrderModel
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PesananViewModel : ViewModel() {

    var data = MutableLiveData<ArrayList<OrderModel>>()
    private var recyclerViewState: Parcelable? = null

    fun openMoreInfo(id: String) {

    }

    fun getOrders(rvOrder: RecyclerView) {
        NetworkHandler().getService().getOrder().enqueue(object :
            Callback<List<OrderModel>> {
            override fun onFailure(call: Call<List<OrderModel>>, t: Throwable) {
                Log.d("GSON ERROR", t.toString())
            }

            @SuppressLint("SetTextI18n", "SimpleDateFormat")
            override fun onResponse(
                call: Call<List<OrderModel>>,
                model: Response<List<OrderModel>>
            ) {
                recyclerViewState = rvOrder.layoutManager?.onSaveInstanceState()
                val adapter = GroupAdapter<ViewHolder>()
                model.body()?.forEach {
                    val dataOrder = it
                    data.value?.add(dataOrder)
                    adapter.add(PesananAdapter(dataOrder))
                    rvOrder.adapter = adapter
                    Log.d("dataOrder", dataOrder.toString())
                }
                rvOrder.layoutManager?.onRestoreInstanceState(recyclerViewState)
            }
        })
    }
}