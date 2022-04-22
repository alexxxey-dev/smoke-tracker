package com.eatmybrain.smoketracker.ui.screens.tolerance

import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eatmybrain.smoketracker.R
import com.eatmybrain.smoketracker.data.Repository
import com.eatmybrain.smoketracker.util.removeCommas
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ToleranceViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    fun saveSmokeInfo(
        smokeFreq: String,
        smokeAmount: String,
        price: String
    ): Int? {
        if (smokeFreq.removeCommas().isBlank()) {
            return R.string.smoke_freq_empty
        }

        if (smokeFreq.toInt() == 0) {
            return R.string.smoke_freq_zero
        }

        if (smokeAmount.removeCommas().isBlank()) {
            return R.string.smoke_amount_empty
        }

        if (smokeAmount.toDouble() == 0.0) {
            return R.string.smoke_amount_zero
        }

        if (price.removeCommas().isBlank()) {
            return R.string.smoke_freq_empty
        }

        if (price.toDouble() == 0.0) {
            return R.string.smoke_freq_empty
        }

        if (!smokeFreq.removeCommas().isDigitsOnly()) {
            return R.string.smoke_freq_incorrect
        }

        if (!smokeAmount.removeCommas().isDigitsOnly()) {
            return R.string.smoke_amount_incorrect
        }

        if (!price.removeCommas().isDigitsOnly()) {
            return R.string.price_incorrect
        }

        saveSmokeData(smokeFreq.toInt(), smokeAmount.toDouble(), price.toDouble())

        return null
    }

    private fun saveSmokeData(
        smokeFreq: Int,
        smokeAmount: Double,
        price: Double
    ) = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            repository.saveSmokeData(
                smokeFreq,
                smokeAmount,
                price
            )
        }
    }
}