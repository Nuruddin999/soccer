package com.example.soccer

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.soccer.Models.ParentParameter
import java.util.ArrayList

 class ParentParameterAdapter(var list:ArrayList<ParentParameter>, var context: Context):
    RecyclerView.Adapter<ParentParameterAdapter.ViewHolder>() {
     override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParentParameterAdapter.ViewHolder {
var view=LayoutInflater.from(context).inflate(R.layout.params_items,parent, false)
         return ParentParameterAdapter.ViewHolder(view)
     }

     override fun getItemCount(): Int {
     return list.size
     }

     override fun onBindViewHolder(holder: ViewHolder, position: Int) {
     var parentParameter=list[position]
         holder.parentparamName.text=parentParameter.name

     }

     class ViewHolder(var itemview:View):RecyclerView.ViewHolder(itemview) {
var parentparamName:TextView=itemview.findViewById(R.id.parent_param_name)
         var params:RecyclerView=itemview.findViewById(R.id.params)
    }
}