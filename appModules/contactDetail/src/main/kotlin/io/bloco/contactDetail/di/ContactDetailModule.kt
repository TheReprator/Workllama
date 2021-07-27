package io.bloco.contactDetail.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import io.bloco.contact.sharedModule.datasource.local.ContactDBManager
import io.bloco.contactDetail.data.repositoryImpl.ContactDetailDataRepositoryImpl
import io.bloco.contactDetail.domain.repository.ContactDetailRepository
import io.bloco.contactDetail.domain.usecase.ContactDetailUseCase

@InstallIn(ViewModelComponent::class)
@Module
class ContactDetailModule {


    @Provides
    fun provideContactDetailRepository(
        contactListDBManager: ContactDBManager
    ): ContactDetailRepository {
        return ContactDetailDataRepositoryImpl(
            contactListDBManager
        )
    }

    @Provides
    fun provideContactDetailUseCase(
        contactDetailRepository: ContactDetailRepository
    ): ContactDetailUseCase {
        return ContactDetailUseCase(contactDetailRepository)
    }
}
