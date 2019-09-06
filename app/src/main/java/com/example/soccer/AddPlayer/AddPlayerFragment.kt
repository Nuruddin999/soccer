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
    var parentParameter = ParentParameter()

    lateinit var parametersAdapter: FirebaseRecyclerAdapter<ParentParameter, ForPlayerParametersViewHolderr>
    var totalValue: String = ""
    var keyPos: String = ""
    var plName: String = ""
    var databaseref = FirebaseDatabase.getInstance()
    var db = databaseref.getReference()
    var gsonBuilder = GsonBuilder()
    var gson = gsonBuilder.create()

    companion object {
        val READ_REQUEST_CODE = 42
    }



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var frview = inflater.inflate(R.layout.add_player_fragment, container, false)
        initView(frview)
        if (arguments!=null){
            var name_from_childfr=arguments!!.getString("pname")
            playerName.setText(name_from_childfr.toString())
        }
        addPhotoButton.setOnClickListener {
            openImage()
        }
        var databaseref = FirebaseDatabase.getInstance()
        var database = databaseref.getReference("ParentParameter")
        var list=ArrayList<ParentParameter>()
        parametersAdapter = object : FirebaseRecyclerAdapter<ParentParameter, ForPlayerParametersViewHolderr>(
            ParentParameter::class.java,
            R.layout.add_player_item,
            ForPlayerParametersViewHolderr::class.java,
            database
        ) {

            override fun populateViewHolder(p0: ForPlayerParametersViewHolderr?, p1: ParentParameter?, p2: Int) {
                if(arguments!=null){
                    db.child("Players").addListenerForSingleValueEvent(object :ValueEventListener{
                        override fun onCancelled(d0: DatabaseError) {

                        }

                        override fun onDataChange(d0: DataSnapshot) {
                            if(d0.exists()) {
                                var plr = d0.child(playerName.text.toString()).getValue(Players::class.java)
                                Log.d("adpl", "fetched pl  ${plr!!.name}")
                                if (plr != null) {
                                    for (p in plr.params!!) {
                                        if (p.name.equals(p1!!.name)) {
                                            p0!!.parentParamValue.setText(p.totalValue)
                                        }
                                    }
                                }
                            }

                        }

                    })
                }
                p0!!.parentParamName?.text = p1?.name
                p0.parentParamValue.text = "0"
                list.add(p1!!)

                p0.editButton.setOnClickListener {
                    for(l in list){
                        Log.d("adpl",l.name)
                    }
                    if (playerName.text.isNotEmpty()) {
                        if (arguments == null) {
                            var player = Players()
                            player.name=playerName.text.toString()
                            player.picpath=""
                            player.params=list
                          db.child("Players").child(playerName.text.toString()).setValue(player) }

                        var bundle = Bundle()
                            bundle.putString("plname", playerName.text.toString())
                            bundle.putString("parpar", parametersAdapter.getItem(p2).name)
                        Log.d("position", p2.toString() )
                            bundle.putInt("pos",p2)
                            var ChildParametersFragment = ChildParametersFragment()
                            ChildParametersFragment.arguments = bundle
                            var fragmentTransaction = fragmentManager!!.beginTransaction()
                            fragmentTransaction.replace(R.id.main_content, ChildParametersFragment)
                            fragmentTransaction.commit()

                    } else {
                        return@setOnClickListener
                    }
                }
            }

        }
        // playerParameters.isNestedScrollingEnabled=false
        playerParameters.layoutManager = layoutManager
        playerParameters.adapter = parametersAdapter
save_player_button.setOnClickListener {
    db.child("Players").addListenerForSingleValueEvent(object :ValueEventListener{
        override fun onCancelled(d0: DatabaseError) {

        }

        override fun onDataChange(d0: DataSnapshot) {
            if (d0.exists()) {
                var plr = d0.child(playerName.text.toString()).getValue(Players::class.java)
                Log.d("adpl", "fetched pl  ${plr!!.name}")
                if (plr != null) {
                    db.child("Final Players").child(playerName.text.toString()).setValue(plr)
                    db.child("Players").removeValue()
                }
            }
        }
    })

    var AddPlayerFragment=AddPlayerFragment()
    var fragmentTransaction=fragmentManager!!.beginTransaction()
    fragmentTransaction.replace(R.id.main_content,AddPlayerFragment)
    fragmentTransaction.commit()


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
        save_player_button = frview.findViewById(R.id.save_player_button)
        layoutManager = LinearLayoutManager(context)
    }
}