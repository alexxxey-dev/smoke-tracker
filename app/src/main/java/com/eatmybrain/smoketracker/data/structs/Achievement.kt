package com.eatmybrain.smoketracker.data.structs



data class Achievement(
    val number:Int,
    val description:String,
    val achieved:Boolean = false,
    val type:AchievementType,
    val index:Int
)

enum class AchievementType {
    MONEY, GRAMS, DAYS
}