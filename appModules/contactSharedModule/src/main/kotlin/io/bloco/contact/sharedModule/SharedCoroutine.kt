package io.bloco.contact.sharedModule

import dagger.hilt.android.scopes.ActivityRetainedScoped
import io.bloco.contact.sharedModule.modals.ContactModals
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import timber.log.Timber
import javax.inject.Inject

@ActivityRetainedScoped
class SharedCoroutine @Inject constructor() {

    private val _tickFlow = MutableSharedFlow<Pair<Int, ContactModals>>(
        replay = 1
    )
    val tickFlow: SharedFlow<Pair<Int, ContactModals>> = _tickFlow

    fun updateFlow(contactModal: ContactModals, index: Int = contactModal.id.toInt() - 1) {
        val res = _tickFlow.tryEmit(Pair(index, contactModal))
        Timber.e("fetch result is: $res")
    }
}