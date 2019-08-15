package com.example.soccer

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.soccer.Models.Parameter
import java.util.ArrayList

class ParameterInParameterListAdapter(var list: ArrayList<Parameter>, val context: Context): androidx.recyclerview.widget.RecyclerView.Adapter<ParameterViewHolder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ParameterViewHolder {
        val v= LayoutInflater.from(p0.context).inflate(R.layout.parameter_item,p0, false)
        return ParameterViewHolder(v)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(p0: ParameterViewHolder, p1: Int) {
        var parameter=list[p1]
        p0.parameterName.setText(list[p1].name)

    }
}