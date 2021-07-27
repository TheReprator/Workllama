package io.bloco.contact.sharedModule

import dagger.hilt.android.scopes.ActivityRetainedScoped
import io.bloco.contact.sharedModule.modals.ContactModals
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import timber.log.Timber
import javax.inject.Inject

@ActivityRetainedScoped
class SharedCoroutine @Inject constructor() {

    private val _tickFlow = MutableSharedFlow<ContactModals>(
        replay = 1
    )
    val tickFlow: SharedFlow<ContactModals> = _tickFlow

    fun updateFlow(contactModal: ContactModals) {
        val res = _tickFlow.tryEmit(contactModal)
        Timber.e("fetch result is: $res")
    }
}