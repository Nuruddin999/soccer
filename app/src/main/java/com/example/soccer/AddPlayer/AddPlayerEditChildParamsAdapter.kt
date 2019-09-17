package com.example.soccer.AddPlayer

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import com.example.soccer.Common
import com.example.soccer.MainActivity
import com.example.soccer.Models.ParentParameter
import com.example.soccer.Models.Players
import com.example.soccer.R
import com.google.firebase.database.FirebaseDatabase
import java.util.ArrayList

class AddPlayerEditChildParamsAdapter(
    list: ArrayList<ParentParameter>,
    context: Context,
    activity: MainActivity,
    playername: EditText
) : ForPlayerParametersAdapter(list, context, activity, playername) {
    override fun onBindViewHolder(holder: ForPlayerParametersViewHolderr, position: Int) {
        var parentParameter = list[position]
        holder.parentParamName.text = parentParameter.name
        holder.parentParamValue.setText(parentParameter.totalValue)

        holder.editButton.setOnClickListener {
            if (playername.text.isNullOrBlank()) {
                return@setOnClickListener
            } else {
                var player= Players()
                player.name=playername.text.toString()
                player.params=list
                var databaseref = FirebaseDatabase.getInstance()
                var db = databaseref.getReference()
                db.child("Players").child(player.name!!).setValue(player)
                var bundle = Bundle()
                bundle.putInt("pos", position)
                bundle.putString("plname", playername.text.toString())
                bundle.putString("parpar", parentParameter.name)
                var ChildParametersFragment = ChildParametersFragment()
               ChildParametersFragment.arguments = bundle
                Common.getFragment(ChildParametersFragment, R.id.main_content, activity)
            }

        }
    }
}