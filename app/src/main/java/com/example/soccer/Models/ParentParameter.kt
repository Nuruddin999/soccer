package com.example.soccer.Models

import java.util.ArrayList

class ParentParameter {
    var name:String?=null
    var parameters: ArrayList<Parameter>?=null
    constructor(){

    }
    constructor(name: String?, parameters: ArrayList<Parameter>?) {
        this.name = name
        this.parameters = parameters
    }
}