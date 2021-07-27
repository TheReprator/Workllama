package io.bloco.contactDetail.domain.repository

import app.template.base.useCases.AppResult
import io.bloco.contact.sharedModule.modals.ContactModals

interface ContactDetailRepository {
    suspend fun getContact(userId: Long):
            AppResult<ContactModals>
}
