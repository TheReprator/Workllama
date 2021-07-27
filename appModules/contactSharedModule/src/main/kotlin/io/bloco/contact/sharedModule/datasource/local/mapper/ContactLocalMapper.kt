package io.bloco.contact.sharedModule.datasource.local.mapper

import app.template.database.DBContactEntity
import io.bloco.contact.sharedModule.modals.ContactModals
import javax.inject.Inject

class ContactLocalMapper @Inject constructor() {

    suspend fun mapTo(from: List<DBContactEntity>): List<ContactModals> {
        return from.map {
            ContactModals(it.id, it.name, it.phone, it.thumbnail, it.email, it.isStarred)
        }
    }

    suspend fun mapTo(from: DBContactEntity): ContactModals {
        return ContactModals(
            from.id,
            from.name,
            from.phone,
            from.thumbnail,
            from.email,
            from.isStarred
        )
    }

    suspend fun mapFrom(pageNumber: Long, from: List<ContactModals>): List<DBContactEntity> {
        return from.map {
            DBContactEntity(
                it.id,
                it.name,
                it.phone,
                it.thumbnail,
                it.email,
                it.isStarred,
                pageNumber
            )
        }
    }
}
