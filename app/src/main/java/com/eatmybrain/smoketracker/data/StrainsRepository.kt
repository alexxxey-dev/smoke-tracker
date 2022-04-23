package com.eatmybrain.smoketracker.data

import com.eatmybrain.smoketracker.data.api.StrainsApi
import com.eatmybrain.smoketracker.data.structs.StrainInfo

class StrainsRepository(
    private val strainsApi: StrainsApi
) {


    suspend fun strainInfo(strainName: String): StrainInfo {
        return searchStrainInfo(strainName) ?: StrainInfo(
            imageUrl = "",
            title = strainName
        )
    }


    suspend fun searchStrainInfo(strainName: String): StrainInfo? {
        return null
    }

}