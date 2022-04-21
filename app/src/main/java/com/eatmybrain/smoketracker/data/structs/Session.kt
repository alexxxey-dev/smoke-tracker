package com.eatmybrain.smoketracker.data.structs

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.eatmybrain.smoketracker.ui.screens.add_session.enums.AmountType

@Entity(tableName = "session_list")
data class Session(
    @ColumnInfo(name = "mood_before")
    var moodBefore:Float = 0.0f,
    @ColumnInfo(name = "mood_after")
    var moodAfter:Float = 100.0f,
    @PrimaryKey
    var timestamp:Long,
    @ColumnInfo(name = "high_strength")
    var highStrength:Int = 0,
    @ColumnInfo(name = "amount")
    var amount:Double = 0.0,
    @ColumnInfo(name = "amount_type")
    var amountType: AmountType = AmountType.Gram,
    @ColumnInfo
    var pricePerGram:Double = 0.0,
    @ColumnInfo(name = "strain_info")
    var strainInfo:StrainInfo
)