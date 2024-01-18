package com.example.travelreservation.ui.fragment

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.travelreservation.R
import com.example.travelreservation.databinding.FragmentAddPassengerBinding
import com.example.travelreservation.databinding.FragmentDropdownBinding
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class AddPassengerFragment : Fragment() {
    private lateinit var binding: FragmentAddPassengerBinding
    private lateinit var editTextBirthDay: TextInputEditText
    private lateinit var auth: FirebaseAuth
    private lateinit var db : FirebaseFirestore


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddPassengerBinding.inflate(inflater, container, false)
        auth = FirebaseAuth.getInstance() // Firebase Auth ve Firestore objesini oluştur
        db = FirebaseFirestore.getInstance()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editTextBirthDay = binding.editTextBirthDay
        setupDatePicker()

        binding.btnAddPassenger.setOnClickListener {
            addPassenger()
        }
    }

    private fun addPassenger() {
        val surname = binding.editTextSurname.text.toString()
        val name = binding.editTextName.text.toString()
        val birthDay = binding.editTextBirthDay.text.toString()
        val phone = binding.editTextPhone.text.toString()

        // Cinsiyetin belirlenmesi
        val gender = if (binding.radioGroupGender.checkedRadioButtonId == R.id.radioMale) {
            "Male"
        } else {
            "Female"
        }

        // Firestore "Users" --> "Passengers" koleksiyonu
        val userId = auth.currentUser?.uid
        val passengersCollection = db.collection("Users").document(userId!!)
            .collection("Passengers")

        // Yeni bir yolcu objesi oluştur
        val passenger = hashMapOf(
            "Surname" to surname,
            "Name" to name,
            "BirthDay" to birthDay,
            "Phone" to phone,
            "Gender" to gender
        )

        // Firestore'a yolcu eklemesi
        passengersCollection.add(passenger)
            .addOnSuccessListener { documentReference ->
                // Belge başarıyla eklendi, oluşturulan kimliği al
                val passengerId = documentReference.id

                // Oluşturulan kimliği "id" alanına yaz
                passengersCollection.document(passengerId).update("id", passengerId)
                    .addOnSuccessListener {
                        Navigation.findNavController(requireView()).navigate(R.id.action_addPassengerFragment_to_passengerInfoFragment)
                        Toast.makeText(requireContext(), "Passenger added successfully!", Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener {
                        Toast.makeText(requireContext(), "Could not update passenger id!", Toast.LENGTH_SHORT).show()
                    }
            }
            .addOnFailureListener {
                Toast.makeText(requireContext(), "Could not add passenger!", Toast.LENGTH_SHORT).show()
            }
    }

    private fun setupDatePicker() {
        editTextBirthDay.setOnClickListener {
            showDatePicker()
        }
    }

    private fun showDatePicker() {
        val builder = MaterialDatePicker.Builder.datePicker()
        val picker = builder.build()
        picker.addOnPositiveButtonClickListener { selection ->
            val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
            val formattedDate = dateFormat.format(Date(selection))
            editTextBirthDay.setText(formattedDate)
        }
        picker.show(parentFragmentManager, picker.toString())
    }
}