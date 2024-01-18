import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.example.travelreservation.databinding.FragmentDropdownBinding
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.textfield.TextInputEditText
import java.text.SimpleDateFormat
import java.util.*

class Dropdown : Fragment() {
    private lateinit var binding: FragmentDropdownBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDropdownBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        createSeats()
    }
    private fun createSeats() {
        val gridLayout = binding.gridLayoutSeats

        for (row in 0 until gridLayout.rowCount) {
            for (column in 0 until gridLayout.columnCount) {
                // Koltuk numarasını hesapla
                val seatNumber = row * gridLayout.columnCount + column + 1

                // Her koltuk için bir CardView oluştur
                val cardView = createSeatCard(seatNumber)

                // Oluşturulan CardView'ı GridLayout'a ekle
                val params = GridLayout.LayoutParams()
                params.rowSpec = GridLayout.spec(row)
                params.columnSpec = GridLayout.spec(column)
                cardView.layoutParams = params
                gridLayout.addView(cardView)
            }
        }
    }

    private fun createSeatCard(seatNumber: Int): CardView {
        val cardView = CardView(requireContext())
        cardView.radius = 8f
        cardView.cardElevation = 4f
        cardView.useCompatPadding = true
        cardView.setContentPadding(16, 16, 16, 16)

        val textView = TextView(requireContext())
        textView.text = seatNumber.toString()
        textView.textSize = 18f
        textView.gravity = Gravity.CENTER

        cardView.addView(textView)

        // Koltuk seçildiğinde tetiklenecek olayları belirle
        cardView.setOnClickListener {
            handleSeatSelection(seatNumber)
        }

        return cardView
    }

    private fun handleSeatSelection(seatNumber: Int) {
        val selectedSeatText = "Seçili Koltuk: $seatNumber"
        binding.textSelectedSeat.text = selectedSeatText

        // Burada seçilen koltuk bilgisini istediğiniz şekilde kullanabilirsiniz.
        // Örneğin, Firebase Firestore'a kaydedebilirsiniz.

        // Örnek bir kayıt işlemi:
        // val userId = FirebaseAuth.getInstance().currentUser?.uid
        // val firestore = FirebaseFirestore.getInstance()
        // firestore.collection("Users").document(userId!!).collection("SelectedSeats")
        //    .add(mapOf("seatNumber" to seatNumber, "cityFrom" to selectedCityFrom, "cityTo" to selectedCityTo))
    }
}


