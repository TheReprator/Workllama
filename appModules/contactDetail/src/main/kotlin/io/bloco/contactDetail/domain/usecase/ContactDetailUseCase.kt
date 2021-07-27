package io.bloco.contactDetail.domain.usecase

import app.template.base.useCases.AppResult
import io.bloco.contact.sharedModule.modals.ContactModals
import io.bloco.contactDetail.domain.repository.ContactDetailRepository
import javax.inject.Inject

class ContactDetailUseCase @Inject constructor(
    private val contactDetailRepository: ContactDetailRepository
) {
    suspend operator fun invoke(
        userId: Long
    ): AppResult<ContactModals> {
        return contactDetailRepository.getContact(userId)
    }
}
