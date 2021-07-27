package io.bloco.contactlist.di

import androidx.fragment.app.Fragment
import app.template.navigation.AppNavigator
import app.template.navigation.ContactListNavigator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import io.bloco.contactlist.ui.ContactListAdapter
import io.bloco.contactlist.ui.ItemClickListener

@InstallIn(FragmentComponent::class)
@Module
class ContactListFragmentModule {

    @Provides
    fun provideItemClickListener(
        fragment: Fragment
    ): ItemClickListener {
        return fragment as ItemClickListener
    }

    @Provides
    fun provideContactListAdapter(
        itemClickListener: ItemClickListener
    ): ContactListAdapter {
        return ContactListAdapter(itemClickListener)
    }

    @Provides
    fun provideContactListNavigator(appNavigator: AppNavigator): ContactListNavigator {
        return appNavigator
    }
}
