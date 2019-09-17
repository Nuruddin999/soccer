package com.example.soccer.PlayersList

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.soccer.AddPlayer.AddPlayerFragment
import com.example.soccer.AddPlayer.ChildParametersFragment
import com.example.soccer.AddPlayer.ForPlayerParameterAdapter
import com.example.soccer.Common
import com.example.soccer.MainActivity
import com.example.soccer.Models.Parameter
import com.example.soccer.Models.ParentParameter
import com.example.soccer.R
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase

class PlayerEditChildFragment:ChildParametersFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
var pos=0
        var v: View = inflater.inflate(R.layout.child_parameters_list, container, false)
        playerName=arguments!!.getString("plname")
        pos=arguments!!.getInt("pos")
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)
        var picpath=sharedPref!!.getString("picpath","")
        Log.d("sharedpr player edit",picpath)
        Log.d("arguments","${pos.toString()}  , $playerName")
        params = ArrayList<Parameter>()
        var parentparName=arguments!!.getString("parpar")
        recyclerView = v.findViewById(R.id.child_parameters_list)
        parentparamName = v.findViewById(R.id.childparameter_name)
        parentparamValue = v.findViewById(R.id.childparameter_value)
        saveTotalValueButton = v.findViewById(R.id.save_totalvalue)
        layoutManager=LinearLayoutManager(context)
        adapter = ForPlayerParameterAdapter(params!!, context!!, parentparamValue)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
        var databaseref = FirebaseDatabase.getInstance()
        Log.d("playername","$playerName")
        var database = databaseref.getReference("Final Players").child(playerName.toString()!!).child("params").child(pos.toString()).child("parameters")
        Log.d("path","${database.ref}")
        database.addChildEventListener(object : ChildEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {
            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {

            }

            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                var parameter = p0.getValue(Parameter::class.java)
                Log.d("parameter","${parameter!!.name}")
                params.add(parameter!!)
                adapter.notifyDataSetChanged()
                saveTotalValueButton.setOnClickListener {
                    var parentParameter=ParentParameter()
                    parentParameter.parameters=params
                    parentParameter.name=parentparName
                    parentParameter.totalValue=parentparamValue.text.toString()
                    db.child("Final Players").child(playerName!!).child("params").child(pos!!.toString()).setValue(parentParameter)
                    var bundle=Bundle()
                    bundle.putString("plname",playerName)
                    bundle.putString("picpath",picpath)
                    var PlayerEditFr=PlayerEditFr()
                    PlayerEditFr.arguments=bundle
                    Common.getFragment(PlayerEditFr,R.id.main_content,activity as MainActivity)

                }


            }

            override fun onChildRemoved(p0: DataSnapshot) {
            }
        })

    return v
    }
}