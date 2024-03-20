package com.example.samplenewsapp.di.module

import android.content.Context
import androidx.room.Room
import androidx.work.WorkManager
import com.example.samplenewsapp.data.api.ApiKeyInterceptor
import com.example.samplenewsapp.data.api.NetworkService
import com.example.samplenewsapp.data.local.database.DatabaseService
import com.example.samplenewsapp.data.local.database.NewsDatabase
import com.example.samplenewsapp.data.local.database.NewsDatabaseService
import com.example.samplenewsapp.di.BaseUrl
import com.example.samplenewsapp.di.DatabaseName
import com.example.samplenewsapp.di.NetworkApiKey
import com.example.samplenewsapp.utils.Constants.API_KEY
import com.example.samplenewsapp.utils.Constants.BASE_URL
import com.example.samplenewsapp.utils.DefaultDispatcherProvider
import com.example.samplenewsapp.utils.DefaultResourceProvider
import com.example.samplenewsapp.utils.DispatcherProvider
import com.example.samplenewsapp.utils.ResourceProvider
import com.example.samplenewsapp.utils.logger.AppLogger
import com.example.samplenewsapp.utils.logger.Logger
import com.example.samplenewsapp.utils.network.DefaultNetworkHelper
import com.example.samplenewsapp.utils.network.NetworkHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {
    @BaseUrl
    @Provides
    fun provideBaseUrl(): String = BASE_URL

    @NetworkApiKey
    @Provides
    fun provideApiKey(): String = API_KEY

    @Provides
    @Singleton
    fun provideGsonConverterFactory(): GsonConverterFactory = GsonConverterFactory.create()

    @Provides
    @Singleton
    fun provideApiKeyInterceptor(@NetworkApiKey apiKey: String): ApiKeyInterceptor =
        ApiKeyInterceptor(apiKey)

    @Provides
    @Singleton
    fun provideOkHttpClient(apiKeyInterceptor: ApiKeyInterceptor): OkHttpClient =
        OkHttpClient().newBuilder().addInterceptor(apiKeyInterceptor).build()

    @Provides
    @Singleton
    fun provideNetworkService(
        @BaseUrl baseUrl: String,
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): NetworkService {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()
            .create(NetworkService::class.java)

    }

    @Provides
    @Singleton
    fun provideLogger(): Logger = AppLogger()

    @Provides
    @Singleton
    fun provideDispatcherProvider(): DispatcherProvider = DefaultDispatcherProvider()

    @Provides
    @Singleton
    fun provideNetworkHelper(@ApplicationContext context: Context): NetworkHelper {
        return DefaultNetworkHelper(context)
    }

    @Provides
    @Singleton
    fun provideStringHelper(@ApplicationContext context: Context): ResourceProvider {
        return DefaultResourceProvider(context)
    }

    @DatabaseName
    @Provides
    fun provideDatabaseName(): String = "news-database"

    @Provides
    @Singleton
    fun provideNewsDatabase(
        @ApplicationContext context: Context,
        @DatabaseName databaseName: String
    ): NewsDatabase {
        return Room.databaseBuilder(
            context,
            NewsDatabase::class.java,
            databaseName
        ).build()
    }

    @Provides
    @Singleton
    fun provideDatabaseService(newsDatabase: NewsDatabase): DatabaseService {
        return NewsDatabaseService(newsDatabase)
    }

    @Provides
    @Singleton
    fun provideWorkManager(@ApplicationContext context: Context): WorkManager {
        return WorkManager.getInstance(context)
    }

}