package com.eatmybrain.smoketracker.data.db.converters

import androidx.room.TypeConverter
import com.eatmybrain.smoketracker.data.structs.StrainInfo
import org.json.JSONObject

class StrainTypeConverter {

    @TypeConverter
    fun fromValue(strainInfo: StrainInfo): String {
        return JSONObject().apply {
            put("image_url", strainInfo.imageUrl)
            put("gram_price", strainInfo.gramPrice)
            put("title", strainInfo.title)
        }.toString()
    }

    @TypeConverter
    fun toValue(json: String): StrainInfo {
        val jsonObject = JSONObject(json)
        return StrainInfo(
            imageUrl = jsonObject.getString("image_url"),
            title = jsonObject.getString("title"),
            gramPrice = jsonObject.getDouble("gram_price")
        )
    }
}