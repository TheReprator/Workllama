package app.template.base_android.appinitializers

import android.app.Application

interface AppInitializer {
    fun init(application: Application)
}
