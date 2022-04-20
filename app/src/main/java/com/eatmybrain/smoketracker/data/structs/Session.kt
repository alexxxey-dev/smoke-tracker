package com.eatmybrain.smoketracker.data.structs

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.eatmybrain.smoketracker.ui.screens.add_session.enums.AmountType

@Entity(tableName = "session_list")
data class Session(
    @ColumnInfo(name = "mood_before")
    val moodBefore:Float = 0.0f,
    @ColumnInfo(name = "mood_after")
    val moodAfter:Float = 100.0f,
    @PrimaryKey
    val timestamp:Long,
    @ColumnInfo(name = "high_strength")
    val highStrength:Int = 0,
    @ColumnInfo(name = "amount")
    val amount:Double = 0.0,
    @ColumnInfo(name = "amount_type")
    val amountType: AmountType = AmountType.Gram,

    @ColumnInfo(name = "strain_info")
    val strainInfo:StrainInfo
)