package com.arwan.arwansa_scanmecalculator.di

import android.content.Context
import com.arwan.arwansa_scanmecalculator.data.local.AppDatabase
import com.arwan.arwansa_scanmecalculator.data.local.ExpressionsDao
import com.arwan.arwansa_scanmecalculator.data.local.FileStorage
import com.arwan.arwansa_scanmecalculator.data.repository.ExpressionsRepository
import com.arwan.arwansa_scanmecalculator.data.repository.ExpressionsRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context) = AppDatabase.getDatabase(context)

    @Singleton
    @Provides
    fun provideExpressionsDao(db: AppDatabase) = db.expressionsDao()

    @Singleton
    @Provides
    fun provideFileStorage(@ApplicationContext context: Context) = FileStorage(context)

    @Singleton
    @Provides
    fun provideExpressionsRepository(expressionsDao: ExpressionsDao, fileStorage: FileStorage): ExpressionsRepository =
        ExpressionsRepositoryImpl(expressionsDao, fileStorage)
}