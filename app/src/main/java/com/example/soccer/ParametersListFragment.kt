package com.example.soccer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.soccer.Models.ParentParameter
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.ArrayList

class ParametersListFragment: Fragment() {
    lateinit var parameters_recycler_view:RecyclerView
    lateinit var layoutManager: RecyclerView.LayoutManager
    lateinit var parentparametersList:ArrayList<ParentParameter>
    lateinit var new_parameter_button:Button
lateinit var adapter: FirebaseRecyclerAdapter<ParentParameter,ParamsViewHolder>
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view=inflater.inflate(R.layout.parameters_list_layout,container,false)
parameters_recycler_view=view.findViewById(R.id.parameters_list)
        layoutManager=LinearLayoutManager(context)
        parentparametersList= ArrayList()
        var databaseref= FirebaseDatabase.getInstance()
        var database=databaseref.getReference("ParentParameter")
adapter=object :FirebaseRecyclerAdapter<ParentParameter,ParamsViewHolder>(ParentParameter::class.java, R.layout.params_items, ParamsViewHolder::class.java,database){
    override fun populateViewHolder(p0: ParamsViewHolder?, p1: ParentParameter?, p2: Int) {
p0?.parentParamName?.text=p1?.name
   var str=""
        for (t in p1?.parameters!!){
            str+="${t.name} \n"
        }
        p0!!.parametersChild?.setText(str)
            p0.parametersChild.visibility=TextView.GONE




        p0!!.showall.setOnClickListener {
            if (p0!!.showall.text.equals("Hide")){
                p0.parametersChild.visibility=RecyclerView.GONE
                p0.showall.setText("Show all")
            }
            else {


            p0.parametersChild.visibility=RecyclerView.VISIBLE
        p0.showall.setText("Hide")} }
    }

}
        parameters_recycler_view.layoutManager=layoutManager

        parameters_recycler_view.adapter=adapter

        return view
    }
}