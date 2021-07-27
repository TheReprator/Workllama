package io.bloco.contact.sharedModule.datasource.remote

import app.template.base.useCases.AppError
import app.template.base.useCases.AppResult
import app.template.base.useCases.AppSuccess
import app.template.base.util.network.safeApiCall
import app.template.base.util.network.toResult
import io.bloco.contact.sharedModule.data.datasource.ContactIsFavouriteRemoteDataSource
import io.bloco.contact.sharedModule.datasource.remote.remotemapper.ContactIsFavouriteRemoteMapper
import io.bloco.contact.sharedModule.modals.ContactModals
import javax.inject.Inject

class ContactIsFavouriteRemoteDataSourceImpl @Inject constructor(
    private val contactApiService: ContactApiService,
    private val contactIsFavouriteRemoteMapper: ContactIsFavouriteRemoteMapper
) : ContactIsFavouriteRemoteDataSource {

    private suspend fun updateContactRemoteDataSourceApi(contactModals: ContactModals):
            AppResult<ContactModals> {

        val apiData = if (contactModals.isStarred) {
            contactApiService.markUnFavourite(contactModals.id.toString())
        } else
            contactApiService.markFavourite(contactModals.id.toString())

        return when (val data = apiData.toResult()) {
            is AppSuccess -> {
                val evaluatedData = contactIsFavouriteRemoteMapper.map(data.data)

                if (evaluatedData.first)
                    AppSuccess(evaluatedData.third!!)
                else
                    AppError(message = evaluatedData.second)
            }
            is AppError -> {
                AppError(message = data.message, throwable = data.throwable)
            }
            else -> throw IllegalArgumentException("Illegal State")
        }
    }

    override suspend fun updateContact(contactModals: ContactModals): AppResult<ContactModals> =
        safeApiCall(call = { updateContactRemoteDataSourceApi(contactModals) })
}
