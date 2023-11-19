package com.example.travelreservation.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.travelreservation.R
import com.example.travelreservation.ui.login.LoginActivity
import com.example.travelreservation.databinding.FragmentSettingBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class SettingFragment : Fragment() {
    private lateinit var binding: FragmentSettingBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var db : FirebaseFirestore

    private lateinit var txtUsername: TextView
    private lateinit var txtEmail: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        binding = FragmentSettingBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)

        auth = Firebase.auth
        db = FirebaseFirestore.getInstance()
        setHasOptionsMenu(true) // Bu, Fragment'ın kendi menüsünü kullanacağını belirtir
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.signOutButton.setOnClickListener {
            auth.signOut()
            val intent = Intent(context, LoginActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }

       // auth = Firebase.auth
        //db = FirebaseFirestore.getInstance()

        // Kullanıcının UID'sini kontrol etme
        val user = auth.currentUser
        if (user != null) {
            val userId = user.uid
            Log.d("SettingFragment", "Current User ID: $userId")

            // Firestore'dan kullanıcı adını çekme
            loadUsernameFromFirestore(userId)
        } else {
            // Kullanıcı oturum açmamışsa, başka bir işlem yapabilirsiniz.
            Log.d("SettingFragment", "User is not signed in.")
        }
    }

    private fun loadUsernameFromFirestore(userId: String) {
        db.collection("Users")
            .document(userId)
            .get()
            .addOnSuccessListener { document ->
                if (document != null && document.exists()) {
                    // Firestore'dan kullanıcı adını al ve UI elemanına ata
                    val username = document.getString("username")
                    val email = document.getString("email")

                    binding.txtUsername.text =  "Username: $username"
                    binding.txtUsernamePhoto.text = "$username"
                    binding.txtEmail.text =     "Email:         $email"
                } else {
                    Log.d("SettingFragment", "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.w("SettingFragment", "get failed with ", exception)
            }
    }

/*
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.nav_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.settingFragment -> {
                auth.signOut()
                val intent = Intent(context, MainActivity::class.java)
                startActivity(intent)
                // Menü öğesi 1 seçildiğinde yapılacak işlemler
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

 */


}