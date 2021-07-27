package io.bloco.contactlist.di

import app.template.base.util.interent.ConnectionDetector
import app.template.database.DBManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import io.bloco.contact.sharedModule.datasource.local.ContactDBManager
import io.bloco.contact.sharedModule.datasource.remote.ContactApiService
import io.bloco.contactlist.data.datasource.ContactListRemoteDataSource
import io.bloco.contactlist.data.repositoryImpl.ContactListDataRepositoryImpl
import io.bloco.contactlist.datasource.remote.ContactListRemoteDataSourceImpl
import io.bloco.contactlist.datasource.remote.remotemapper.ContactListRemoteMapper
import io.bloco.contactlist.domain.repository.ContactListRepository
import io.bloco.contactlist.domain.usecase.ContactListUseCase
import kotlinx.coroutines.CoroutineScope
import retrofit2.Retrofit

@InstallIn(ViewModelComponent::class)
@Module
class ContactListModule {

    @Provides
    fun provideContactListRemoteDataSource(
        contactApiService: ContactApiService,
        contactListMapper: ContactListRemoteMapper
    ): ContactListRemoteDataSource {
        return ContactListRemoteDataSourceImpl(
            contactApiService,
            contactListMapper
        )
    }

    @Provides
    fun provideContactListRepository(
        contactListRemoteDataSource: ContactListRemoteDataSource,
        connectionDetector: ConnectionDetector,
        contactDBManager: ContactDBManager
    ): ContactListRepository {
        return ContactListDataRepositoryImpl(
            connectionDetector,
            contactListRemoteDataSource,
            contactDBManager
        )
    }

    @Provides
    fun provideContactListUseCase(
        contactListRepository: ContactListRepository
    ): ContactListUseCase {
        return ContactListUseCase(contactListRepository)
    }
}
