package com.Aina.pasardekat

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText

    private lateinit var btnLogin: Button
    private lateinit var btnDaftar: Button

    private lateinit var txtDaftar: TextView

    private lateinit var layoutPembeli: LinearLayout
    private lateinit var layoutPenjual: LinearLayout

    private lateinit var userDao: UserDao

    private var roleDipilih = "Penjual"

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(
            R.layout.activity_login
        )

        etEmail =
            findViewById(R.id.etEmail)

        etPassword =
            findViewById(R.id.etPassword)

        btnLogin =
            findViewById(R.id.btnLogin)

        btnDaftar =
            findViewById(R.id.btnDaftar)

        txtDaftar =
            findViewById(R.id.txtDaftar)

        layoutPembeli =
            findViewById(R.id.layoutPembeli)

        layoutPenjual =
            findViewById(R.id.layoutPenjual)

        val db =
            AppDatabase.getDatabase(this)

        userDao =
            db.userDao()

        layoutPenjual.setBackgroundResource(
            R.drawable.bg_role_selected
        )

        layoutPembeli.setBackgroundResource(
            R.drawable.bg_role
        )

        layoutPembeli.setOnClickListener {

            roleDipilih = "Pembeli"

            layoutPembeli.setBackgroundResource(
                R.drawable.bg_role_selected
            )

            layoutPenjual.setBackgroundResource(
                R.drawable.bg_role
            )
        }

        layoutPenjual.setOnClickListener {

            roleDipilih = "Penjual"

            layoutPenjual.setBackgroundResource(
                R.drawable.bg_role_selected
            )

            layoutPembeli.setBackgroundResource(
                R.drawable.bg_role
            )
        }

        btnDaftar.setOnClickListener {

            startActivity(
                Intent(
                    this,
                    RegisterActivity::class.java
                )
            )
        }

        txtDaftar.setOnClickListener {

            startActivity(
                Intent(
                    this,
                    RegisterActivity::class.java
                )
            )
        }

        btnLogin.setOnClickListener {

            val emailInput =
                etEmail.text.toString().trim()

            val passwordInput =
                etPassword.text.toString().trim()

            if (
                emailInput.isEmpty() ||
                passwordInput.isEmpty()
            ) {

                Toast.makeText(
                    this,
                    "Lengkapi data login",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            lifecycleScope.launch {

                val user =
                    userDao.login(
                        emailInput,
                        passwordInput
                    )

                runOnUiThread {

                    if (user == null) {

                        Toast.makeText(
                            this@LoginActivity,
                            "Email atau Password salah",
                            Toast.LENGTH_SHORT
                        ).show()

                        return@runOnUiThread
                    }

                    if (user.role != roleDipilih) {

                        Toast.makeText(
                            this@LoginActivity,
                            "Role tidak sesuai",
                            Toast.LENGTH_SHORT
                        ).show()

                        return@runOnUiThread
                    }

                    getSharedPreferences(
                        "PasarDekat",
                        MODE_PRIVATE
                    )
                        .edit()
                        .putInt(
                            "user_id",
                            user.id
                        )
                        .putString(
                            "email_login",
                            user.email
                        )
                        .putString(
                            "namaToko",
                            user.namaToko
                        )
                        .putString(
                            "role",
                            user.role
                        )
                        .apply()

                    Toast.makeText(
                        this@LoginActivity,
                        "Login Berhasil",
                        Toast.LENGTH_SHORT
                    ).show()

                    if (
                        user.role == "Penjual"
                    ) {

                        startActivity(
                            Intent(
                                this@LoginActivity,
                                DashboardPenjualActivity::class.java
                            )
                        )

                    } else {

                        startActivity(
                            Intent(
                                this@LoginActivity,
                                DashboardPembeliActivity::class.java
                            )
                        )
                    }

                    finish()
                }
            }
        }
    }
}