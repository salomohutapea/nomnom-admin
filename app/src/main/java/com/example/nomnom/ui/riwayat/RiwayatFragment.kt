package com.example.nomnom.ui.riwayat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.nomnom.R

class RiwayatFragment : Fragment() {

    private lateinit var riwayatViewModel: RiwayatViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        riwayatViewModel =
            ViewModelProviders.of(this).get(RiwayatViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_riwayat, container, false)
        val textView: TextView = root.findViewById(R.id.text_notifications)
        riwayatViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}