package com.example.soccer.AddPlayer

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.ParcelFileDescriptor
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.soccer.Models.ParentParameter
import com.example.soccer.Models.Players
import com.example.soccer.R
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.google.firebase.database.*
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.add_player_fragment.*
import java.io.FileDescriptor
import java.util.ArrayList
import kotlin.math.log

class AddPlayerFragment : Fragment() {
    lateinit var playerPhoto: ImageView
    lateinit var addPhotoButton: ImageView
    lateinit var playerName: EditText
    lateinit var playerParameters: RecyclerView
    lateinit var layoutManager: RecyclerView.LayoutManager
    lateinit var save_player_button: Button
    var params = ArrayList<ParentParameter>()
    var parentParameter=ParentParameter()
    var player=Players()
    lateinit var parametersAdapter: FirebaseRecyclerAdapter<ParentParameter, ForPlayerParametersViewHolderr>
    var totalValue: String = ""
    var keyPos: String = ""
    var plName: String = ""
    var databaseref = FirebaseDatabase.getInstance()
    var db = databaseref.getReference()
    var gsonBuilder=GsonBuilder()
    var gson=gsonBuilder.create()
    companion object {
        val READ_REQUEST_CODE = 42
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var frview = inflater.inflate(R.layout.add_player_fragment, container, false)
        initView(frview)
        addPhotoButton.setOnClickListener {
            openImage()
        }
        var databaseref = FirebaseDatabase.getInstance()
        var database = databaseref.getReference("ParentParameter")
        parametersAdapter = object : FirebaseRecyclerAdapter<ParentParameter, ForPlayerParametersViewHolderr>(
            ParentParameter::class.java,
            R.layout.add_player_item,
            ForPlayerParametersViewHolderr::class.java,
            database
        ) {

            override fun populateViewHolder(p0: ForPlayerParametersViewHolderr?, p1: ParentParameter?, p2: Int) {
                if (arguments == null) {
                p0!!.parentParamName?.text = p1?.name
                p0.parentParamValue.text = "0"
                p0.editButton.setOnClickListener {
if (playerName.text.isNotEmpty()){
                    Log.d(activity!!.localClassName, "button works")
                    var bundle = Bundle()
                    bundle.putString("parpar", parametersAdapter.getRef(p2).key)
                    bundle.putString("plname", playerName.text.toString())
    parentParameter=p1!!
    params.add(parentParameter)
                    player.name=playerName.text.toString()
    player.picpath=""
    player.params=params

    Log.d(this.toString(),gson.toJson(player))
    var pl=gson.toJson(player)
    bundle.putString("player",pl)
    db.child("Players").child(playerName.text.toString()).setValue(player)
                    var ChildParametersFragment = ChildParametersFragment()
                    ChildParametersFragment.arguments = bundle
                    var fragmentTransaction = fragmentManager!!.beginTransaction()
                    fragmentTransaction.replace(R.id.main_content, ChildParametersFragment)
                    fragmentTransaction.commit()}
                    else {
    return@setOnClickListener
}
                }
                } else {
                    plName=arguments!!.getString("plname")
                    playerName.setText(plName)
                    p0!!.parentParamName.text=p1!!.name
                    db.child("Players").child(plName).addValueEventListener(object :ValueEventListener{
                        override fun onCancelled(d0: DatabaseError) {

                        }

                        override fun onDataChange(d0: DataSnapshot) {
                            var pl=d0.getValue(Players::class.java)
                            Log.d("NAME",pl?.name)
                            for (p in pl!!.params!!){
                                Log.d("NAME",p.name)
                            }
                        var parentParameter=p1!!
                           var list=pl.params
                            list!!.add(parentParameter)
                            pl.params=list
                            for (p in pl!!.params!!){
                                Log.d("NAME",p.name)
                            }
                            p0.editButton.setOnClickListener {
                                db.child("Players").child(plName).setValue(pl)
                                /*  var bundle = Bundle()
                                  bundle.putString("parpar", parametersAdapter.getRef(p2).key)
                                  bundle.putString("plname", playerName.text.toString())

                                  var ChildParametersFragment = ChildParametersFragment()
                                  ChildParametersFragment.arguments = bundle
                                  var fragmentTransaction = fragmentManager!!.beginTransaction()
                                  fragmentTransaction.replace(R.id.main_content, ChildParametersFragment)
                                  fragmentTransaction.commit()*/
                            }
                        }
                    })

                    Log.d(this.toString(),plName)


                }


            }

        }
        // playerParameters.isNestedScrollingEnabled=false
        playerParameters.layoutManager = layoutManager
        playerParameters.adapter = parametersAdapter
        save_player_button = frview.findViewById(R.id.save_player_button)
        save_player_button.setOnClickListener {


        }
        return frview
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            var uri: Uri? = null
            if (data != null) {
                uri = data.data
                var parcelFileDescriptor: ParcelFileDescriptor = context!!.contentResolver.openFileDescriptor(uri, "r")
                var filedescriptor: FileDescriptor = parcelFileDescriptor.fileDescriptor
                var image: Bitmap = BitmapFactory.decodeFileDescriptor(filedescriptor)
                parcelFileDescriptor.close()
                playerPhoto.setImageBitmap(
                    Bitmap.createScaledBitmap(
                        image,
                        add_player_photo.width,
                        add_player_photo.height,
                        false
                    )
                )

            }
        }
    }

    private fun openImage() {
        var intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.setType("image/*")
        startActivityForResult(intent, READ_REQUEST_CODE)
    }

    private fun initView(frview: View?) {
        playerPhoto = frview!!.findViewById(R.id.add_player_photo)
        addPhotoButton = frview!!.findViewById(R.id.add_photo_button)
        playerName = frview!!.findViewById(R.id.add_player_input_name)
        playerParameters = frview!!.findViewById(R.id.add_player_parent_parameterlist)

        layoutManager = LinearLayoutManager(context)
    }
}