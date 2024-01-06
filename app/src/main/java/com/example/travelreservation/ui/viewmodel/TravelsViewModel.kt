import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travelreservation.model.Travel
import kotlinx.coroutines.launch

class TravelsViewModel(private val repository: TravelRepository) : ViewModel() {
/*
    private val _travelList = MutableLiveData<List<Travel>?>()
    val travelList: MutableLiveData<List<Travel>?> get() = _travelList

    fun fetchTravelData() {
        viewModelScope.launch {
            val data = repository.getTravelData()
            _travelList.value = data
        }
    }
 */
}
