package com.example.soccer.AddPlayer

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
import com.example.soccer.Models.Parameter
import com.example.soccer.Models.ParentParameter
import com.example.soccer.Models.Players
import com.example.soccer.R
import com.google.firebase.database.*
import com.google.gson.GsonBuilder
import java.util.*
import kotlin.collections.ArrayList

open class ChildParametersFragment : Fragment() {
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: ForPlayerParameterAdapter
    lateinit var layoutManager: RecyclerView.LayoutManager
    lateinit var parentparamName: TextView
    lateinit var parentparamValue: TextView
    lateinit var saveTotalValueButton: Button


    var params = ArrayList<Parameter>()
    var parentParameter = ParentParameter()
    var parparList = ArrayList<ParentParameter>()
    var players = Players()
    var totalValue: String? = null
    var playerName: String? = null
    var databaseref = FirebaseDatabase.getInstance()
    var db = databaseref.getReference()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        var v: View = inflater.inflate(R.layout.child_parameters_list, container, false)
        playerName = arguments!!.getString("plname")

        recyclerView = v.findViewById(R.id.child_parameters_list)
        parentparamName = v.findViewById(R.id.childparameter_name)
        parentparamValue = v.findViewById(R.id.childparameter_value)
        saveTotalValueButton = v.findViewById(R.id.save_totalvalue)
        layoutManager = LinearLayoutManager(context)
        adapter = ForPlayerParameterAdapter(params!!, context!!, parentparamValue)
        var picpath=arguments!!.getString("picpath")
        var parentparName = arguments!!.getString("parpar")
       var  pos=arguments!!.getInt("pos")
        Log.d("position","child fr  $pos")

        parentparamName.text = parentparName
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
        var databaseref = FirebaseDatabase.getInstance()
        var database = databaseref.getReference("ParentParameter").child(parentparName).child("parameters")
        database.addChildEventListener(object : ChildEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {
            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {

            }

            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                var parameter = p0.getValue(Parameter::class.java)
params.add(parameter!!)
                adapter.notifyDataSetChanged()
                var parentParameter=ParentParameter()

                saveTotalValueButton.setOnClickListener {
                    parentParameter.parameters=params
                    parentParameter.name=parentparName
                    parentParameter.totalValue=parentparamValue.text.toString()
                    db.child("Players").child(playerName!!).child("params").child(pos!!.toString()).setValue(parentParameter)

                    var bundle =Bundle()
                    if (picpath!=null){
                        bundle.putString("pc",picpath)
                    }
                    bundle.putString("pname",playerName.toString())
                    var AddPlayerFragment = AddPlayerFragment()
                    AddPlayerFragment.arguments = bundle
                    var fragmentTransaction = fragmentManager!!.beginTransaction()
                    fragmentTransaction.replace(R.id.main_content, AddPlayerFragment)
                    fragmentTransaction.commit()


                }

            }

            override fun onChildRemoved(p0: DataSnapshot) {
            }
        })



        return v
    }
}