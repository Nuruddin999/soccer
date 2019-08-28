package com.example.soccer.AddPlayer

import android.view.View
import android.widget.Button
import android.widget.TextView
import com.example.soccer.R

class ForPlayerParametersViewHolderr(itemview: View): androidx.recyclerview.widget.RecyclerView.ViewHolder(itemview) {
    var parentParamName: TextView =itemview.findViewById(R.id.add_player_item_parent_param_name)
    var parentParamValue: TextView =itemview.findViewById(R.id.add_player_item_parent_param_value)
    var editButton: Button =itemview.findViewById(R.id.add_player_item_parent_param_edit)
}