package io.bloco.template.appinitializers

import android.app.Application
import app.template.base_android.appinitializers.AppInitializer
import io.bloco.template.implementation.AppDebugTree
import io.bloco.template.implementation.CrashlyticsTree
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Named

class TimberInitializer @Inject constructor( @Named("isDebug") val isDebug: Boolean) :
    AppInitializer {

    override fun init(application: Application) {
        if(isDebug)
            Timber.plant(AppDebugTree())
        else
            Timber.plant(CrashlyticsTree())
    }
}
