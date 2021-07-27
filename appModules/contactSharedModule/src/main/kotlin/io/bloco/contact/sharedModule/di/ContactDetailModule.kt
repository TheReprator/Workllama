package io.bloco.contact.sharedModule.di

import app.template.base.util.interent.ConnectionDetector
import app.template.database.DBManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import io.bloco.contact.sharedModule.data.datasource.ContactIsFavouriteRemoteDataSource
import io.bloco.contact.sharedModule.data.repositoryImpl.ContactIsFavouriteRepositoryImpl
import io.bloco.contact.sharedModule.datasource.local.ContactDBManager
import io.bloco.contact.sharedModule.datasource.local.ContactListDBManagerImpl
import io.bloco.contact.sharedModule.datasource.local.mapper.ContactLocalMapper
import io.bloco.contact.sharedModule.datasource.remote.ContactApiService
import io.bloco.contact.sharedModule.datasource.remote.ContactIsFavouriteRemoteDataSourceImpl
import io.bloco.contact.sharedModule.datasource.remote.remotemapper.ContactIsFavouriteRemoteMapper
import io.bloco.contact.sharedModule.domain.repository.ContactIsFavouriteRepository
import io.bloco.contact.sharedModule.domain.usecase.ContactIsFavouriteUseCase
import kotlinx.coroutines.CoroutineScope
import retrofit2.Retrofit

@InstallIn(SingletonComponent::class)
@Module
class ContactDetailModule {

    @Provides
    fun provideContactApiService(
        retrofit: Retrofit
    ): ContactApiService {
        return retrofit
            .create(ContactApiService::class.java)
    }

    @Provides
    fun provideContactDBManager(
        dbManager: DBManager,
        contactLocalMapper: ContactLocalMapper,
        coroutineScope: CoroutineScope
    ): ContactDBManager {
        return ContactListDBManagerImpl(
            dbManager,
            coroutineScope,
            contactLocalMapper
        )
    }

    @Provides
    fun provideContactIsFavouriteRemoteDataSource(
        contactApiService: ContactApiService,
        contactMapper: ContactIsFavouriteRemoteMapper
    ): ContactIsFavouriteRemoteDataSource {
        return ContactIsFavouriteRemoteDataSourceImpl(
            contactApiService,
            contactMapper
        )
    }

    @Provides
    fun provideContactIsFavouriteRepository(
        contactIsFavouriteDataSource: ContactIsFavouriteRemoteDataSource,
        connectionDetector: ConnectionDetector,
        contactListDBManager: ContactDBManager
    ): ContactIsFavouriteRepository {
        return ContactIsFavouriteRepositoryImpl(
            connectionDetector,
            contactIsFavouriteDataSource,
            contactListDBManager
        )
    }

    @Provides
    fun provideContactIsFavouriteUseCase(
        contactIsFavouriteRepository: ContactIsFavouriteRepository
    ): ContactIsFavouriteUseCase {
        return ContactIsFavouriteUseCase(contactIsFavouriteRepository)
    }
}
