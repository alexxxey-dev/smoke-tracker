package com.eatmybrain.smoketracker.ui.screens.add_session

import androidx.core.text.isDigitsOnly
import androidx.lifecycle.*
import com.eatmybrain.smoketracker.R
import com.eatmybrain.smoketracker.data.Repository
import com.eatmybrain.smoketracker.data.structs.Session
import com.eatmybrain.smoketracker.ui.screens.add_session.enums.AmountType
import com.eatmybrain.smoketracker.ui.screens.add_session.enums.amountType
import com.eatmybrain.smoketracker.util.removeCommas
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class AddSessionViewModel @AssistedInject constructor(
    private val repository: Repository,
    @Assisted
    private val sessionTimestamp: Long
) : ViewModel() {
    private val _session = MutableLiveData<Session>()
    val session: LiveData<Session> = _session

    init {
        viewModelScope.launch {
            if (sessionTimestamp != 0L) {
                val result = withContext(Dispatchers.IO) { repository.session(sessionTimestamp) }
                _session.value = result
            }
        }
    }

    private fun addSession(
        strainName: String,
        pricePerGram: Double,
        moodBefore: Float,
        moodAfter: Float,
        highStrength: Int,
        amount: Double,
        amountType: AmountType
    ) = viewModelScope.launch {

        val strainInfo = repository.strainInfo(strainName)
        val session = session.value?.apply {
            this.moodAfter = moodAfter
            this.moodBefore = moodBefore
            this.highStrength = highStrength
            this.amount = amount
            this.amountType = amountType
            this.strainInfo = strainInfo
            this.pricePerGram = pricePerGram
        } ?: Session(
            moodBefore = moodBefore,
            moodAfter = moodAfter,
            timestamp = System.currentTimeMillis(),
            highStrength = highStrength,
            amount = amount,
            amountType = amountType,
            strainInfo = strainInfo,
            pricePerGram = pricePerGram
        )
        withContext(Dispatchers.IO) {
            repository.addSession(session)
        }

    }


    fun onSaveClicked(
        strainName: String,
        pricePerGram: String,
        moodBefore: Float,
        moodAfter: Float,
        highStrength: Int,
        amount: String,
        amountType: String
    ): Int? {
        if (strainName.isBlank()) {
            return R.string.strain_name_empty
        }

        if (pricePerGram.isBlank()) {
            return R.string.price_per_gram_empty
        }

        if (!pricePerGram.removeCommas().isDigitsOnly()) {
            return R.string.price_per_gram_digits
        }

        if (highStrength == 0) {
            return R.string.high_empty
        }

        if (amount.isBlank()) {
            return R.string.amount_empty
        }

        if (!amount.removeCommas().isDigitsOnly()) {
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


    @AssistedFactory
    interface Factory {
        fun create(timestamp: Long): AddSessionViewModel
    }

    @Suppress("UNCHECKED_CAST")
    companion object {
        fun provideFactory(
            assistedFactory: Factory,
            id: Long
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return assistedFactory.create(id) as T
            }
        }
    }
}