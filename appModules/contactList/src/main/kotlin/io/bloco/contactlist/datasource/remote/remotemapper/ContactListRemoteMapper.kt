package io.bloco.contactlist.datasource.remote.remotemapper

import app.template.base.util.Mapper
import io.bloco.contact.sharedModule.datasource.remote.modal.base.ContentData
import io.bloco.contact.sharedModule.datasource.remote.modal.contact.ContactEntity
import io.bloco.contact.sharedModule.modals.ContactModals
import javax.inject.Inject

class ContactListRemoteMapper @Inject constructor() :
    Mapper<ContentData<List<ContactEntity>>, Triple<Boolean, String, List<ContactModals>>> {

    override suspend fun map(from: ContentData<List<ContactEntity>>): Triple<Boolean, String, List<ContactModals>>{

        return if(from.meta.success) {
            val contactList = from.content!!.map {

                ContactModals(
                    it.id ?: 0L, it.name ?: "", it.phone ?: "",
                    it.thumbnail ?: "", it.email ?: "", it.isStarred ?: false
                )
            }

            Triple(from.meta.success, from.meta.message ?: "", contactList)
        }else
            Triple(from.meta.success, from.meta.message ?: "", emptyList())
    }

}