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
import com.example.soccer.Models.Players
import com.example.soccer.R
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

        var url = Uri.parse(player.picpath)
        holder.player_item.setOnClickListener {
            Log.d("player item click", "works")
        }
        holder.player_rating.setText(total.toString())


        if (url != null) {
            Picasso.get().load(url).into(holder.player_photo)
        }
        holder.player_edit.setOnClickListener {
            var player = list[position]
            var bundle = Bundle()
            bundle.putString("pc",url.toString())
            bundle.putString("pname",player.name.toString())

            Log.d("Player name",player.name.toString())
            var addPlayerFragment= AddPlayerFragment()
            addPlayerFragment.arguments = bundle
            var fragmentTransaction =activity!!.supportFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.main_content, addPlayerFragment)
            fragmentTransaction.commit()
        }


    }
}