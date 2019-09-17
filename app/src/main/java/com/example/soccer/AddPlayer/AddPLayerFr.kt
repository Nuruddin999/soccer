package com.example.soccer.AddPlayer

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.soccer.Common
import com.example.soccer.MainActivity
import com.example.soccer.Models.ParentParameter
import com.example.soccer.Models.Players
import com.example.soccer.PlayersList.PlayerEditFr
import com.example.soccer.PlayersList.PlayersList
import com.example.soccer.R
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.player_item.*

class AddPLayerFr : PlayerEditFr() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var frview = inflater.inflate(R.layout.add_player_fragment, container, false)
        initView(frview)
        if (arguments != null) {
            var playername_from_addfrag = arguments!!.getString("plname")
            var picturepath = arguments!!.getString("picpath")
            filepath=Uri.parse(picturepath)
            Log.d("add picture",picturepath)
            playerName.setText(playername_from_addfrag)
            Picasso.get().load(Uri.parse(picturepath)).into(playerPhoto)

        }
        Log.d("player name in pledit", "${playerName.text.toString()}")
        adapter = AddPlayerEditChildParamsAdapter(
            params,
            context!!,
            this.activity as MainActivity,
            playerName
        )
        recyclerView.adapter = adapter
        try {
            db.child("Players").child(playerName.text.toString())
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {

                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        if (p0.exists()) {
                            var player = p0.getValue(Players::class.java)
                            Log.d("playeredit", "${player!!.name}")
                         try {
                             for (p in player.params!!) {
                                 params.add(p)
                                 adapter.notifyDataSetChanged()
                             }
                         } catch (e:Exception){

                         }
                        } else {
                            var databaseref = FirebaseDatabase.getInstance()
                            var database = databaseref.getReference().child("ParentParameter")
                                .addListenerForSingleValueEvent(object : ValueEventListener {
                                    override fun onCancelled(p0: DatabaseError) {

                                    }

                                    override fun onDataChange(p0: DataSnapshot) {
                                        for (p in p0.children) {
                                            var parent_parameter = p.getValue(ParentParameter::class.java)
                                            params.add(parent_parameter!!)
                                            adapter.notifyDataSetChanged()


                                        }

                                    }
                                })
                        }


                    }
                })
        } catch (e: Exception) {

        }

        addPhotoButton.setOnClickListener {
            openImage()
        }
        save_player_button.setOnClickListener {
            if (playerName.text.isNullOrEmpty()) {
                return@setOnClickListener
            } else {
                db.child("Players").addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onCancelled(d0: DatabaseError) {

                    }

                    override fun onDataChange(d0: DataSnapshot) {
                        if (d0.exists()) {

                            var plr = d0.child(playerName.text.toString()).getValue(Players::class.java)
                            db.child("Final Players").child(playerName.text.toString())
                                .setValue(plr)
                            db.child("Players").removeValue()
                        } else {
                            Log.d("filepath", filepath.toString())

                            var databaseref = FirebaseDatabase.getInstance()
                            var database = databaseref.getReference().child("ParentParameter")
                                .addListenerForSingleValueEvent(object : ValueEventListener {
                                    override fun onCancelled(p0: DatabaseError) {

                                    }

                                    override fun onDataChange(p0: DataSnapshot) {
                                        var plr = Players()
                                        var list = ArrayList<ParentParameter>()
                                        for (p in p0.children) {
                                            var parent_parameter = p.getValue(ParentParameter::class.java)
                                            list.add(parent_parameter!!)
                                        }
                                        plr.name = playerName.text.toString()
                                        plr.params = list
                                        db.child("Final Players").child(playerName.text.toString())
                                            .setValue(plr)
                                        db.child("Players").removeValue()

                                    }
                                })


                        }

                    }

                })
            }

            if (filepath != null) {
                uploadImage(filepath)
            }
        }
        return frview
    }

    override fun uploadImage(uri: Uri?) {
        var storage = FirebaseStorage.getInstance()
        var storageref = storage.reference
        storageref = storageref.child("images/${playerName.text.toString()}")
        storageref.putFile(uri!!)
            .addOnCompleteListener(object : OnCompleteListener<UploadTask.TaskSnapshot> {
                override fun onComplete(p0: Task<UploadTask.TaskSnapshot>) {
                    storageref.downloadUrl.addOnCompleteListener(object : OnCompleteListener<Uri> {
                        override fun onComplete(p0: Task<Uri>) {
                            db.child("Final Players").child(playerName.text.toString()).child("picpath")
                                .setValue(p0.result.toString())
                            Toast.makeText(context, "Сохранено", Toast.LENGTH_SHORT).show()
                            var playersList=PlayersList()
                            Common.getFragment(playersList,R.id.main_content,activity as MainActivity)
                        }
                    })
                }
            })
    }


}