package com.example.soccer

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.soccer.Models.ParentParameter
import java.util.ArrayList

 class ParentParameterAdapter(var list:ArrayList<ParentParameter>, var context: Context):
    RecyclerView.Adapter<ParamsViewHolder>() {
     override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParamsViewHolder {
         val v=LayoutInflater.from(parent.context).inflate(R.layout.params_items,parent, false)
         return ParamsViewHolder(v)
     }

     override fun getItemCount(): Int {
    return  list.size
     }

     override fun onBindViewHolder(holder: ParamsViewHolder, position: Int) {
var parentParameter=list[position]
         holder.parentParamName.text=parentParameter.name
         holder.parameters

     }


 }