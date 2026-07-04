package com.Aina.pasardekat

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.launch

class DashboardPembeliActivity : AppCompatActivity() {

    private lateinit var rvData: RecyclerView

    private lateinit var btnKeluar: LinearLayout
    private lateinit var btnKeranjang: ImageView
    private lateinit var txtBadge: TextView

    private lateinit var tabLayout: TabLayout

    private lateinit var produkDao: ProdukDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard_pembeli)

        rvData = findViewById(R.id.rvData)
        btnKeluar = findViewById(R.id.btnKeluar)
        btnKeranjang = findViewById(R.id.btnKeranjang)
        txtBadge = findViewById(R.id.txtBadge)
        tabLayout = findViewById(R.id.tabLayout)

        rvData.layoutManager = LinearLayoutManager(this)

        val db = AppDatabase.getDatabase(this)
        produkDao = db.produkDao()

        loadProduk()
        updateBadge()

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

            override fun onTabSelected(tab: TabLayout.Tab) {

                when (tab.position) {

                    0 -> loadProduk()

                    1 -> loadToko()

                    2 -> startActivity(
                        Intent(
                            this@DashboardPembeliActivity,
                            PesananActivity::class.java
                        )
                    )
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}

            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

        btnKeranjang.setOnClickListener {

            startActivity(
                Intent(
                    this,
                    KeranjangActivity::class.java
                )
            )

        }

        btnKeluar.setOnClickListener {

            startActivity(
                Intent(
                    this,
                    LoginActivity::class.java
                )
            )

            finish()

        }
    }

    fun updateBadge() {

        val jumlah = DataKeranjang.listKeranjang.size

        if (jumlah == 0) {

            txtBadge.visibility = View.GONE

        } else {

            txtBadge.visibility = View.VISIBLE
            txtBadge.text = jumlah.toString()

        }

    }

    private fun loadProduk() {

        lifecycleScope.launch {

            try {

                val listProduk = produkDao.getAllProduk()

                rvData.adapter = ProdukAdapter(
                    ArrayList(listProduk)
                )

                updateBadge()

            } catch (e: Exception) {

                e.printStackTrace()

            }

        }

    }

    private fun loadToko() {

        lifecycleScope.launch {

            try {

                val listToko = produkDao.getAllToko()

                rvData.adapter = TokoAdapter(
                    ArrayList(listToko)
                )

            } catch (e: Exception) {

                e.printStackTrace()

            }

        }

    }

    override fun onResume() {
        super.onResume()
        updateBadge()
        loadProduk()
    }
}