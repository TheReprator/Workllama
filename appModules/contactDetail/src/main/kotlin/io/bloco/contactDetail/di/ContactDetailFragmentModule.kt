package io.bloco.contactDetail.di

import app.template.navigation.AppNavigator
import app.template.navigation.ContactDetailNavigator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@InstallIn(FragmentComponent::class)
@Module
class ContactDetailFragmentModule {

    @Provides
    fun provideContactDetailNavigator(appNavigator: AppNavigator): ContactDetailNavigator {
        return appNavigator
    }
}
