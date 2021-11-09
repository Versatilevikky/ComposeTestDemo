package com.gv.composetestdemo.model
import java.io.Serializable;


data class User(
    val gender: String,
    val name: Name,
    var phone:String,
    val email:String,
    val nat:String
): Serializable