package com.example.soccer.AddPlayer

import android.view.View
import android.widget.EditText
import android.widget.TextView
import com.example.soccer.R

class ForPlayerViewHolder (itemview: View): androidx.recyclerview.widget.RecyclerView.ViewHolder(itemview) {
    var parameterName=itemview.findViewById(R.id.add_player_item_childparam_name) as TextView
    var parameterValue=itemview.findViewById(R.id.add_player_item_childparam_value) as EditText


}