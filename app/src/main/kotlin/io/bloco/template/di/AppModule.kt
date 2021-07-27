package io.bloco.template.di

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ProcessLifecycleOwner
import app.template.base.util.date.DateUtils
import app.template.base.util.interent.ConnectionDetector
import app.template.base.util.network.AppCoroutineDispatchers
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.bloco.template.BuildConfig
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.asCoroutineDispatcher
import io.bloco.template.R
import io.bloco.template.implementation.AppCoroutineDispatchersImpl
import io.bloco.template.implementation.DateUtilsImpl
import io.bloco.template.implementation.connectivity.InternetChecker
import kotlinx.coroutines.SupervisorJob
import java.util.concurrent.Executors
import javax.inject.Named
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AppModule {

    @Provides
    fun provideLifeCycle(): Lifecycle {
        return ProcessLifecycleOwner.get().lifecycle
    }

    @Singleton
    @Provides
    fun providesCoroutineScope(appCoroutineDispatchers: AppCoroutineDispatchers): CoroutineScope {
        return CoroutineScope(SupervisorJob() + appCoroutineDispatchers.default)
    }

    @Provides
    fun provideCoroutineDispatcherProvider(): AppCoroutineDispatchers {
        return AppCoroutineDispatchersImpl(
            Dispatchers.Main, Dispatchers.IO, Dispatchers.IO, Dispatchers.Default,
            Executors.newFixedThreadPool(1).asCoroutineDispatcher()
        )
    }

    @Provides
    fun provideAppPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences(
            context.getString(R.string.app_name),
            Context.MODE_PRIVATE
        )
    }

    @Provides
    fun provideConnectivityChecker(
        @ApplicationContext context: Context, lifecycle: Lifecycle
    ): ConnectionDetector {
        return InternetChecker(context, lifecycle)
    }

    @Provides
    fun provideDateUtils(): DateUtils {
        return DateUtilsImpl()
    }

    @Named("isDebug")
    @Provides
    fun provideIsDebug(): Boolean {
        return BuildConfig.DEBUG
    }
}