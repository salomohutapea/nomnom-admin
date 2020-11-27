package com.example.nomnom.ui.pesanan

import android.annotation.SuppressLint
import android.content.Context
import android.os.Parcelable
import android.util.Log
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.example.nomnom.handlers.NetworkHandler
import com.example.nomnom.models.OrderModel
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PesananViewModel : ViewModel() {

    var data = MutableLiveData<MutableList<OrderModel>>()
    private var recyclerViewState: Parcelable? = null
//    var context = Context()

    fun getOrders(rvOrder: RecyclerView, noPesananConstraintLayout: ConstraintLayout, context: Context) {
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

                if(model.body()?.size != 0) {

                    val insideData: MutableList<OrderModel> = mutableListOf()

                    model.body()?.forEachIndexed { index, orderModel ->
                        insideData.add(orderModel)
                        Log.d("dataz", data.value?.get(index).toString())
                        adapter.add(PesananAdapter(orderModel, context))
                    }

                    data.value = insideData
                }
                if(data.value?.size != null) {
                    rvOrder.visibility = View.VISIBLE
                    noPesananConstraintLayout.visibility = View.GONE
                    rvOrder.adapter = adapter
                    rvOrder.layoutManager?.onRestoreInstanceState(recyclerViewState)
                } else {
                    rvOrder.visibility = View.GONE
                    noPesananConstraintLayout.visibility = View.VISIBLE
                }
            }
        })
    }
}