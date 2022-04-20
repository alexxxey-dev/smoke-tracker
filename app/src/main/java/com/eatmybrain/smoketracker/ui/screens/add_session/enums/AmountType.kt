package com.eatmybrain.smoketracker.ui.screens.add_session.enums

import java.lang.IllegalStateException

sealed class AmountType(val weight:Double) {
    object Gram : AmountType(1.0)
    object Bowl : AmountType(0.1)
    object Joint : AmountType(0.4)

    companion object {
        fun values() = listOf(Gram.string(),Bowl.string(),Joint.string())
    }
}
fun AmountType.string():String{
    return when(this){
        AmountType.Gram -> "Gram"
        AmountType.Bowl -> "Bowl"
        AmountType.Joint -> "Joint"
    }
}
fun String.amountType():AmountType{
    return when(this) {
        "Gram" -> AmountType.Gram
        "Bowl" -> AmountType.Bowl
        "Joint" -> AmountType.Joint
        else -> throw IllegalStateException("unknown amount type")
    }
}