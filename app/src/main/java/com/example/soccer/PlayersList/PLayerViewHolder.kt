package com.example.soccer.PlayersList

import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.soccer.R
import kotlinx.android.synthetic.main.player_item.view.*

class PLayerViewHolder(itemview:View):RecyclerView.ViewHolder(itemview){
    var player_item:LinearLayout=itemview.findViewById(R.id.player_item)
    var player_name:TextView=itemview.findViewById(R.id.player_name)
    var player_photo:ImageView=itemview.findViewById(R.id.player_photo)
    var player_rating:TextView=itemview.findViewById(R.id.player_rating)
    var player_delete:ImageView=itemview.findViewById(R.id.delete_button)
    var player_edit:ImageView=itemview.findViewById(R.id.edit_button)
}