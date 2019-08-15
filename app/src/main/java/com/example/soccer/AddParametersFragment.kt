package com.example.soccer

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import com.example.soccer.Models.Parameter
import com.example.soccer.Models.ParentParameter
import com.google.firebase.database.FirebaseDatabase
import java.util.ArrayList

class AddParametersFragment: Fragment() {
    lateinit var parentParamName: EditText
    lateinit var parentParamNameLabel:TextView
    lateinit var parameter:EditText
    lateinit var addParameter: Button
    lateinit var parameters: androidx.recyclerview.widget.RecyclerView
    lateinit var parameters_list:ArrayList<Parameter>
    lateinit var parameters_cl:List<Parameter>
    lateinit var save_parameters:Button
lateinit var recycler_layoutmanager: androidx.recyclerview.widget.RecyclerView.LayoutManager
    lateinit var parameterAdapter:ParameterAdapter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view=layoutInflater.inflate(R.layout.add_parameter_fragment, container,false)
        init(view)
        addParameter.setOnClickListener {
            var parameter=Parameter(parameter.text.toString(),"0")
            parameters_list.add(parameter)
            parameterAdapter.notifyDataSetChanged()
        }
        save_parameters.setOnClickListener {
            var parentParameter=ParentParameter(parentParamNameLabel.text.toString(),parameters_list)
            var databaseref=FirebaseDatabase.getInstance().reference
            var database=databaseref.child("ParentParameter").child(parentParamNameLabel.text.toString())
            database.setValue(parentParameter)
        }
        return view
    }

    private fun init(view: View?) {
parentParamName=view!!.findViewById(R.id.parent_param_name)
        parameter=view!!.findViewById(R.id.parameter_name)
        parentParamNameLabel=view!!.findViewById(R.id.parent_param_label)
        addParameter=view.findViewById(R.id.add_buton)
        parameters=view.findViewById(R.id.parameters_listview)
save_parameters=view.findViewById(R.id.save_button)
parameters_list=ArrayList<Parameter>()
        recycler_layoutmanager = androidx.recyclerview.widget.LinearLayoutManager(context)
        parameters.layoutManager=recycler_layoutmanager
         parameterAdapter=ParameterAdapter(parameters_list as ArrayList<Parameter>,context!!)
        parameters.adapter=parameterAdapter
        parentParamName.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
parentParamNameLabel.text=parentParamName.text.toString()
            }
        })

    }


}