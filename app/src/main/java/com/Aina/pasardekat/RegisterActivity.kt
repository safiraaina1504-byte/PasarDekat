package com.Aina.pasardekat

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.Aina.pasardekat.UserDao
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {

    private lateinit var etNama: EditText
    private lateinit var etNamaToko: EditText
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText

    private lateinit var btnRegister: Button
    private lateinit var btnMasuk: Button

    private lateinit var layoutPembeli: LinearLayout
    private lateinit var layoutPenjual: LinearLayout

    private lateinit var userDao: UserDao

    private var role = "Penjual"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_register)

        etNama = findViewById(R.id.etNama)
        etNamaToko = findViewById(R.id.etNamaToko)
        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)

        btnRegister = findViewById(R.id.btnRegister)
        btnMasuk = findViewById(R.id.btnMasuk)

        layoutPembeli = findViewById(R.id.layoutPembeli)
        layoutPenjual = findViewById(R.id.layoutPenjual)

        val db = AppDatabase.getDatabase(this)
        userDao = db.userDao()

        layoutPenjual.setBackgroundResource(
            R.drawable.bg_role_selected
        )

        layoutPembeli.setBackgroundResource(
            R.drawable.bg_role
        )

        etNamaToko.visibility = View.VISIBLE

        layoutPembeli.setOnClickListener {

            role = "Pembeli"

            layoutPembeli.setBackgroundResource(
                R.drawable.bg_role_selected
            )

            layoutPenjual.setBackgroundResource(
                R.drawable.bg_role
            )

            etNama.hint = "Nama Lengkap"

            etNamaToko.visibility = View.GONE
        }

        layoutPenjual.setOnClickListener {

            role = "Penjual"

            layoutPenjual.setBackgroundResource(
                R.drawable.bg_role_selected
            )

            layoutPembeli.setBackgroundResource(
                R.drawable.bg_role
            )

            etNama.hint = "Nama Pemilik"

            etNamaToko.visibility = View.VISIBLE
        }

        btnMasuk.setOnClickListener {

            startActivity(
                Intent(
                    this,
                    LoginActivity::class.java
                )
            )

            finish()
        }

        btnRegister.setOnClickListener {

            val nama =
                etNama.text.toString().trim()

            val namaToko =
                etNamaToko.text.toString().trim()

            val email =
                etEmail.text.toString().trim()

            val password =
                etPassword.text.toString().trim()

            if (nama.isEmpty()) {
                etNama.error = "Wajib diisi"
                return@setOnClickListener
            }

            if (
                role == "Penjual" &&
                namaToko.isEmpty()
            ) {
                etNamaToko.error = "Wajib diisi"
                return@setOnClickListener
            }

            if (email.isEmpty()) {
                etEmail.error = "Wajib diisi"
                return@setOnClickListener
            }

            if (password.isEmpty()) {
                etPassword.error = "Wajib diisi"
                return@setOnClickListener
            }

            lifecycleScope.launch {

                userDao.insert(
                    UserEntity(
                        nama = nama,
                        namaToko = namaToko,
                        email = email,
                        password = password,
                        role = role
                    )
                )

                runOnUiThread {

                    Toast.makeText(
                        this@RegisterActivity,
                        "Registrasi Berhasil",
                        Toast.LENGTH_SHORT
                    ).show()

                    startActivity(
                        Intent(
                            this@RegisterActivity,
                            LoginActivity::class.java
                        )
                    )

                    finish()
                }
            }
        }
    }
}

