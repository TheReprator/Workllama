package io.bloco.contact.sharedModule.domain.repository

import app.template.base.useCases.AppResult
import io.bloco.contact.sharedModule.modals.ContactModals
import kotlinx.coroutines.flow.Flow

interface ContactIsFavouriteRepository {
    suspend fun updateContact(contactModals: ContactModals):
            Flow<AppResult<ContactModals>>
}