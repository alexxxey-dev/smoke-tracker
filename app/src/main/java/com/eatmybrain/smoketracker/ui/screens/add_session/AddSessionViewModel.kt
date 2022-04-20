package com.eatmybrain.smoketracker.ui.screens.add_session

import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eatmybrain.smoketracker.R
import com.eatmybrain.smoketracker.data.Repository
import com.eatmybrain.smoketracker.data.structs.Session
import com.eatmybrain.smoketracker.data.structs.StrainInfo
import com.eatmybrain.smoketracker.ui.screens.add_session.enums.AmountType
import com.eatmybrain.smoketracker.ui.screens.add_session.enums.amountType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AddSessionViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private fun addSession(
        strainName:String,
        pricePerGram:Double,
        moodBefore:Float,
        moodAfter:Float,
        highStrength:Int,
        amount:Double,
        amountType: AmountType
    ) = viewModelScope.launch {
        val strainInfo = repository.strainInfo(strainName) ?: StrainInfo(imageUrl = "", title = strainName, gramPrice = pricePerGram)
        val session = Session(
            moodBefore = moodBefore,
            moodAfter = moodAfter,
            timestamp = System.currentTimeMillis(),
            highStrength = highStrength,
            amount = amount,
            amountType = amountType,
            strainInfo = strainInfo
        )
        repository.addSession(session)
    }


    fun onSaveClicked(
        strainName:String,
        pricePerGram:String,
        moodBefore:Float,
        moodAfter:Float,
        highStrength:Int,
        amount:String,
        amountType: String
    ) : Int?{
        if(strainName.isBlank()) {
            return R.string.strain_name_empty
        }

        if(pricePerGram.isBlank()) {
            return R.string.price_per_gram_empty
        }

        if(!pricePerGram.isDigitsOnly()){
            return R.string.price_per_gram_digits
        }

        if(highStrength == 0) {
            return R.string.high_empty
        }

        if(amount.isBlank()) {
            return R.string.amount_empty
        }

        if(!amount.isDigitsOnly()) {
            return R.string.amount_digits
        }
        addSession(
            strainName = strainName,
            pricePerGram = pricePerGram.toDouble(),
            moodBefore = moodBefore,
            moodAfter = moodAfter,
            highStrength = highStrength,
            amount = amount.toDouble(),
            amountType = amountType.amountType()
        )

        return null
    }

}