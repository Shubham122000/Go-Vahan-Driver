package com.gvpartner.com.module

import com.gvpartner.com.network.MainRepository
import com.gvpartner.com.network.MainRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface RepositoriesModule {
    @Binds
    fun mainRepository(mainRepositoryImpl : MainRepositoryImpl) : MainRepository

}