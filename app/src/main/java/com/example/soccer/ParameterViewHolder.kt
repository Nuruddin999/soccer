package com.example.soccer

import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView

class ParameterViewHolder(itemview:View): androidx.recyclerview.widget.RecyclerView.ViewHolder(itemview) {
var parameterName=itemview.findViewById(R.id.parameter_name) as TextView
    var parameterValue=itemview.findViewById(R.id.parameter_value) as TextView
    var deleteparam:ImageView=itemview.findViewById(R.id.delete_item)

}