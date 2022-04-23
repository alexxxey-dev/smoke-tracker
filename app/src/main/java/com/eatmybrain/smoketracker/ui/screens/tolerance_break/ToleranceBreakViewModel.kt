package com.eatmybrain.smoketracker.ui.screens.tolerance_break

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eatmybrain.smoketracker.data.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class ToleranceBreakViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {


    fun toggleToleranceBreak() = viewModelScope.launch {
        withContext(Dispatchers.IO) { repository.toggleToleranceBreak() }
    }
}