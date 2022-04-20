package com.eatmybrain.smoketracker.data

import com.eatmybrain.smoketracker.data.structs.Session
import com.eatmybrain.smoketracker.data.structs.StrainInfo
import com.eatmybrain.smoketracker.ui.screens.add_session.enums.AmountType
import com.eatmybrain.smoketracker.ui.screens.statistics.enums.SessionsPeriod
import java.util.concurrent.TimeUnit

object FakeRepository {
    fun sessionHistory(sessionPeriod: SessionsPeriod) : List<Session> {
        val currentTime = System.currentTimeMillis()
        val sessionList = listOf(
            //Magic

            //Week period
            Session(
                timestamp = currentTime - TimeUnit.HOURS.toMillis(1),
                amount = 1.0,
                amountType = AmountType.Gram,
                strainInfo = StrainInfo("https://atlas-content-cdn.pixelsquid.com/stock-images/marijuana-bud-mda4Xn1-600.jpg", "Big Bud", 5.0)
            ),
            Session(
                timestamp =currentTime - TimeUnit.DAYS.toMillis(1),
                amount = 5.0,
                amountType = AmountType.Gram,
                strainInfo = StrainInfo("https://atlas-content-cdn.pixelsquid.com/stock-images/marijuana-bud-mda4Xn1-600.jpg", "Big Bud", 5.0)
            ),
            Session(
                timestamp = currentTime - TimeUnit.DAYS.toMillis(3),
                amount = 2.0,
                amountType = AmountType.Gram,
                strainInfo = StrainInfo("https://atlas-content-cdn.pixelsquid.com/stock-images/marijuana-bud-mda4Xn1-600.jpg", "Big Bud", 5.0)
            ),
            //Month period
            Session(
                timestamp = currentTime - TimeUnit.DAYS.toMillis(14),
                amount = 3.0,
                amountType = AmountType.Gram,
                strainInfo = StrainInfo("https://atlas-content-cdn.pixelsquid.com/stock-images/marijuana-bud-mda4Xn1-600.jpg", "Big Bud", 5.0)
            ),
            Session(
                timestamp = currentTime - TimeUnit.DAYS.toMillis(20),
                amount = 1.4,
                amountType = AmountType.Gram,
                strainInfo = StrainInfo("https://atlas-content-cdn.pixelsquid.com/stock-images/marijuana-bud-mda4Xn1-600.jpg", "Big Bud", 5.0)
            ),
            Session(
                timestamp = currentTime - TimeUnit.DAYS.toMillis(25),
                amount = 2.6,
                amountType = AmountType.Gram,
                strainInfo = StrainInfo("https://atlas-content-cdn.pixelsquid.com/stock-images/marijuana-bud-mda4Xn1-600.jpg", "Big Bud", 5.0)
            ),
            //Year period
            Session(
                timestamp = currentTime - TimeUnit.DAYS.toMillis(60),
                amount = 7.8,
                amountType = AmountType.Gram,
                strainInfo = StrainInfo("https://atlas-content-cdn.pixelsquid.com/stock-images/marijuana-bud-mda4Xn1-600.jpg", "Big Bud", 5.0)
            ),
            Session(
                timestamp = currentTime - TimeUnit.DAYS.toMillis(120),
                amount = 0.1,
                amountType = AmountType.Gram,
                strainInfo = StrainInfo("https://atlas-content-cdn.pixelsquid.com/stock-images/marijuana-bud-mda4Xn1-600.jpg", "Big Bud", 5.0)
            ),
            Session(
                timestamp = currentTime - TimeUnit.DAYS.toMillis(180),
                amount = 0.3,
                amountType = AmountType.Gram,
                strainInfo = StrainInfo("https://atlas-content-cdn.pixelsquid.com/stock-images/marijuana-bud-mda4Xn1-600.jpg", "Big Bud", 5.0)
            ),
            Session(
                timestamp = currentTime - TimeUnit.DAYS.toMillis(190),
                amount = 10.0,
                amountType = AmountType.Gram,
                strainInfo = StrainInfo("https://atlas-content-cdn.pixelsquid.com/stock-images/marijuana-bud-mda4Xn1-600.jpg", "Big Bud", 5.0)
            ),
        )
        val start = sessionPeriod.startTimestamp()
        return sessionList.filter { it.timestamp >= start }
    }
}