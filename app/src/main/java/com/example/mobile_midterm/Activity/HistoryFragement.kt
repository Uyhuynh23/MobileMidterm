package com.example.mobile_midterm.Activity

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobile_midterm.Adapter.OrderAdapter
import com.example.mobile_midterm.Helper.TinyDB
import com.example.mobile_midterm.R


class HistoryFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.fragment_history, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerHistory)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val cartItems = TinyDB(requireContext()).getListObject("HistoryOrders")
        recyclerView.adapter = OrderAdapter(cartItems)

        return view
    }
}
