package io.bloco.template.implementation.connectivity

import android.content.Context
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.coroutineScope
import app.template.base.util.interent.ConnectionDetector
import dagger.hilt.android.qualifiers.ApplicationContext
import io.bloco.template.implementation.connectivity.base.ConnectivityProvider
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InternetChecker @Inject constructor(
    @ApplicationContext private val context: Context,
    lifecycle: Lifecycle, override var isInternetAvailable: Boolean = false
) : LifecycleObserver, ConnectionDetector, ConnectivityProvider.ConnectivityStateListener {

    private val provider: ConnectivityProvider by lazy { ConnectivityProvider.createProvider(context) }

    init {
        lifecycle.coroutineScope.launchWhenCreated {
            lifecycle.addObserver(this@InternetChecker)
        }
    }

    override fun onStateChange(state: ConnectivityProvider.NetworkState) {
        isInternetAvailable = state.hasInternet()
    }

    private fun ConnectivityProvider.NetworkState.hasInternet(): Boolean {
        return (this as? ConnectivityProvider.NetworkState.ConnectedState)?.hasInternet == true
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onForegroundStartMonitoringConnectivityOnCreate() {
        provider.addListener(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onForegroundStartMonitoringConnectivity() {
        provider.addListener(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onBackgroundStartMonitoringConnectivity() {
        provider.removeListener(this)
    }

}
