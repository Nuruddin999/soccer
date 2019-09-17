package com.example.soccer

import android.content.Context
import androidx.fragment.app.Fragment

object Common:Fragment() {
    var fragment=Fragment()
    var view:Int?=null
    fun getFragment( fragment: Fragment, view:Int,activity: MainActivity){
        activity.supportFragmentManager
        var fragmentTransaction=activity.supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(view, fragment)
        fragmentTransaction.commit()

    }
}