package com.eatmybrain.smoketracker.data

import com.eatmybrain.smoketracker.util.Premium


//TODO
class BillingRepository(
    private val premium: Premium
)  {

     fun hasPremium() = premium.available()

    suspend fun price():Int{
        return 3
    }

    suspend  fun purchase(sku:String):Boolean{
        return true
    }
}