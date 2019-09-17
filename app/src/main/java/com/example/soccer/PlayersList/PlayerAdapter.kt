package com.example.soccer.PlayersList

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.soccer.AddPlayer.AddPlayerFragment
import com.example.soccer.AddPlayer.ChildParametersFragment
import com.example.soccer.Common
import com.example.soccer.MainActivity
import com.example.soccer.Models.Players
import com.example.soccer.R
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso
import java.util.ArrayList

class PlayerAdapter(
    var list: ArrayList<Players>, val context: Context,
    var activity: FragmentActivity?
) : RecyclerView.Adapter<PLayerViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PLayerViewHolder {
        var v = LayoutInflater.from(context).inflate(R.layout.player_item, parent, false)
        return PLayerViewHolder(v)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: PLayerViewHolder, position: Int) {
        var total: Int = 0
        var player = list[position]
        holder.player_name.text = player.name

        for(p in player.params!!){
            var value:Int=Integer.parseInt(p.totalValue)
            total+=value
        }
holder.player_rating.setText(total.toString())
        var url = Uri.parse(player.picpath.toString())
        holder.player_item.setOnClickListener {
            Log.d("player item click", "works")
        }
        holder.player_rating.setText(total.toString())


        if (url != null) {
            Picasso.get().load(url).into(holder.player_photo)
        }
        holder.player_edit.setOnClickListener {
            var bundle = Bundle()
            bundle.putString("plname", player.name)
            if (!player.picpath.isNullOrEmpty()){
                bundle.putString("picpath",player.picpath)
            }
            var playerEditFr = PlayerEditFr()
            playerEditFr.arguments = bundle
            Common.getFragment(playerEditFr, R.id.main_content, activity as MainActivity)
        }
        holder.player_delete.setOnClickListener {
            var databaseref = FirebaseDatabase.getInstance()
            var db = databaseref.getReference()
            db.child("Final Players").child(player.name!!).removeValue()
                .addOnSuccessListener(object : OnSuccessListener<Void> {
                    override fun onSuccess(p0: Void?) {
                        list.remove(player)
                        notifyDataSetChanged()
                    }
                })
        }

    }


}
