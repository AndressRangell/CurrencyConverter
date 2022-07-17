package andres.rangel.currencyconverter.data

import andres.rangel.currencyconverter.data.models.Result
import andres.rangel.currencyconverter.utils.Constants.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface CurrencyApi {

    @GET("exchangerates_data/convert?")
    suspend fun getConvert(
        @Query("from") from: String,
        @Query("to") to: String,
        @Query("amount") amount: String,
        @Header("apikey") token: String = API_KEY
    ): Response<Result>

}