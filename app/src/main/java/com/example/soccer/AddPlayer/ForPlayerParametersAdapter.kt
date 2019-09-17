package com.example.soccer.AddPlayer

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import com.example.soccer.Common
import com.example.soccer.MainActivity
import com.example.soccer.Models.ParentParameter
import com.example.soccer.PlayersList.PlayerEditChildFragment
import com.example.soccer.R
import java.util.ArrayList

open class ForPlayerParametersAdapter(
    var list: ArrayList<ParentParameter>,
    var context: Context,
    var activity: MainActivity,
    var playername: EditText
):
    RecyclerView.Adapter<ForPlayerParametersViewHolderr>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForPlayerParametersViewHolderr {
        val v= LayoutInflater.from(parent.context).inflate(R.layout.add_player_item,parent, false)
        return ForPlayerParametersViewHolderr(v)
    }

    override fun getItemCount(): Int {
        return  list.size
    }

    override fun onBindViewHolder(holder: ForPlayerParametersViewHolderr, position: Int) {
        var parentParameter = list[position]
        holder.parentParamName.text = parentParameter.name
        holder.parentParamValue.setText(parentParameter.totalValue)
        holder.editButton.setOnClickListener {
            if (playername.text.isNullOrBlank()) {
                return@setOnClickListener
            } else {
                var bundle = Bundle()
                bundle.putInt("pos", position)
                bundle.putString("plname", playername.text.toString())
                bundle.putString("parpar", parentParameter.name)
                var PlayerEditChildFragment = PlayerEditChildFragment()
                PlayerEditChildFragment.arguments = bundle
                Common.getFragment(PlayerEditChildFragment, R.id.main_content, activity)
            }

        }
    }

}