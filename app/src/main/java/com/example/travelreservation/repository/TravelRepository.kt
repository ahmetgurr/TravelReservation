import com.example.travelreservation.model.Travel
import com.example.travelreservation.service.TravelDao

class TravelRepository(private val travelDao: TravelDao) {
/*
    suspend fun getTravelData(): List<Travel>? {
        return try {
            val response = travelDao.getData()
            if (response.isSuccessful) {
                response.body()
            } else {
                // Hata durumu
                null
            }
        } catch (e: Exception) {
            // Network hatası veya diğer istisnalar
            null
        }
    }
 */

}
