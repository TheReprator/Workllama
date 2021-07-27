package io.bloco.contactDetail.data.repositoryImpl

import app.template.base.useCases.AppResult
import io.bloco.contact.sharedModule.datasource.local.ContactDBManager
import io.bloco.contact.sharedModule.modals.ContactModals
import io.bloco.contactDetail.domain.repository.ContactDetailRepository
import javax.inject.Inject

class ContactDetailDataRepositoryImpl @Inject constructor(
    private val contactListDBManager: ContactDBManager
) : ContactDetailRepository {

    override suspend fun getContact(userId: Long): AppResult<ContactModals> {
        return contactListDBManager.getContact(userId)
    }
}
