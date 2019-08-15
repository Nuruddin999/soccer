package com.example.soccer

import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.widget.Button
import android.widget.TextView

class ParamsViewHolder(itemview: View): androidx.recyclerview.widget.RecyclerView.ViewHolder(itemview) {
    var parentParamName:TextView=itemview.findViewById(R.id.parent_param_name)
    var parentParamValue:TextView=itemview.findViewById(R.id.parent_param_value)
    var parametersChild:TextView =itemview.findViewById(R.id.child_parameters)
    var showall:Button=itemview.findViewById(R.id.show_all_button)
}