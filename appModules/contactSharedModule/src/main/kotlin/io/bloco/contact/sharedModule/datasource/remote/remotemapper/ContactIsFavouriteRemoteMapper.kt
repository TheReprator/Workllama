package io.bloco.contact.sharedModule.datasource.remote.remotemapper

import app.template.base.util.Mapper
import io.bloco.contact.sharedModule.datasource.remote.modal.base.ContentData
import io.bloco.contact.sharedModule.datasource.remote.modal.contact.ContactEntity
import io.bloco.contact.sharedModule.modals.ContactModals
import javax.inject.Inject

class ContactIsFavouriteRemoteMapper @Inject constructor() :
    Mapper<ContentData<ContactEntity>, Triple<Boolean, String, ContactModals?>> {

    override suspend fun map(from: ContentData<ContactEntity>): Triple<Boolean, String, ContactModals?> {
        return if (from.meta.success) {
            val user = from.content!!
            val contactList = ContactModals(
                user.id ?: 0L, user.name ?: "", user.phone ?: "",
                user.thumbnail ?: "", user.email ?: "", user.isStarred ?: false
            )

            Triple(from.meta.success, from.meta.message ?: "", contactList)
        } else
            Triple(from.meta.success, from.meta.message ?: "", null)
    }

}