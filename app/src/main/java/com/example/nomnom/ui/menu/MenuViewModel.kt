package com.example.nomnom.ui.menu

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.example.nomnom.handlers.NetworkHandler
import com.example.nomnom.models.MenuModel
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.collections.ArrayList

class MenuViewModel : ViewModel() {

    var data = MutableLiveData<ArrayList<MenuModel>>()

    fun getPredictions(rvMenu: RecyclerView) {

        NetworkHandler().getService().getMenu().enqueue(object :
            Callback<List<MenuModel>> {
            override fun onFailure(call: Call<List<MenuModel>>, t: Throwable) {
                Log.d("GSON ERROR", t.toString())
            }

            @SuppressLint("SetTextI18n", "SimpleDateFormat")
            override fun onResponse(
                call: Call<List<MenuModel>>,
                model: Response<List<MenuModel>>
            ) {
                val adapter = GroupAdapter<ViewHolder>()
                model.body()?.forEach {
                    val dataMenu = it
                    if (dataMenu != null) {
                        //TODO: Tambahin if untuk yang sick dan yg gak sick
                        data.value?.add(dataMenu)
                        adapter.add(MenuAdapter(dataMenu))
                        rvMenu.adapter = adapter
                    }
                    Log.d("dataMenu", dataMenu.toString())
                }
            }
        })
    }
}