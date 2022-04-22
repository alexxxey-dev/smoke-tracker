package com.eatmybrain.smoketracker.ui.screens.tolerance

import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eatmybrain.smoketracker.R
import com.eatmybrain.smoketracker.data.Repository
import com.eatmybrain.smoketracker.data.structs.SessionsInfo
import com.eatmybrain.smoketracker.ui.screens.tolerance.enums.DialogError
import com.eatmybrain.smoketracker.util.double
import com.eatmybrain.smoketracker.util.removeCommas
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.NumberFormatException
import javax.inject.Inject

@HiltViewModel
class ToleranceViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    fun checkFreqError(
        smokeFreq:String
    ) :Boolean{
        return try {
             smokeFreq.removeCommas().isBlank() || smokeFreq.toInt() == 0 || !smokeFreq.removeCommas().isDigitsOnly()
        }catch (ex:NumberFormatException){
            true
        }
    }

    fun checkAmountError(
        smokeAmount:String
    ) :Boolean{
        return try {
            smokeAmount.removeCommas().isBlank() || smokeAmount.toDouble() == 0.0 || !smokeAmount.removeCommas().isDigitsOnly()
        }catch (ex:NumberFormatException){
            true
        }
    }

    fun checkPriceError(
        price:String
    ) :Boolean{
        return try{
            price.removeCommas().isBlank() || price.toDouble() == 0.0 || !price.removeCommas().isDigitsOnly()
        } catch (ex:NumberFormatException){
            true
        }
    }


    fun saveSmokeData(
        sessionsInfo: SessionsInfo
    ) = viewModelScope.launch {
        val smokeFreq = sessionsInfo.freq.toInt()
        val smokeAmount = sessionsInfo.amount.double()
        val price = sessionsInfo.price.double()
        withContext(Dispatchers.IO) {
            repository.saveSmokeData(
                smokeFreq,
                smokeAmount,
                price
            )
        }
    }

    fun startToleranceBreak(){
        //TODO
    }
}