package com.malenikajkat.classmate.ui.api

import com.malenikajkat.classmate.model.TypeOfLesson
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface TypeOfLessonApi {
    @GET("api/typeoflesson")
    fun getTypesOfLesson(): Call<List<TypeOfLesson>>

    @POST("api/typeoflesson")
    fun postQuery():Call<String>

}