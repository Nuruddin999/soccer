package com.example.soccer.Models

import java.util.ArrayList

class ParentParameter {
    var name:String?=null
    var totalValue:String?=null
    var parameters: ArrayList<Parameter>?=null
    constructor(){

    }
     constructor(name: String?,totalValue:String, parameters: ArrayList<Parameter>?) {
        this.name = name
         this.totalValue=totalValue
        this.parameters = parameters

    }
}