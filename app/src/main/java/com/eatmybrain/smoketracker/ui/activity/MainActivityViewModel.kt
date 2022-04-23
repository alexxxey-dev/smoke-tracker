package com.eatmybrain.smoketracker.ui.activity

import androidx.lifecycle.ViewModel
import com.eatmybrain.smoketracker.data.BreakRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val repository: BreakRepository
) : ViewModel(){

    val isBreakActive = repository.isBreakActive()
}