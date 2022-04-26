package com.eatmybrain.smoketracker.data

import android.content.Context
import com.eatmybrain.smoketracker.R
import com.eatmybrain.smoketracker.data.structs.Achievement
import com.eatmybrain.smoketracker.data.structs.AchievementType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit

class AchievementsProvider(
    private val context: Context
) {


    suspend fun provide(moneySaved:Double, gramsAvoided:Double, weedFreeTime:Long) = withContext(Dispatchers.IO){
        moneyAchievements(moneySaved) + gramsAchievements(gramsAvoided) + daysAchievements(weedFreeTime)
    }

    private fun moneyAchievements(moneySaved:Double): List<Achievement> {
        val description = context.getString(R.string.dollars_saved_achievement)
        val type = AchievementType.MONEY
        return listOf(
            Achievement(
                number = 25,
                description = description,
                type = type,
                achieved = moneySaved >= 25,
                index = 0
            ),
            Achievement(
                number = 50,
                description = description,
                type = type,
                achieved = moneySaved >= 50,
                index = 1
            ),
            Achievement(
                number = 100,
                description = description,
                type = type,
                achieved = moneySaved >= 100,
                index = 2
            ),
            Achievement(
                number = 250,
                description = description,
                type = type,
                achieved = moneySaved >= 250,
                index = 3
            ),
            Achievement(
                number = 500,
                description = description,
                type = type,
                achieved = moneySaved >= 500,
                index = 4
            ),
            Achievement(
                number = 1000,
                description = description,
                type = type,
                achieved = moneySaved >= 1000,
                index = 5
            )
        )
    }

    private fun gramsAchievements(gramsAvoided:Double): List<Achievement> {
        val description = context.getString(R.string.grams_not_smoked_achievement)
        val type = AchievementType.GRAMS
        return listOf(
            Achievement(
                number = 5,
                description = description,
                type = type,
                achieved = gramsAvoided >= 5,
                index = 0
            ),
            Achievement(
                number = 10,
                description = description,
                type = type,
                achieved = gramsAvoided >= 10,
                index = 1
            ),
            Achievement(
                number = 15,
                description = description,
                type = type,
                achieved = gramsAvoided >= 15,
                index = 2
            ),
            Achievement(
                number = 25,
                description = description,
                type = type,
                achieved = gramsAvoided >= 25,
                index = 3
            ),
            Achievement(
                number = 35,
                description = description,
                type = type,
                achieved = gramsAvoided >= 35,
                index = 4
            ),
            Achievement(
                number = 50,
                description = description,
                type = type,
                achieved = gramsAvoided >= 50,
                index = 5
            )
        )
    }

    private fun daysAchievements(weedFreeTime:Long): List<Achievement> {
        val description = context.getString(R.string.days_without_weed_achievement)
        val weedFreeDays = TimeUnit.MILLISECONDS.toDays(weedFreeTime)
        val type = AchievementType.DAYS
        return listOf(
            Achievement(
                number = 7,
                description = description,
                type = type,
                achieved = weedFreeDays >= 7,
                index = 0
            ),
            Achievement(
                number = 14,
                description = description,
                type = type,
                achieved =  weedFreeDays >= 14,
                index = 1
            ),
            Achievement(
                number = 21,
                description = description,
                type = type,
                achieved =  weedFreeDays >= 21,
                index = 2
            ),
            Achievement(
                number = 30,
                description = description,
                type = type,
                achieved =  weedFreeDays >= 30,
                index = 3
            ),
            Achievement(
                number = 60,
                description = description,
                type = type,
                achieved =  weedFreeDays >= 60,
                index = 4
            ),
            Achievement(
                number = 90,
                description = description,
                type = type,
                achieved =  weedFreeDays >= 90,
                index = 5
            ),
        )
    }
}