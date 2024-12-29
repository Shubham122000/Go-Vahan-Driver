package com.govahanpartner.com.module

import com.govahanpartner.com.network.MainRepository
import com.govahanpartner.com.network.MainRepositoryImpl
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