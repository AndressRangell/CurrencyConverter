package andres.rangel.currencyconverter.utils

import java.text.DecimalFormat

fun Double.format(): String {
    val decimalFormat = DecimalFormat("#.00")
    return decimalFormat.format(this)
}