package com.example.mobile_midterm

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.mobile_midterm.Domain.UsersModel
import com.example.mobile_midterm.Helper.TinyDB
import com.example.mobile_midterm.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    private lateinit var tinyDB: TinyDB
    private lateinit var user: UsersModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        tinyDB = TinyDB(this)
        user = tinyDB.getObject("User", UsersModel::class.java) ?: UsersModel()
        loadProfile()

        binding.backButtonProfile.setOnClickListener() {finish()}

        // Click listeners for editing
        binding.editFullName.setOnClickListener {
            showEditDialog("Full Name", user.fullName) {
                user.fullName = it
                updateProfile()
            }
        }

        binding.editPhone.setOnClickListener {
            showEditDialog("Phone Number", user.phoneNumber) {
                user.phoneNumber = it
                updateProfile()
            }
        }

        binding.editEmail.setOnClickListener {
            showEditDialog("Email", user.email) {
                user.email = it
                updateProfile()
            }
        }

        binding.editAddress.setOnClickListener {
            showEditDialog("Address", user.address) {
                user.address = it
                updateProfile()
            }
        }
    }

    private fun loadProfile() {
        binding.fullNameText.text = user.fullName
        binding.phoneText.text = user.phoneNumber
        binding.emailText.text = user.email
        binding.addressText.text = user.address
    }

    private fun updateProfile() {
        tinyDB.putObject("User", user)
        loadProfile()
        Toast.makeText(this, "Profile updated", Toast.LENGTH_SHORT).show()
    }

    @SuppressLint("ResourceType")
    private fun showEditDialog(title: String, currentValue: String, onSave: (String) -> Unit) {
        val dialogView = layoutInflater.inflate(R.drawable.dialog_edit_profile, null)
        val editField = dialogView.findViewById<EditText>(R.id.editField)
        editField.setText(currentValue)

        val dialog = AlertDialog.Builder(this)
            .setTitle("Edit $title")
            .setView(dialogView)
            .setPositiveButton("Save", null)
            .setNegativeButton("Cancel", null)
            .create()

        dialog.setOnShowListener {
            val saveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
            saveButton.setOnClickListener {
                val newValue = editField.text.toString()
                if (newValue.isNotBlank()) {
                    onSave(newValue)
                    dialog.dismiss()
                } else {
                    editField.error = "$title cannot be empty"
                }
            }
        }

        dialog.show()
    }

}
