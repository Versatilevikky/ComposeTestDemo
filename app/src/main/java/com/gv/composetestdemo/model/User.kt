package com.gv.composetestdemo.model
import java.io.Serializable;


data class User(
    val gender: String,
    val name: Name,
    val phone:String,
    val email:String,
    val nat:String
): Serializable