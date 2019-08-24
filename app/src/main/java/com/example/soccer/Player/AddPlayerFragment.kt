package com.example.soccer.Player

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.ParcelFileDescriptor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.soccer.Models.Parameter
import com.example.soccer.Models.ParentParameter
import com.example.soccer.R
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.add_player_fragment.*
import java.io.FileDescriptor
import java.util.ArrayList

class AddPlayerFragment:Fragment(){
lateinit var playerPhoto:ImageView
    lateinit var addPhotoButton: ImageView
    lateinit var playerName: EditText
    lateinit var playerParameters: RecyclerView
    lateinit var layoutManager: RecyclerView.LayoutManager
    lateinit var forPlayerAdapter:RecyclerView.Adapter<ForPlayerViewHolder>
    lateinit var parametersAdapter: FirebaseRecyclerAdapter<ParentParameter,ForPlayerParametersViewHolderr>
    companion object{
        val READ_REQUEST_CODE=42
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
       var frview=inflater.inflate(R.layout.add_player_fragment,container,false)
        initView(frview)
        var total:Int=0
        addPhotoButton.setOnClickListener {
            openImage()
        }
        var databaseref= FirebaseDatabase.getInstance()
        var database=databaseref.getReference("ParentParameter")
        parametersAdapter=object : FirebaseRecyclerAdapter<ParentParameter, ForPlayerParametersViewHolderr>(ParentParameter::class.java, R.layout.add_player_parameters_item,ForPlayerParametersViewHolderr::class.java,database)
             {


            override fun populateViewHolder(p0: ForPlayerParametersViewHolderr?, p1: ParentParameter?, p2: Int) {
                p0?.parentParamName?.text=p1?.name


                 forPlayerAdapter=ForPlayerParameterAdapter(p1!!.parameters!!,context!!, p0!!.parentParamValue)
                p0!!.parametersChild.overScrollMode=RecyclerView.OVER_SCROLL_NEVER
                p0!!.parametersChild.layoutManager=LinearLayoutManager(context)
                p0!!.parametersChild.adapter=forPlayerAdapter

            }

        }
       // playerParameters.isNestedScrollingEnabled=false
        playerParameters.layoutManager=layoutManager
        playerParameters.adapter=parametersAdapter
        return frview
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode== READ_REQUEST_CODE && resultCode== Activity.RESULT_OK){
            var uri:Uri?=null
            if (data!=null){
                uri=data.data
                var parcelFileDescriptor:ParcelFileDescriptor=context!!.contentResolver.openFileDescriptor(uri,"r")
                var filedescriptor:FileDescriptor=parcelFileDescriptor.fileDescriptor
                var image:Bitmap=BitmapFactory.decodeFileDescriptor(filedescriptor)
                parcelFileDescriptor.close()
                playerPhoto.setImageBitmap(Bitmap.createScaledBitmap(image,add_player_photo.width,add_player_photo.height,false))

            }
        }
    }

    private fun openImage() {
        var intent=Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.setType("image/*")
        startActivityForResult(intent, READ_REQUEST_CODE)
    }

    private fun initView(frview: View?) {
playerPhoto=frview!!.findViewById(R.id.add_player_photo)
        addPhotoButton=frview!!.findViewById(R.id.add_photo_button)
        playerName=frview!!.findViewById(R.id.add_player_input_name)
        playerParameters=frview!!.findViewById(R.id.add_player_parent_parameterlist)

layoutManager=LinearLayoutManager(context)
    }
}