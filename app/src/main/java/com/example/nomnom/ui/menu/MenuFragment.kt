package com.example.nomnom.ui.menu

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nomnom.R
import com.example.nomnom.activities.TambahMenuActivity
import kotlinx.android.synthetic.main.fragment_menu.*

class MenuFragment : Fragment() {

    private lateinit var menuViewModel: MenuViewModel
    private lateinit var rvMenu: RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        menuViewModel =
            ViewModelProviders.of(this).get(MenuViewModel::class.java)

        val root = inflater.inflate(R.layout.fragment_menu, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        rvMenu = view.findViewById<RecyclerView>(R.id.fragmenu_recyclerview)
        rvMenu.layoutManager = LinearLayoutManager(context)
        menuViewModel.getMenu(rvMenu, fragMenu_swiperefresh)
    }

    override fun onResume() {
        super.onResume()
        menuViewModel.getMenu(rvMenu, fragMenu_swiperefresh)
    }
    private fun initUI() {
        fragmenu_addnewmenu_fab.setOnClickListener {
            val intent = Intent (activity, TambahMenuActivity::class.java)
            activity?.startActivity(intent)
        }

        fragMenu_swiperefresh.setOnRefreshListener {
            menuViewModel.getMenu(rvMenu, fragMenu_swiperefresh)
        }
    }
}