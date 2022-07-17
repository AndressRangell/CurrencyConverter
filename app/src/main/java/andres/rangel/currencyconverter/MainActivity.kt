package andres.rangel.currencyconverter

import andres.rangel.currencyconverter.databinding.ActivityMainBinding
import andres.rangel.currencyconverter.main.MainViewModel
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            btnConvert.setOnClickListener {
                viewModel.convert(
                    etAmount.text.toString(),
                    spFromCurrency.selectedItem.toString(),
                    spToCurrency.selectedItem.toString()
                )
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.conversion.collect { event ->
                when (event) {
                    is MainViewModel.CurrencyEvent.Success -> {
                        binding.apply {
                            progressBar.isVisible = false
                            tvResult.setTextColor(Color.BLACK)
                            tvResult.text = event.resultText
                        }
                    }
                    is MainViewModel.CurrencyEvent.Failure -> {
                        binding.apply {
                            progressBar.isVisible = false
                            tvResult.setTextColor(Color.RED)
                            tvResult.text = event.errorText
                        }
                    }
                    is MainViewModel.CurrencyEvent.Loading -> {
                        binding.progressBar.isVisible = true
                    }
                    else -> Unit
                }
            }
        }
    }
}