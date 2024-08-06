package com.malenikajkat.classmate.ui.api

import com.malenikajkat.classmate.model.ThemeNavigation
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface ThemeApi {
    @GET("api/theme")
    fun getThemes(): Call<List<ThemeNavigation>>

}