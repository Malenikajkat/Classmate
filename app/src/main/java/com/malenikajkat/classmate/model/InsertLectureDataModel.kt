package com.malenikajkat.classmate.model

import java.io.Serializable

data class InsertLectureDataModel (
    val Day :String,
    val Group :Int,
    val Tema :Short,
    val Teacher :Short,
    val Hours :Byte,
    val VidZan :Byte
):Serializable