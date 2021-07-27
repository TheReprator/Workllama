package io.bloco.contactlist.data.datasource

import app.template.base.useCases.AppResult
import io.bloco.contact.sharedModule.modals.ContactModals

interface ContactListRemoteDataSource {
    suspend fun getContactListRemoteDataSource(pageNumber: Int): AppResult<List<ContactModals>>
}
