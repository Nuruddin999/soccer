package com.example.soccer.Models

import java.util.ArrayList

class Players {
    var name: String? = null
    var picpath: String? = null
    var params: ArrayList<ParentParameter>? = null

    constructor() {

    }

    constructor(name: String?, picpath: String?, params: ArrayList<ParentParameter>?) {
        this.name = name
        this.picpath = picpath
        this.params = params
    }
}