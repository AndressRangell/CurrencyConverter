package andres.rangel.currencyconverter.data.models

data class Result(
    val query: Query,
    val result: Double,
    val success: Boolean
)