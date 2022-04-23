package com.eatmybrain.smoketracker.ui.screens.strain_search

import androidx.lifecycle.ViewModel
import com.eatmybrain.smoketracker.data.SessionsRepository
import com.eatmybrain.smoketracker.data.StrainsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StrainSearchViewModel @Inject constructor(
    private val repository: StrainsRepository
) :ViewModel(){





    fun updateStrainList(query:String){

    }
}