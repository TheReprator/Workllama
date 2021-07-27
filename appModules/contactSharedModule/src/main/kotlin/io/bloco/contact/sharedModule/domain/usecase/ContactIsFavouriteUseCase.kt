package io.bloco.contact.sharedModule.domain.usecase

import app.template.base.useCases.AppResult
import io.bloco.contact.sharedModule.domain.repository.ContactIsFavouriteRepository
import io.bloco.contact.sharedModule.modals.ContactModals
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ContactIsFavouriteUseCase @Inject constructor(
    private val contactIsFavouriteRepository: ContactIsFavouriteRepository
) {
    suspend operator fun invoke(
        contactModals: ContactModals
    ): Flow<AppResult<ContactModals>> {
        return contactIsFavouriteRepository.updateContact(contactModals)
    }
}
