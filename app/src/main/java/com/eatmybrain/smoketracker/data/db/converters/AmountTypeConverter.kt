package com.eatmybrain.smoketracker.data.db.converters

import androidx.room.TypeConverter
import com.eatmybrain.smoketracker.ui.screens.add_session.enums.AmountType
import org.json.JSONObject
import java.lang.IllegalStateException

class AmountTypeConverter {

    @TypeConverter
    fun fromAmountType(amountType: AmountType) : String{
        val type = when(amountType){
            AmountType.Gram -> "gram"
            AmountType.Joint -> "joint"
            AmountType.Bowl -> "bowl"
        }
        return JSONObject().apply {
            put("type", type)
        }.toString()
    }

    @TypeConverter
    fun toAmountType(amountType:String) : AmountType {
        val json = JSONObject(amountType)
        return when(json.getString("type")){
            "gram" -> AmountType.Gram
            "bowl" -> AmountType.Bowl
            "joint" -> AmountType.Joint
            else -> throw IllegalStateException("unknown amount type")
        }
    }
}