package com.eatmybrain.smoketracker.ui.screens.tolerance_break

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eatmybrain.smoketracker.data.BreakRepository
import com.eatmybrain.smoketracker.data.SessionsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class BreakViewModel @Inject constructor(
    private val breakRepository: BreakRepository
) : ViewModel() {


    fun toggleBreak() = viewModelScope.launch {
        withContext(Dispatchers.IO) { breakRepository.toggleBreak() }
    }
}