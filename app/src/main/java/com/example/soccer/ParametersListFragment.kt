package com.example.soccer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.soccer.Models.ParentParameter
import java.util.ArrayList

class ParametersListFragment: Fragment() {
    lateinit var parameters_recycler_view:RecyclerView
    lateinit var layoutManager: RecyclerView.LayoutManager
    lateinit var parentparametersList:ArrayList<ParentParameter>
    lateinit var new_parameter_button:Button
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view=inflater.inflate(R.layout.parameters_list_layout,container,false)
parameters_recycler_view=view.findViewById(R.id.parameters_list)
        layoutManager=LinearLayoutManager(context)
        return view
    }
}