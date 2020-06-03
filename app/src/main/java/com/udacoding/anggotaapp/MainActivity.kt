package com.udacoding.anggotaapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.udacoding.anggotaapp.Adapter.DataAdapter
import com.udacoding.anggotaapp.Config.NetworkModule
import com.udacoding.anggotaapp.Model.action.ResponseAction
import com.udacoding.anggotaapp.Model.getdata.DataItem
import com.udacoding.anggotaapp.Model.getdata.ResponseGetData
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fab.setOnClickListener {
            val intent = Intent(this, InputActivity::class.java)
            startActivity(intent)
        }

        showData()

    }

    private fun showData(){

        val listAnggota = NetworkModule.service().getData()
        listAnggota.enqueue(object : Callback<ResponseGetData> {

            override fun onResponse(
                call: Call<ResponseGetData>,
                response: Response<ResponseGetData>
            ) {

                if (response.isSuccessful){

                    val item = response.body()?.data

                    val adapter = DataAdapter(item, object : DataAdapter.OnClickListener {
                        override fun detail(item: DataItem?) {

                            val intent = Intent(applicationContext, InputActivity::class.java)
                            intent.putExtra("data", item)
                            startActivity(intent)


                        }

                        override fun hapus(item: DataItem?) {

                            AlertDialog.Builder(this@MainActivity).apply {
                                setTitle("Hapus Data")
                                setMessage("Yakin mau hapus data ?")
                                setPositiveButton("Hapus") { dialog, which ->
                                    hapusData(item?.id)
                                    dialog.dismiss()
                                }
                                setNegativeButton("Batal") { dialog, which ->
                                    dialog.dismiss()
                                }
                            }.show()


                        }

                    })

                    list.adapter = adapter

                }

            }

            override fun onFailure(call: Call<ResponseGetData>, t: Throwable) {
                Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
            }

        })

    }

    private fun hapusData(id: String?) {

        val hapus = NetworkModule.service().deleteData(id ?: "")
        hapus.enqueue(object : Callback<ResponseAction> {

            override fun onResponse(
                call: Call<ResponseAction>,
                response: Response<ResponseAction>
            ) {

                Toast.makeText(applicationContext, "Data Berhasil dihapus", Toast.LENGTH_SHORT).show()
                showData()

            }

            override fun onFailure(call: Call<ResponseAction>, t: Throwable) {

                Toast.makeText(applicationContext, t.message, Toast.LENGTH_SHORT).show()

            }

        })

    }

    override fun onResume() {
        super.onResume()
        showData()
    }



}
