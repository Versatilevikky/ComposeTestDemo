package com.gv.composetestdemo.model

data class UserResponse(
    val info: Info,
    val results: List<User>
)