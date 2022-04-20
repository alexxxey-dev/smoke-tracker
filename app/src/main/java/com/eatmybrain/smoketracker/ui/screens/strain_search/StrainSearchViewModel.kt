package com.eatmybrain.smoketracker.ui.screens.strain_search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.eatmybrain.smoketracker.data.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StrainSearchViewModel @Inject constructor(
    private val repository: Repository
) :ViewModel(){





    fun updateStrainList(query:String){

    }
}