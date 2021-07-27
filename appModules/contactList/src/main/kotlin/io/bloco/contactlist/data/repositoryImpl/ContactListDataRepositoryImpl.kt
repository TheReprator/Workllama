package io.bloco.contactlist.data.repositoryImpl

import app.template.base.useCases.AppError
import app.template.base.useCases.AppResult
import app.template.base.useCases.AppSuccess
import app.template.base.util.interent.ConnectionDetector
import io.bloco.contact.sharedModule.datasource.local.ContactDBManager
import io.bloco.contact.sharedModule.modals.ContactModals
import io.bloco.contactlist.data.datasource.ContactListRemoteDataSource
import io.bloco.contactlist.domain.repository.ContactListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import timber.log.Timber
import javax.inject.Inject

class ContactListDataRepositoryImpl @Inject constructor(
    private val connectionDetector: ConnectionDetector,
    private val contactListRemoteDataSource: ContactListRemoteDataSource,
    private val contactListDBManager: ContactDBManager
) : ContactListRepository {

    companion object {
        private const val NO_INTERNET = "No internet connection detected."
    }

    override suspend fun getContactListRepository(
        pageNumber: Int,
        offset: Int
    ): Flow<AppResult<List<ContactModals>>> {
        return if (connectionDetector.isInternetAvailable) {
            when (val result =
                contactListRemoteDataSource.getContactListRemoteDataSource(pageNumber)) {
                is AppSuccess -> {
                    contactListDBManager.saveContactList(pageNumber.toLong(), result.data)
                    getContactListFromLocalDB(offset)
                }
                is AppError -> {
                    if (0 == offset)
                        getContactListFromLocalDB(offset)
                    else {
                        flowOf(result)
                    }
                }
            }
        } else {
            getContactListFromLocalDB(offset)
        }
    }

    private suspend fun getContactListFromLocalDB(offset: Int): Flow<AppResult<List<ContactModals>>> {
        return when (val data = contactListDBManager.getContactList(offset = offset)) {
            is AppSuccess -> {
                flowOf(AppSuccess(data.data))
            }
            is AppError -> {
                flowOf(AppError(message = NO_INTERNET))
            }
        }
    }
}