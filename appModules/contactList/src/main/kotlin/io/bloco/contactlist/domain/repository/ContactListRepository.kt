package io.bloco.contactlist.domain.repository

import app.template.base.useCases.AppResult
import io.bloco.contact.sharedModule.modals.ContactModals
import kotlinx.coroutines.flow.Flow

interface ContactListRepository {
    suspend fun getContactListRepository(pageNumber: Int = 0, offset: Int = 0):
            Flow<AppResult<List<ContactModals>>>
}
