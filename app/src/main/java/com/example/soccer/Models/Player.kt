package com.example.soccer.Models

import java.util.ArrayList

class Player {
    var name:String?=null
    var params:ArrayList<ParentParameter>?=null
    constructor(){

    }
    constructor(name: String?, params: ArrayList<ParentParameter>?) {
        this.name = name
        this.params = params
    }
}