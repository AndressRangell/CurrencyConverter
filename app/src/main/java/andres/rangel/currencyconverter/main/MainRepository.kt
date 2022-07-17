package andres.rangel.currencyconverter.main

import andres.rangel.currencyconverter.data.models.Result
import andres.rangel.currencyconverter.utils.Resource

interface MainRepository {

    suspend fun getConvert(from: String, to: String, amount: String): Resource<Result>

}