package com.example.soccer.PlayersList

import android.app.Activity
import android.content.Context
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
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.soccer.AddPlayer.AddPlayerFragment
import com.example.soccer.AddPlayer.ForPlayerParametersAdapter
import com.example.soccer.Common
import com.example.soccer.MainActivity
import com.example.soccer.Models.ParentParameter
import com.example.soccer.Models.Players
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
import kotlinx.android.synthetic.main.add_player_fragment.*
import java.io.FileDescriptor

open class PlayerEditFr : Fragment() {
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: ForPlayerParametersAdapter
    lateinit var layoutManager: RecyclerView.LayoutManager
    lateinit var playerName: EditText
    lateinit var playerPhoto: ImageView
    lateinit var addPhotoButton: ImageView
    lateinit var playerParameters: RecyclerView
    lateinit var save_player_button: Button
    var filepath:Uri?=null
    var params = ArrayList<ParentParameter>()
    var databaseref = FirebaseDatabase.getInstance()
    var db = databaseref.getReference()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var frview = inflater.inflate(R.layout.add_player_fragment, container, false)
        initView(frview)
        if (arguments != null) {
            var playername_from_addfrag = arguments!!.getString("plname")
            playerName.setText(playername_from_addfrag)
            var picturepath = arguments!!.getString("picpath")
            if (!picturepath.isNullOrEmpty()){
                filepath=Uri.parse(picturepath)
                Picasso.get().load(Uri.parse(picturepath)).into(playerPhoto)
            }

            Log.d("add picture",picturepath)
            playerName.setText(playername_from_addfrag)

        }
        Log.d("player name in pledit", "${playerName.text.toString()}")
        adapter =
            ForPlayerParametersAdapter(params, context!!, this.activity as MainActivity, playerName)
        recyclerView.adapter = adapter
        db.child("Final Players").child(playerName.text.toString())
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {

                }

                override fun onDataChange(p0: DataSnapshot) {
                    var player = p0.getValue(Players::class.java)
                    Log.d("playeredit", "${player!!.name}")
                    for (p in player.params!!) {
                        params.add(p)
                        adapter.notifyDataSetChanged()
                    }


                }
            })
        addPhotoButton.setOnClickListener {
            openImage()
        }
        save_player_button.setOnClickListener {

            if (filepath!=null){
                uploadImage(filepath)
            }

        }
        return frview
    }

    internal fun openImage() {
        var intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.setType("image/*")
        startActivityForResult(intent, AddPlayerFragment.READ_REQUEST_CODE)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == AddPlayerFragment.READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            var uri: Uri? = null
            if (data != null) {
                uri = data.data
               filepath=uri
                val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
                with (sharedPref.edit()) {
                    putString("picpath",filepath.toString())
                    commit()
                }
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

    internal open fun uploadImage(uri: Uri?) {
        var storage = FirebaseStorage.getInstance()
        var storageref = storage.reference
        storageref = storageref.child("images/${playerName.text.toString()}")
        storageref.putFile(uri!!)
            .addOnCompleteListener(object : OnCompleteListener<UploadTask.TaskSnapshot> {
                override fun onComplete(p0: Task<UploadTask.TaskSnapshot>) {
                    storageref.downloadUrl.addOnCompleteListener(object : OnCompleteListener<Uri> {
                        override fun onComplete(p0: Task<Uri>) {
                            db.child("Final Players").child(playerName.text.toString()).child("picpath").setValue(p0.result.toString()).addOnCompleteListener {
                                var PlayersList=PlayersList()
                                Common.getFragment(PlayersList,R.id.main_content,activity as MainActivity)
                            }

                        }
                    })
                }
            })
    }


    fun initView(frview: View?) {
        playerPhoto = frview!!.findViewById(R.id.add_player_photo)
        addPhotoButton = frview!!.findViewById(R.id.add_photo_button)
        playerName = frview!!.findViewById(R.id.add_player_input_name)
        playerParameters = frview!!.findViewById(R.id.add_player_parent_parameterlist)
        save_player_button = frview.findViewById(R.id.save_player_button)
        layoutManager = LinearLayoutManager(context)
        recyclerView = frview.findViewById(R.id.add_player_parent_parameterlist)
        layoutManager = LinearLayoutManager(context)
        Log.d("player name in pledit", "${playerName.text.toString()}")
        recyclerView.layoutManager = layoutManager

    }
}
