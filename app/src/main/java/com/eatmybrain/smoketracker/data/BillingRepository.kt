package com.eatmybrain.smoketracker.data

import com.eatmybrain.smoketracker.util.PremiumUtil


//TODO
class BillingRepository(
    private val premiumUtil: PremiumUtil
)  {

     fun hasPremium() = premiumUtil.available()

    suspend fun price():Int{
        return 3
    }

    suspend  fun successPurchase(sku:String):Boolean{
        premiumUtil.activate()
        return true
    }

    suspend  fun errorPurchase(sku:String):Boolean{
        return false
    }
}