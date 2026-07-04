package com.Aina.pasardekat

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch

class NotifikasiActivity :
    AppCompatActivity(),
    NotifikasiAdapter.NotifikasiListener {

    private lateinit var rvNotifikasi: RecyclerView

    private lateinit var notifikasiDao: NotifikasiDao

    private lateinit var pendapatanDao: PendapatanDao

    override fun onCreate(
        savedInstanceState: Bundle?
    ) {

        super.onCreate(savedInstanceState)

        setContentView(
            R.layout.activity_notifikasi
        )

        rvNotifikasi =
            findViewById(R.id.rvNotifikasi)

        rvNotifikasi.layoutManager =
            LinearLayoutManager(this)

        val db =
            AppDatabase.getDatabase(this)

        notifikasiDao =
            db.notifikasiDao()

        pendapatanDao =
            db.pendapatanDao()

        loadData()
    }

    private fun loadData() {

        lifecycleScope.launch {

            val sharedPref =
                getSharedPreferences(
                    "PasarDekat",
                    MODE_PRIVATE
                )

            val namaToko =
                sharedPref.getString(
                    "namaToko",
                    ""
                ) ?: ""

            val data =
                notifikasiDao.getByToko(
                    namaToko
                )

            rvNotifikasi.adapter =
                NotifikasiAdapter(
                    ArrayList(data),
                    this@NotifikasiActivity
                )
        }
    }

    override fun onProses(
        notifikasi: NotifikasiEntity
    ) {

        lifecycleScope.launch {

            notifikasiDao.update(
                notifikasi.copy(
                    status = "Diproses"
                )
            )

            loadData()
        }
    }

    override fun onSelesai(
        notifikasi: NotifikasiEntity
    ) {

        lifecycleScope.launch {

            notifikasiDao.update(
                notifikasi.copy(
                    status = "Selesai"
                )
            )

            val pendapatan =
                pendapatanDao.getPendapatanByToko(
                    notifikasi.namaToko
                )

            if (pendapatan == null) {

                pendapatanDao.insert(
                    PendapatanEntity(
                        namaToko =
                            notifikasi.namaToko,

                        totalPendapatan =
                            notifikasi.totalHarga
                    )
                )

            } else {

                pendapatanDao.update(
                    pendapatan.copy(
                        totalPendapatan =
                            pendapatan.totalPendapatan +
                                    notifikasi.totalHarga
                    )
                )
            }

            loadData()
        }
    }
}