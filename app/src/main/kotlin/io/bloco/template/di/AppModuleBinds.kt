package io.bloco.template.di

import app.template.base_android.appinitializers.AppInitializer
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import io.bloco.template.appinitializers.TimberInitializer
import io.bloco.template.appinitializers.CoilAppInitializer

@InstallIn(SingletonComponent::class)
@Module
abstract class AppModuleBinds {

    @Binds
    @IntoSet
    abstract fun provideTimberInitializer(bind: TimberInitializer): AppInitializer

    @Binds
    @IntoSet
    abstract fun provideCoilInitializer(bind: CoilAppInitializer): AppInitializer
}
