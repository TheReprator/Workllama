package io.bloco.template

import androidx.multidex.MultiDexApplication
import dagger.hilt.android.HiltAndroidApp
import io.bloco.template.appinitializers.AppInitializers
import javax.inject.Inject

@HiltAndroidApp
class App : MultiDexApplication() {

    @Inject
    lateinit var initializers: AppInitializers

    override fun onCreate() {
        super.onCreate()
        initializers.init(this)
    }
}