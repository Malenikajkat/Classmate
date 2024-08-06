package com.malenikajkat.classmate.model

import java.io.Serializable

data class ThemeNavigation(
    val nom:Short,
    val name:String
):Serializable{
    override fun toString(): String = name
}
