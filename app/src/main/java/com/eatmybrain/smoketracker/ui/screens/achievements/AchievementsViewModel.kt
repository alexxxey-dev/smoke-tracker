package com.eatmybrain.smoketracker.ui.screens.achievements

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eatmybrain.smoketracker.data.BreakRepository
import com.eatmybrain.smoketracker.data.structs.Achievement
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class AchievementsViewModel @Inject constructor(
    private val breakRepository: BreakRepository
) : ViewModel() {
    private val _achievementsList = MutableLiveData<List<Achievement>>()
    val achievementsList:LiveData<List<Achievement>> = _achievementsList


    private val _completedCount = MutableLiveData<Int>()
    val completedCount:LiveData<Int> = _completedCount

    init {
        loadAchievements()
    }

    private fun loadAchievements() = viewModelScope.launch {
        val list = withContext(Dispatchers.IO) { breakRepository.achievements() }
        val count = list.count { it.achieved }
        _achievementsList.value = list
        _completedCount.value = count
    }


}