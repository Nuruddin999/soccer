package com.example.soccer.AddPlayer

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.soccer.Models.Parameter
import com.example.soccer.Models.ParentParameter
import com.example.soccer.R
import com.google.firebase.database.FirebaseDatabase
import java.util.ArrayList

class ForPlayerParameterAdapter(var list: ArrayList<Parameter>, val context: Context, var listener: TextView) :
    androidx.recyclerview.widget.RecyclerView.Adapter<ForPlayerViewHolder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ForPlayerViewHolder {
        val v = LayoutInflater.from(p0.context).inflate(R.layout.add_player_childparameter_item, p0, false)
        return ForPlayerViewHolder(v)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(p0: ForPlayerViewHolder, p1: Int) {
        var parameter = list[p1]
        p0.parameterName.setText(parameter.name)

        var total: Int = 0
        var lastNotZeroValue:Int=0
        p0.parameterValue.setText(parameter.value.toString())
        p0.parameterValue.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {


            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s!!.isNotBlank()){
                    try {
                        parameter.value=s.toString()
                        total=0
                        for (l in list){
                            total+=Integer.parseInt(l.value)

                        }
                        listener.text=total.toString()
                    } catch (e:Exception){
                        Toast.makeText(context,"Ввщдите цифры",Toast.LENGTH_SHORT).show()
                    }

                }

            }
        })
//        savebutton.setOnClickListener {
//            var parentParameter=ParentParameter()
//            parentParameter.parameters=list
//            parentParameter.totalValue=listener.text.toString()
//            parentParameter.name=parentparname
//            var databaseref = FirebaseDatabase.getInstance()
//            var database = databaseref.getReference("Final Players").child(playerName.toString()!!).child("params").child(pos.toString()).child("parameters")
//
//        }


    }
}