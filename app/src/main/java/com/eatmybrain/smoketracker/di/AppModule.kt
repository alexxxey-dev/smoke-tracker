package com.eatmybrain.smoketracker.di

import android.content.Context
import androidx.room.Room
import com.eatmybrain.smoketracker.data.Repository
import com.eatmybrain.smoketracker.data.api.StrainsApi
import com.eatmybrain.smoketracker.data.db.AppDatabase
import com.eatmybrain.smoketracker.util.ChartDataParser
import com.eatmybrain.smoketracker.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit():Retrofit = Retrofit.Builder()
        .baseUrl(Constants.STRAINS_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideStrainsApi(retrofit: Retrofit) = retrofit.create(StrainsApi::class.java)

    @Provides
    @Singleton
    fun provideChartDataParser() = ChartDataParser()
    @Provides
    @Singleton
    fun provideDb(@ApplicationContext context: Context):AppDatabase = Room
        .databaseBuilder(context, AppDatabase::class.java, Constants.DATABASE_NAME)
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    @Singleton
    fun provideRepository(db: AppDatabase, strainsApi: StrainsApi) = Repository(db,strainsApi)
}