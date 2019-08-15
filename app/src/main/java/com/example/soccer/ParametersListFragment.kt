package com.example.soccer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Button
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
        p0!!.parameters.layoutManager=LinearLayoutManager(context)
        p0.parameters.adapter=ParameterAdapter(p1!!.parameters!!,context!!)
p0.parameters.addOnItemTouchListener(object: RecyclerView.OnItemTouchListener{
    override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {

    }

    override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
var action=e.action
        when(action){
            MotionEvent.ACTION_MOVE-> rv.parent.requestDisallowInterceptTouchEvent(true)
        }
        return false
    }

    override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {
    }
})
    }

}
        parameters_recycler_view.layoutManager=layoutManager

        parameters_recycler_view.adapter=adapter

        return view
    }
}