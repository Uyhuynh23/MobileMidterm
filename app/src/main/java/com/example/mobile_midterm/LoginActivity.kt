package com.example.mobile_midterm.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mobile_midterm.Activity.MainActivity
import com.example.mobile_midterm.Domain.UsersModel
import com.example.mobile_midterm.Helper.TinyDB
import com.example.mobile_midterm.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var tinyDB: TinyDB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        tinyDB = TinyDB(this)

        binding.loginBtn.setOnClickListener {
            val inputEmail = binding.usernameEdit.text.toString().trim()
            val inputPassword = binding.passwordEdit.text.toString().trim()

            if (inputEmail.isEmpty() || inputPassword.isEmpty()) {
                Toast.makeText(this, "Please enter both email and password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val storedUser = tinyDB.getObject("User", UsersModel::class.java)?: UsersModel()


            if (storedUser.email == inputEmail && storedUser.password == inputPassword) {
                Toast.makeText(this, "Welcome back!", Toast.LENGTH_SHORT).show()
                goToMain()
            } else {
                Toast.makeText(this, "Incorrect email or password", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun goToMain() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }
}
