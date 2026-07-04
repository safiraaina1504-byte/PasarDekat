package com.Aina.pasardekat

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import kotlinx.coroutines.launch

class KeranjangActivity : AppCompatActivity() {

    private lateinit var rvKeranjang: RecyclerView
    private lateinit var btnCheckout: Button

    private lateinit var adapter: KeranjangAdapter

    private lateinit var pesananDao: PesananDao
    private lateinit var notifikasiDao: NotifikasiDao

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(
            R.layout.activity_keranjang
        )

        rvKeranjang =
            findViewById(R.id.rvKeranjang)

        btnCheckout =
            findViewById(R.id.btnCheckout)

        val db =
            AppDatabase.getDatabase(this)

        pesananDao =
            db.pesananDao()

        notifikasiDao =
            db.notifikasiDao()

        rvKeranjang.layoutManager =
            LinearLayoutManager(this)

        adapter =
            KeranjangAdapter(
                DataKeranjang.listKeranjang
            )

        rvKeranjang.adapter =
            adapter

        btnCheckout.setOnClickListener {

            checkout()
        }
    }

    private fun checkout() {

        if (
            DataKeranjang.listKeranjang.isEmpty()
        ) {

            Toast.makeText(
                this,
                "Keranjang masih kosong",
                Toast.LENGTH_SHORT
            ).show()

            return
        }

        lifecycleScope.launch {

            DataKeranjang.listKeranjang.forEach { item ->

                val totalHarga =
                    item.harga.toInt() *
                            item.jumlah

                pesananDao.insert(

                    PesananEntity(

                        namaPembeli =
                            "Pembeli",

                        namaToko =
                            item.namaToko,

                        namaProduk =
                            item.namaProduk,

                        jumlah =
                            item.jumlah,

                        totalHarga =
                            totalHarga,

                        status =
                            "Menunggu"
                    )
                )

                notifikasiDao.insert(

                    NotifikasiEntity(

                        namaPembeli =
                            "Pembeli",
                        namaToko =
                            item.namaToko,
                        namaProduk =
                            item.namaProduk,

                        jumlah =
                            item.jumlah,

                        totalHarga =
                            totalHarga,
                        status =
                            "Menunggu"
                    )
                )
            }

            Toast.makeText(
                this@KeranjangActivity,
                "Checkout berhasil",
                Toast.LENGTH_SHORT
            ).show()

            DataKeranjang.listKeranjang.clear()

            adapter.notifyDataSetChanged()
        }
    }

    override fun onResume() {
        super.onResume()

        adapter.notifyDataSetChanged()
    }
}