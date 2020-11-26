package com.example.nomnom.ui.pesanan

import android.os.Bundle
import android.os.CountDownTimer
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nomnom.R

class PesananFragment : Fragment() {

    private lateinit var pesananViewModel: PesananViewModel
    private lateinit var rvOrder: RecyclerView
    private var timer: CountDownTimer? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        pesananViewModel =
            ViewModelProviders.of(this).get(PesananViewModel::class.java)
        return inflater.inflate(R.layout.fragment_pesanan, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvOrder = view.findViewById<RecyclerView>(R.id.fragPesanan_recyclerview)
        rvOrder.layoutManager = LinearLayoutManager(context)
    }

    override fun onStart() {
        super.onStart()
        startTestTimer()
    }

    override fun onStop() {
        super.onStop()
        stopTestTimer()
    }

    private fun startTestTimer() {
        if(timer == null) {
            timer = object: CountDownTimer(86400000, 2000) {
                override fun onFinish() {
                    Log.d("TICK STATUS", "FINISHED")
                }

                override fun onTick(p0: Long) {
                    pesananViewModel.getOrders(rvOrder)
                    Log.d("TICK STATUS", "$p0")
                }
            }
        }
        timer?.start()
    }

    private fun stopTestTimer() {
        timer?.cancel()
        timer = null
        Log.d("TICK STATUS", "STOP")
    }
}