package io.bloco.contact.sharedModule.data.repositoryImpl

import app.template.base.useCases.AppError
import app.template.base.useCases.AppResult
import app.template.base.useCases.AppSuccess
import app.template.base.util.interent.ConnectionDetector
import io.bloco.contact.sharedModule.data.datasource.ContactIsFavouriteRemoteDataSource
import io.bloco.contact.sharedModule.datasource.local.ContactDBManager
import io.bloco.contact.sharedModule.domain.repository.ContactIsFavouriteRepository
import io.bloco.contact.sharedModule.modals.ContactModals
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class ContactIsFavouriteRepositoryImpl @Inject constructor(
    private val connectionDetector: ConnectionDetector,
    private val contactIsFavouriteDataSource: ContactIsFavouriteRemoteDataSource,
    private val contactListDBManager: ContactDBManager
) : ContactIsFavouriteRepository {

    companion object {
        private const val NO_INTERNET = "No internet connection detected."
    }

    override suspend fun updateContact(contactModals: ContactModals): Flow<AppResult<ContactModals>> {
        return if (connectionDetector.isInternetAvailable) {
            flowOf(contactIsFavouriteDataSource.updateContact(contactModals).also {
                if (it is AppSuccess)
                    contactListDBManager.updateContact(it.data)
            })
        } else
            flowOf(AppError(message = NO_INTERNET))
    }
}
