package com.Aina.pasardekat

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.coroutines.launch
import android.widget.LinearLayout
import android.widget.Toast
import android.view.View
import com.google.android.material.tabs.TabLayout
import android.widget.ArrayAdapter
import android.widget.Spinner


class DashboardPenjualActivity : AppCompatActivity() {
    private lateinit var tabLayout: TabLayout
    private lateinit var txtJudul: TextView
    private lateinit var txtNamaToko: TextView
    private lateinit var txtJumlahProduk: TextView
    private lateinit var txtTotalProduk: TextView
    private lateinit var txtPendapatan: TextView

    private lateinit var btnTambah: Button
    private lateinit var btnKeluar: TextView
    private lateinit var btnNotifikasi: LinearLayout

    private lateinit var rvProduk: RecyclerView

    private lateinit var produkDao: ProdukDao
    private lateinit var pendapatanDao: PendapatanDao

    private lateinit var adapter: ProdukPenjualAdapter

    private var fotoUri: Uri? = null
    private var imgPreview: ImageView? = null

    private val pilihFotoLauncher =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->

            if (result.resultCode == RESULT_OK) {

                fotoUri =
                    result.data?.data

                fotoUri?.let { uri ->

                    contentResolver
                        .takePersistableUriPermission(
                            uri,
                            Intent.FLAG_GRANT_READ_URI_PERMISSION
                        )

                    imgPreview?.setImageURI(uri)
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_dashboard_penjual)
        tabLayout = findViewById(R.id.tabLayout)
        txtJudul = findViewById(R.id.txtJudul)
        txtNamaToko = findViewById(R.id.txtNamaToko)
        txtJumlahProduk = findViewById(R.id.txtJumlahProduk)
        txtTotalProduk = findViewById(R.id.txtTotalProduk)
        txtPendapatan = findViewById(R.id.txtPendapatan)

        btnTambah = findViewById(R.id.btnTambah)
        btnKeluar = findViewById(R.id.btnKeluar)
        btnNotifikasi = findViewById(R.id.btnNotifikasi)

        rvProduk = findViewById(R.id.rvProduk)

        val db = AppDatabase.getDatabase(this)

        produkDao = db.produkDao()
        pendapatanDao = db.pendapatanDao()

        rvProduk.layoutManager =
            LinearLayoutManager(this)

        val sharedPref =
            getSharedPreferences(
                "PasarDekat",
                MODE_PRIVATE
            )

        txtNamaToko.text =
            sharedPref.getString(
                "namaToko",
                "Toko Saya"
            )

        loadProduk()
        loadPendapatan()
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

            override fun onTabSelected(tab: TabLayout.Tab?) {

                when (tab?.position) {

                    0 -> {

                        txtJudul.text = "Produk"

                        btnTambah.visibility = View.VISIBLE

                        loadProduk()

                    }

                    1 -> {

                        txtJudul.text = "Pesanan"

                        btnTambah.visibility = View.GONE

                        loadPesanan()

                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
        btnTambah.setOnClickListener {
            showTambahProdukBottomSheet()
        }

        btnNotifikasi.setOnClickListener {

            tabLayout.getTabAt(1)?.select()

        }

        btnKeluar.setOnClickListener {

            val intent =
                Intent(
                    this@DashboardPenjualActivity,
                    LoginActivity::class.java
                )

            intent.flags =
                Intent.FLAG_ACTIVITY_NEW_TASK or
                        Intent.FLAG_ACTIVITY_CLEAR_TASK

            startActivity(intent)

            finish()
        }

    }

    private fun loadProduk() {

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

            val listProduk =
                produkDao.getProdukByToko(
                    namaToko
                )
            txtJumlahProduk.text =
                "${listProduk.size} Produk Aktif"

            txtTotalProduk.text =
                listProduk.size.toString()

            adapter =
                ProdukPenjualAdapter(
                    ArrayList(listProduk)
                ) { produk ->

                    lifecycleScope.launch {

                        produkDao.delete(produk)

                        loadProduk()
                    }
                }

            rvProduk.adapter = adapter
        }
    }
    private fun loadPesanan() {

        Toast.makeText(
            this,
            "Belum ada pesanan",
            Toast.LENGTH_SHORT
        ).show()

    }
    private fun loadPendapatan() {

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

            val pendapatan =
                pendapatanDao.getPendapatanByToko(
                    namaToko
                )

            txtPendapatan.text =
                RupiahHelper.format(
                    pendapatan?.totalPendapatan ?: 0
                )
        }
    }

    private fun showTambahProdukBottomSheet() {

        fotoUri = null

        val dialog =
            BottomSheetDialog(this)

        val view =
            layoutInflater.inflate(
                R.layout.bottomsheet_tambah_produk,
                null
            )

        dialog.setContentView(view)

        imgPreview =
            view.findViewById(R.id.imgPreview)

        val btnPilihFoto =
            view.findViewById<Button>(
                R.id.btnPilihFoto
            )

        val etNamaProduk =
            view.findViewById<EditText>(
                R.id.etNamaProduk
            )

        val etHarga =
            view.findViewById<EditText>(
                R.id.etHarga
            )

        val etStok =
            view.findViewById<EditText>(
                R.id.etStok
            )
        val spHarga =
            view.findViewById<Spinner>(
                R.id.spHarga
            )

        val spStok =
            view.findViewById<Spinner>(
                R.id.spStok
            )

        val satuan = arrayOf(
            "Per KG",
            "Per Dus",
            "Per PCS"
        )

        val adapterSatuan = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            satuan
        )

        spHarga.adapter = adapterSatuan
        spStok.adapter = adapterSatuan
        val btnSimpan =
            view.findViewById<Button>(
                R.id.btnSimpan
            )

        btnPilihFoto.setOnClickListener {

            val intent =
                Intent(Intent.ACTION_OPEN_DOCUMENT).apply {

                    addCategory(
                        Intent.CATEGORY_OPENABLE
                    )

                    type = "image/*"

                    flags =
                        Intent.FLAG_GRANT_READ_URI_PERMISSION or
                                Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION
                }

            pilihFotoLauncher.launch(intent)
        }

        btnSimpan.setOnClickListener {

            val namaProduk =
                etNamaProduk.text.toString().trim()

            val harga =
                etHarga.text.toString().trim()

            val stok =
                etStok.text.toString().trim()
            if (
                namaProduk.isEmpty() ||
                harga.isEmpty() ||
                stok.isEmpty()
            ) {

                Toast.makeText(
                    this,
                    "Lengkapi data terlebih dahulu",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }
            val satuanHarga = spHarga.selectedItem.toString()
            val satuanStok = spStok.selectedItem.toString()
            lifecycleScope.launch {

                val sharedPref =
                    getSharedPreferences(
                        "PasarDekat",
                        MODE_PRIVATE
                    )

                val namaToko =
                    sharedPref.getString(
                        "namaToko",
                        "Toko Saya"
                    ) ?: "Toko Saya"

                val produk =
                    ProdukEntity(
                        namaProduk = namaProduk,
                        harga = harga.toInt(),
                        stok = stok.toInt(),
                        satuanHarga = spHarga.selectedItem.toString(),
                        satuanStok = spStok.selectedItem.toString(),
                        namaToko = namaToko,
                        fotoProduk = fotoUri?.toString() ?: ""
                    )

                produkDao.insert(produk)

                loadProduk()

                dialog.dismiss()
            }
        }

        dialog.show()
    }

    override fun onResume() {
        super.onResume()

        loadProduk()
        loadPendapatan()
    }
}