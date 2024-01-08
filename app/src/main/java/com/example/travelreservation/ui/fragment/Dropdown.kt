import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.GridLayout
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.example.travelreservation.R

class Dropdown : Fragment() {

    private lateinit var busLayout: GridLayout
    private lateinit var genderButton: Button
    private var isMaleSelected = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_dropdown, container, false)

        // XML dosyasında tanımladığınız GridLayout ve Button öğelerini alın
        busLayout = rootView.findViewById(R.id.busLayout)
        genderButton = rootView.findViewById(R.id.genderButton)

        // Otobüs koltuklarını oluşturun
        createBusSeats()

        // Cinsiyet seçimi için butona tıklama olayını ekle
        genderButton.setOnClickListener {
            toggleGenderSelection()
        }

        return rootView
    }

    private fun createBusSeats() {
        val seatCount = 20 // Örnek olarak 20 koltuk olsun

        for (i in 1..seatCount) {
            val seatImageView = ImageView(requireContext())
            seatImageView.setImageResource(R.drawable.baseline_question_mark_24)
            seatImageView.layoutParams = GridLayout.LayoutParams().apply {
                width = 100 // Koltuk genişliği
                height = 100 // Koltuk yüksekliği
                //margin = 10 // Koltuklar arası boşluk
            }
            seatImageView.setOnClickListener {
                onSeatClick(it)
            }
            busLayout.addView(seatImageView)
        }
    }

    private fun onSeatClick(view: View) {
        // Koltuğa tıklandığında cinsiyete göre renk değiştirme
        if (isMaleSelected) {
            view.setBackgroundColor(Color.BLUE)
        } else {
            view.setBackgroundColor(Color.MAGENTA)
        }
    }

    private fun toggleGenderSelection() {
        // Cinsiyet seçimini değiştir
        isMaleSelected = !isMaleSelected

        // Buton metnini güncelle
        if (isMaleSelected) {
            genderButton.text = "Erkek Seçildi"
        } else {
            genderButton.text = "Kadın Seçildi"
        }
    }
}