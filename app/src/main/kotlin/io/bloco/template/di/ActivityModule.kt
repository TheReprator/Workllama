package io.bloco.template.di

import android.content.Context
import app.template.base.util.permission.PermissionHelper
import app.template.navigation.AppNavigator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.scopes.ActivityScoped
import io.bloco.template.implementation.AppNavigatorImpl
import io.bloco.template.implementation.PermissionHelperImpl

@InstallIn(ActivityComponent::class)
@Module
class ActivityModule {

    @ActivityScoped
    @Provides
    fun providePermissionHelper(@ActivityContext context: Context): PermissionHelper {
        return PermissionHelperImpl(context)
    }

    @ActivityScoped
    @Provides
    fun provideWillyNavigator(): AppNavigator {
        return AppNavigatorImpl()
    }
}