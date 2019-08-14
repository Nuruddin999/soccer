package com.example.soccer

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView

class ParamsViewHolder(itemview: View): RecyclerView.ViewHolder(itemview) {
    var parentParamName:TextView=itemview.findViewById(R.id.parent_param_name)
    var parentParamValue:TextView=itemview.findViewById(R.id.parent_param_value)
    var parameters:RecyclerView=itemview.findViewById(R.id.params)
}