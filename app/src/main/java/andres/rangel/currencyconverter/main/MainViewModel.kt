package andres.rangel.currencyconverter.main

import andres.rangel.currencyconverter.utils.Constants.SYMBOLS
import andres.rangel.currencyconverter.utils.DispatcherProvider
import andres.rangel.currencyconverter.utils.Resource
import andres.rangel.currencyconverter.utils.format
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: MainRepository,
    private val dispatchers: DispatcherProvider
) : ViewModel() {

    sealed class CurrencyEvent {
        class Success(val resultText: String) : CurrencyEvent()
        class Failure(val errorText: String) : CurrencyEvent()
        object Loading : CurrencyEvent()
        object Empty : CurrencyEvent()
    }

    private val _conversion = MutableStateFlow<CurrencyEvent>(CurrencyEvent.Empty)
    val conversion: StateFlow<CurrencyEvent> = _conversion

    fun convert(
        amountString: String,
        from: String,
        to: String
    ) {
        val amount = amountString.toFloatOrNull()
        if (amount == null) {
            _conversion.value = CurrencyEvent.Failure("Not a valid amount")
            return
        }

        viewModelScope.launch(dispatchers.io) {
            _conversion.value = CurrencyEvent.Loading
            val symbolFrom = SYMBOLS[from]
            val symbolTo = SYMBOLS[to]
            if (symbolFrom == null || symbolTo == null) {
                _conversion.value = CurrencyEvent.Failure("Unexpected error")
            } else {
                when (val response = repository.getConvert(symbolFrom, symbolTo, amountString)) {
                    is Resource.Error -> _conversion.value =
                        CurrencyEvent.Failure(response.message!!)
                    is Resource.Success -> {
                        val result = response.data!!.result
                        _conversion.value = CurrencyEvent.Success(
                            "$amountString $symbolFrom = ${result.format()} $symbolTo"
                        )
                    }
                }
            }

        }
    }

}