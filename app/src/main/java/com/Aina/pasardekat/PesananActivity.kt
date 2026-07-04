package com.Aina.pasardekat

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout

class PesananActivity : AppCompatActivity() {

    private lateinit var rvPesanan: RecyclerView
    private lateinit var btnKembali: Button
    private lateinit var btnKeranjang: Button
    private lateinit var tabLayout: TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pesanan)

        rvPesanan = findViewById(R.id.rvPesanan)
        btnKembali = findViewById(R.id.btnKembali)
        btnKeranjang = findViewById(R.id.btnKeranjang)
        tabLayout = findViewById(R.id.tabLayout)

        rvPesanan.layoutManager = LinearLayoutManager(this)

        btnKembali.setOnClickListener {
            finish()
        }

        btnKeranjang.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    KeranjangActivity::class.java
                )
            )
        }

        tabLayout.selectTab(tabLayout.getTabAt(1))

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

            override fun onTabSelected(tab: TabLayout.Tab) {

                when (tab.position) {

                    0 -> {
                        finish()
                    }

                    1 -> {
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}

            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

        // Adapter pesanan dipasang di sini
        // rvPesanan.adapter = PesananAdapter(...)
    }
}