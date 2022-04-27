package com.eatmybrain.smoketracker.di

import android.content.Context
import androidx.room.Room
import com.eatmybrain.smoketracker.data.*
import com.eatmybrain.smoketracker.data.api.StrainsApi
import com.eatmybrain.smoketracker.data.data_store.MyDataStore
import com.eatmybrain.smoketracker.data.db.AppDatabase
import com.eatmybrain.smoketracker.util.Constants
import com.eatmybrain.smoketracker.util.Premium
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
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(Constants.STRAINS_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideDataStoreUtil(@ApplicationContext context: Context): MyDataStore =
        MyDataStore(context)

    @Provides
    @Singleton
    fun providePremiumRepository(premium: Premium) = BillingRepository(premium)

    @Provides
    @Singleton
    fun providePremiumUtil(dataStore: MyDataStore) = Premium(dataStore)

    @Provides
    @Singleton
    fun provideStrainsApi(retrofit: Retrofit) = retrofit.create(StrainsApi::class.java)

    @Provides
    @Singleton
    fun provideAchievements(@ApplicationContext context:Context) = AchievementsProvider(context)

    @Provides
    @Singleton
    fun provideDb(@ApplicationContext context: Context): AppDatabase = Room
        .databaseBuilder(context, AppDatabase::class.java, Constants.DATABASE_NAME)
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    @Singleton
    fun provideSessionsRepository(db: AppDatabase) = SessionsRepository(db)


    @Provides
    @Singleton
    fun provideBreakRepository(dataStore: MyDataStore, achievementsProvider: AchievementsProvider) = BreakRepository(dataStore,achievementsProvider)


    @Provides
    @Singleton
    fun provideStrainsRepository(api: StrainsApi) = StrainsRepository(api)
}