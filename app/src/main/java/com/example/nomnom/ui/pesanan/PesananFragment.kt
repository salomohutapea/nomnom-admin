package com.example.nomnom.ui.pesanan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.nomnom.R

class PesananFragment : Fragment() {

    private lateinit var pesananViewModel: PesananViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        pesananViewModel =
            ViewModelProviders.of(this).get(PesananViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_pesanan, container, false)
        return root
    }
}