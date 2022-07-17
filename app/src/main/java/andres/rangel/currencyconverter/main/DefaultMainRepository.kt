package andres.rangel.currencyconverter.main

import andres.rangel.currencyconverter.data.CurrencyApi
import andres.rangel.currencyconverter.data.models.Result
import andres.rangel.currencyconverter.utils.Resource
import java.lang.Exception
import javax.inject.Inject

class DefaultMainRepository @Inject constructor(
    private val api: CurrencyApi
) : MainRepository {
    override suspend fun getConvert(from: String, to: String, amount: String): Resource<Result> {
        return try {
            val response = api.getConvert(from, to, amount)
            val result = response.body()
            if (response.isSuccessful && result != null) {
                Resource.Success(result)
            } else {
                Resource.Error(response.message())
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An error occurred")
        }
    }
}