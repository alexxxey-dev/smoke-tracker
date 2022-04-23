package com.eatmybrain.smoketracker.ui.activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.eatmybrain.smoketracker.data.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel(){

    val isToleranceBreakActive = repository.isToleranceBreakActive()
}