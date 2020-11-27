package com.example.nomnom.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nomnom.R
import com.example.nomnom.handlers.NetworkHandler
import com.example.nomnom.models.MenuModel
import com.example.nomnom.models.OrderModel
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_order_more_info.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OrderMoreInfoActivity : AppCompatActivity() {

    private lateinit var pesananData: OrderModel
    private var quantityPesananAda: ArrayList<String> = ArrayList()
    private var menus: ArrayList<MenuModel> = ArrayList()

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_more_info)

        pesananData = intent.getSerializableExtra("DATA") as OrderModel
        val rvOrders = findViewById<RecyclerView>(R.id.ordermoreinfo_rvOrderedMenu)
        rvOrders.layoutManager = LinearLayoutManager(this)

        ordermoreinfo_titleNoMeja.text = "Meja ${pesananData.tableNo}"

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
                model.body()?.forEachIndexed { i, menuModel ->
                    menus.add(menuModel)
                    if (pesananData.foods?.quantity?.get(i) != "0") {
                        pesananData.foods?.quantity?.get(i)?.let { quantityPesananAda.add(it) }
                        adapter.add(
                            OrderMoreInfoAdapter(menuModel, quantityPesananAda)
                        )
                        Log.d("dataOrder", menuModel.toString())
                    }
                }
                rvOrders.adapter = adapter
                ordermoreinfo_totalharga.text = totalHarga()
            }
        })

        ordermoreinfo_backbutton.setOnClickListener {
            finish()
        }

        btnpesananselesai.setOnClickListener {
            pesananData.orderId?.let { it1 ->
                NetworkHandler().getService().finishOrder(it1).enqueue(object :
                    Callback<String> {
                    override fun onFailure(call: Call<String>, t: Throwable) {
                        Log.d("GSON ERROR", t.toString())
                        startActivity(Intent(applicationContext, MainActivity::class.java))
                    }

                    @SuppressLint("SetTextI18n", "SimpleDateFormat")
                    override fun onResponse(
                        call: Call<String>,
                        model: Response<String>
                    ) {
                        startActivity(Intent(applicationContext, MainActivity::class.java))
                    }
                })
            }
        }
    }

    private fun totalHarga(): String {

        var totalHarga = 0

        pesananData.foods?.quantity?.forEachIndexed { index, s ->
//            Log.d("String", s)
            totalHarga += s.trim().toInt() * menus[index].harga?.toInt()!!
        }

        return "Rp. $totalHarga"
    }
}