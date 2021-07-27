package io.bloco.contact.sharedModule.datasource.local

import app.template.base.useCases.AppError
import app.template.base.useCases.AppResult
import app.template.base.useCases.AppSuccess
import app.template.base.util.network.safeApiCall
import app.template.database.DBManager
import io.bloco.contact.sharedModule.datasource.local.mapper.ContactLocalMapper
import io.bloco.contact.sharedModule.modals.ContactModals
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import timber.log.Timber
import javax.inject.Inject
import kotlin.coroutines.resume

interface ContactDBManager {

    suspend fun saveContactList(
        page: Long,
        dbContactEntity: List<ContactModals>
    ): AppResult<List<Long>>

    suspend fun getContactList(
        count: Int = 10,
        offset: Int
    ): AppResult<List<ContactModals>>

    suspend fun getContact(
        id: Long
    ): AppResult<ContactModals>

    suspend fun updateContact(
        contactModals: ContactModals
    ): AppResult<Unit>
}

class ContactListDBManagerImpl @Inject constructor(
    private val dbManager: DBManager,
    private val coroutineScope: CoroutineScope,
    private val contactLocalMapper: ContactLocalMapper
) : ContactDBManager {

    override suspend fun saveContactList(
        page: Long,
        dbContactEntity: List<ContactModals>
    ): AppResult<List<Long>> {
        return dbManager.saveContactList(contactLocalMapper.mapFrom(page, dbContactEntity))
    }

    override suspend fun getContactList(count: Int, offset: Int): AppResult<List<ContactModals>> =
        safeApiCall(call = { getContactListContinuation(count, offset) })

    private suspend fun getContactListContinuation(
        count: Int,
        offset: Int
    ): AppResult<List<ContactModals>> {
        return suspendCancellableCoroutine { continuation ->
            coroutineScope.launch {
                dbManager.getContactList(count, offset).catch {
                    if (continuation.isActive)
                        continuation.resume(AppError(message = "DB Error Occurred"))
                    else
                        Timber.e("continuation isCompleted: ${continuation.isCompleted},isCancelled: ${continuation.isCancelled}")
                }.collect {
                    if (it.isEmpty()) {
                        if (continuation.isActive)
                            continuation.resume(AppError(message = "No Item Found"))
                        else
                            Timber.e("continuation isCompleted: ${continuation.isCompleted},isCancelled: ${continuation.isCancelled}")

                    } else {
                        if (continuation.isActive)
                            continuation.resume(AppSuccess(contactLocalMapper.mapTo(it)))
                        else
                            Timber.e("continuation isCompleted: ${continuation.isCompleted},isCancelled: ${continuation.isCancelled}")
                    }
                }
            }
        }
    }

    override suspend fun getContact(id: Long): AppResult<ContactModals> {
        return AppSuccess(contactLocalMapper.mapTo(dbManager.getContact(id)))
    }

    override suspend fun updateContact(contactModals: ContactModals): AppResult<Unit> {
        return dbManager.updateContact(contactModals.isStarred, contactModals.id)
    }
}