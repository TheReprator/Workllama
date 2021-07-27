package io.bloco.contact.sharedModule.data.datasource

import app.template.base.useCases.AppResult
import io.bloco.contact.sharedModule.modals.ContactModals

interface ContactIsFavouriteRemoteDataSource {
    suspend fun updateContact(contactModals: ContactModals):
            AppResult<ContactModals>
}
