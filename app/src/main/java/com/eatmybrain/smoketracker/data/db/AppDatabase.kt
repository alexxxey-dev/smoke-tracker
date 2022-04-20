package com.eatmybrain.smoketracker.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.eatmybrain.smoketracker.data.db.converters.AmountTypeConverter
import com.eatmybrain.smoketracker.data.db.converters.StrainTypeConverter
import com.eatmybrain.smoketracker.data.structs.Session


@Database(entities = [Session::class], version = 4)
@TypeConverters(AmountTypeConverter::class, StrainTypeConverter::class)
abstract class AppDatabase : RoomDatabase(){
    abstract fun sessionsDao() : SessionsDao

}
