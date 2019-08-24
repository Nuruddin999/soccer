package com.example.soccer.Player

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.soccer.R

class ForPlayerParametersViewHolderr(itemview: View): androidx.recyclerview.widget.RecyclerView.ViewHolder(itemview) {
    var parentParamName: TextView =itemview.findViewById(R.id.add_player_item_parentparam_name)
    var parentParamValue: TextView =itemview.findViewById(R.id.add_player_item_parentparam_value)
    var parametersChild: RecyclerView =itemview.findViewById(R.id.add_player_child_parameters)
  //  var showall: Button =itemview.findViewById(R.id.show_all_button)
}