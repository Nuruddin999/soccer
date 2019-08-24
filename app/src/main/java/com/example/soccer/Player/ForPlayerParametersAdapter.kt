package com.example.soccer.Player

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.soccer.Models.ParentParameter
import com.example.soccer.ParamsViewHolder
import com.example.soccer.R
import java.util.ArrayList

class ForPlayerParametersAdapter(var list: ArrayList<ParentParameter>, var context: Context):
    RecyclerView.Adapter<ForPlayerParametersViewHolderr>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForPlayerParametersViewHolderr {
        val v= LayoutInflater.from(parent.context).inflate(R.layout.params_items,parent, false)
        return ForPlayerParametersViewHolderr(v)
    }

    override fun getItemCount(): Int {
        return  list.size
    }

    override fun onBindViewHolder(holder: ForPlayerParametersViewHolderr, position: Int) {
        var parentParameter=list[position]
        holder.parentParamName.text=parentParameter.name
        var totalvalue:Int=0
        for (t in parentParameter.parameters!!){
            totalvalue+=Integer.parseInt(t.value)
            holder.parentParamValue.text=totalvalue.toString()
        }
        holder.parametersChild.layoutManager=LinearLayoutManager(context)




    }


}