package io.bloco.contactlist.domain.usecase

import app.template.base.useCases.AppResult
import io.bloco.contact.sharedModule.modals.ContactModals
import io.bloco.contactlist.domain.repository.ContactListRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ContactListUseCase @Inject constructor(
    private val factListRepository: ContactListRepository
) {
    suspend operator fun invoke(
        pageNumber: Int,
        offset: Int
    ): Flow<AppResult<List<ContactModals>>> {
        return factListRepository.getContactListRepository(pageNumber, offset)
    }
}
