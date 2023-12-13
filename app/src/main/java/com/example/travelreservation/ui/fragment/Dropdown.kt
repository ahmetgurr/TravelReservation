import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.example.travelreservation.databinding.FragmentDropdownBinding


class Dropdown : Fragment() {

    private val cities = arrayOf("İstanbul", "Ankara", "İzmir", "Bursa", "Antalya", "Adana", "Eskişehir", "Trabzon")
    private var _binding: FragmentDropdownBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDropdownBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, cities)
        binding.autoCompleteTextView.setAdapter(adapter)

        binding.autoCompleteTextView.setOnItemClickListener { parent, _, position, _ ->
            val selectedCity = parent.getItemAtPosition(position).toString()
            // Seçilen şehirle ilgili işlemleri burada yapabilirsiniz.
        }

        // AutoCompleteTextView'ye tıklanıldığında klavye açılmasını sağlamak için
        binding.autoCompleteTextView.setOnClickListener {
            binding.autoCompleteTextView.showDropDown()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
