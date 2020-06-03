package com.udacoding.anggotaapp.Config

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkModule {

    fun getRetrofit() : Retrofit {
        return Retrofit.Builder().baseUrl("http://192.168.0.118/mentoringkotlin/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun service() : ApiService = getRetrofit().create(ApiService::class.java)

}