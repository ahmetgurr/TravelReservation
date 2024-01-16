import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.travelreservation.databinding.FragmentDropdownBinding
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.textfield.TextInputEditText
import java.text.SimpleDateFormat
import java.util.*

class Dropdown : Fragment() {

    private lateinit var binding: FragmentDropdownBinding
    private lateinit var editTextBirthDay: TextInputEditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDropdownBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editTextBirthDay = binding.editTextBirthDay
        setupDatePicker()

        // Diğer işlemleri burada gerçekleştirebilirsiniz.
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
