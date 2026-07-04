package com.Aina.pasardekat

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import android.widget.ArrayAdapter
import android.widget.Spinner
class EditProdukActivity : AppCompatActivity() {

    private lateinit var etNamaProduk: EditText
    private lateinit var etHarga: EditText
    private lateinit var spHarga: Spinner
    private lateinit var spStok: Spinner
    private lateinit var etStok: EditText

    private lateinit var btnUpdate: Button

    private lateinit var produkDao: ProdukDao

    private var idProduk = 0
    private var fotoProduk = ""
    private var satuanHarga = ""
    private var satuanStok = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(
            R.layout.activity_edit_produk
        )

        etNamaProduk =
            findViewById(R.id.etNamaProduk)

        etHarga =
            findViewById(R.id.etHarga)

        etStok =
            findViewById(R.id.etStok)
        spHarga =
            findViewById(R.id.spHarga)

        spStok =
            findViewById(R.id.spStok)

        val satuan = arrayOf(
            "Per KG",
            "Per Dus",
            "Per PCS"
        )

        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            satuan
        )

        spHarga.adapter = adapter
        spStok.adapter = adapter
        btnUpdate =
            findViewById(R.id.btnUpdate)

        val db =
            AppDatabase.getDatabase(this)

        produkDao =
            db.produkDao()

        idProduk =
            intent.getIntExtra(
                "ID_PRODUK",
                0
            )

        etNamaProduk.setText(
            intent.getStringExtra("NAMA")
        )

        etHarga.setText(
            intent.getIntExtra("HARGA",0).toString()
        )

        etStok.setText(
            intent.getIntExtra("STOK",0).toString()
        )

        fotoProduk =
            intent.getStringExtra(
                "FOTO"
            ) ?: ""
        satuanHarga =
            intent.getStringExtra("SATUAN_HARGA") ?: "Per KG"

        satuanStok =
            intent.getStringExtra("SATUAN_STOK") ?: "Per KG"
        spHarga.setSelection(
            adapter.getPosition(satuanHarga)
        )

        spStok.setSelection(
            adapter.getPosition(satuanStok)
        )
        btnUpdate.setOnClickListener {

            updateProduk()
        }
    }

    private fun updateProduk() {

        val namaProduk =
            etNamaProduk.text.toString().trim()

        val harga =
            etHarga.text.toString().trim()

        val stok =
            etStok.text.toString().trim()

        if (namaProduk.isEmpty()) {
            etNamaProduk.error =
                "Nama produk wajib diisi"
            return
        }

        if (harga.isEmpty()) {
            etHarga.error =
                "Harga wajib diisi"
            return
        }

        if (stok.isEmpty()) {
            etStok.error =
                "Stok wajib diisi"
            return
        }

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
                id = idProduk,
                namaProduk = namaProduk,
                harga = harga.toInt(),
                stok = stok.toInt(),
                satuanHarga = spHarga.selectedItem.toString(),
                satuanStok = spStok.selectedItem.toString(),
                namaToko = namaToko,
                fotoProduk = fotoProduk
            )

        lifecycleScope.launch {

            produkDao.update(
                produk
            )

            Toast.makeText(
                this@EditProdukActivity,
                "Produk berhasil diupdate",
                Toast.LENGTH_SHORT
            ).show()

            finish()
        }
    }
}