package com.example.aj.emergencynotifier

/**
 * Created by AJ on 16/03/2018.
 */
class User {
    var id : Int = 0
    var number : String = ""
    var name : String = ""
    constructor(number : String, name : String){
        this.number = number
        this.name = name
    }
    constructor(){}
}