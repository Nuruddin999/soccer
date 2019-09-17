package com.example.soccer.PlayersList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.soccer.AddPlayer.AddPLayerFr
import com.example.soccer.AddPlayer.AddPlayerFragment
import com.example.soccer.Common
import com.example.soccer.MainActivity
import com.example.soccer.Models.Players
import com.example.soccer.R
import com.google.firebase.database.*

class PlayersList:Fragment(){
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: PlayerAdapter
    lateinit var layoutManager: RecyclerView.LayoutManager
    lateinit var add_player_button:Button
    var players = ArrayList<Players>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var databaseref = FirebaseDatabase.getInstance()
        var db = databaseref.getReference().child("Players").removeValue()
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var v: View = inflater.inflate(R.layout.players_list_fragment, container, false)
        recyclerView=v.findViewById(R.id.players_list)
        layoutManager = LinearLayoutManager(context)
        adapter=PlayerAdapter(players,context!!,this.activity)
        recyclerView.layoutManager=layoutManager
        recyclerView.adapter=adapter
        var databaseref = FirebaseDatabase.getInstance()
        var db = databaseref.getReference().child("Final Players").addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
for (p in p0.children){
    var pl=p.getValue(Players::class.java)
    players.add(pl!!)
}
                adapter.notifyDataSetChanged()
            }
        })
         add_player_button=v.findViewById(R.id.new_player_button)
        add_player_button.setOnClickListener {
            //var AddPlayerFragment = AddPlayerFragment()
var AddPLayerFr=AddPLayerFr()
            Common.getFragment(AddPLayerFr,R.id.main_content, activity as MainActivity)
        }
        return  v
    }
}