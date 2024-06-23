package com.ladecentro.di

import android.app.Application
import android.content.Context
import android.location.Geocoder
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.ladecentro.common.Constants
import com.ladecentro.common.NullOnEmptyConverterFactory
import com.ladecentro.data.remote.api.AuthAPI
import com.ladecentro.data.remote.api.CartAPI
import com.ladecentro.data.remote.api.LookupAPI
import com.ladecentro.data.remote.api.OrderAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Locale
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(NullOnEmptyConverterFactory())
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .build()
    }

    @Provides
    @Singleton
    fun provideAuthAPI(retrofit: Retrofit): AuthAPI {
        return retrofit.create(AuthAPI::class.java)
    }

    @Provides
    @Singleton
    fun providesOrderAPI(retrofit: Retrofit): OrderAPI {
        return retrofit.create(OrderAPI::class.java)
    }

    @Provides
    @Singleton
    fun providesLookupAPI(retrofit: Retrofit): LookupAPI {
        return retrofit.create(LookupAPI::class.java)
    }

    @Provides
    @Singleton
    fun providesCartAPI(retrofit: Retrofit): CartAPI {
        return retrofit.create(CartAPI::class.java)
    }

    @Provides
    @Singleton
    fun providesFusedLocationProviderClient(application: Application): FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(application)

    @Provides
    @Singleton
    fun providesGeocoder(@ApplicationContext context: Context): Geocoder {
        return Geocoder(context, Locale.getDefault())
    }

    @Provides
    @Singleton
    fun providesPlacesAPI(@ApplicationContext context: Context): PlacesClient {
        Places.initialize(context, "AIzaSyAmx7yRzVdbuN9JXpsfCCGHMyV3C56CsIM")
        return Places.createClient(context)
    }

    @Provides
    @Singleton
    fun providesIoDispatcher(): CoroutineDispatcher {
        return Dispatchers.IO
    }

    @Provides
    @Singleton
    fun providesGson(): Gson {
        return Gson()
    }
}