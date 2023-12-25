package com.ladecentro.di

import com.ladecentro.data.location.CurrentLocationTracker
import com.ladecentro.data.repository.AuthRepositoryImpl
import com.ladecentro.data.repository.LookupRepositoryImpl
import com.ladecentro.data.repository.OrderRepositoryImpl
import com.ladecentro.data.repository.UserRepositoryImpl
import com.ladecentro.domain.location.LocationTracker
import com.ladecentro.domain.repository.AuthRepository
import com.ladecentro.domain.repository.LookupRepository
import com.ladecentro.domain.repository.OrderRepository
import com.ladecentro.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindUserRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository

    @Binds
    abstract fun bindAuthRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepository

    @Binds
    abstract fun bindOrderRepository(authRepositoryImpl: OrderRepositoryImpl): OrderRepository

    @Binds
    abstract fun bindLookupRepository(lookupRepository: LookupRepositoryImpl): LookupRepository

    @Binds
    abstract fun bindLocationTracker(currentLocationTracker: CurrentLocationTracker): LocationTracker
}