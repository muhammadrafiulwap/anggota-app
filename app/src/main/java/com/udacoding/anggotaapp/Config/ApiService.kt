package com.udacoding.anggotaapp.Config

import com.udacoding.anggotaapp.Model.action.ResponseAction
import com.udacoding.anggotaapp.Model.getdata.ResponseGetData
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    //getData
    @GET("getData.php")
    fun getData(): Call<ResponseGetData>

    //getDataById
    @GET("getData.php")
    fun getDataById(@Query("id") id: String): Call<ResponseGetData>

    //insert
    @FormUrlEncoded
    @POST("insert.php")
    fun insertData(@Field("nama") nama : String,
                   @Field("nohp") nohp: String,
                   @Field("alamat") alamat: String): Call<ResponseAction>

    //update
    @FormUrlEncoded
    @POST("update.php")
    fun updateData(@Field("id") id : String,
                   @Field("nama") nama : String,
                   @Field("nohp") nohp: String,
                   @Field("alamat") alamat: String): Call<ResponseAction>

    //delete
    @FormUrlEncoded
    @POST("delete.php")
    fun deleteData(@Field("id") id : String): Call<ResponseAction>

}