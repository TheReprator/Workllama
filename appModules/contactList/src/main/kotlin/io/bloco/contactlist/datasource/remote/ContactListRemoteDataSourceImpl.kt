package io.bloco.contactlist.datasource.remote

import app.template.base.useCases.AppError
import app.template.base.useCases.AppResult
import app.template.base.useCases.AppSuccess
import app.template.base.util.network.safeApiCall
import app.template.base.util.network.toResult
import io.bloco.contact.sharedModule.datasource.remote.ContactApiService
import io.bloco.contact.sharedModule.modals.ContactModals
import io.bloco.contactlist.data.datasource.ContactListRemoteDataSource
import io.bloco.contactlist.datasource.remote.remotemapper.ContactListRemoteMapper
import javax.inject.Inject

class ContactListRemoteDataSourceImpl @Inject constructor(
    private val contactListApiService: ContactApiService,
    private val factListMapper: ContactListRemoteMapper
) : ContactListRemoteDataSource {

    private suspend fun getContactListRemoteDataSourceApi(pageNumber: Int):
            AppResult<List<ContactModals>> {

        return when (val data = contactListApiService.contactList(pageNumber).toResult()) {
            is AppSuccess -> {
                val evaluatedData = factListMapper.map(data.data)

                if (evaluatedData.first)
                    AppSuccess(evaluatedData.third)
                else
                    AppError(message = evaluatedData.second)
            }
            is AppError -> {
                AppError(message = data.message, throwable = data.throwable)
            }
            else -> throw IllegalArgumentException("Illegal State")
        }
    }

    override suspend fun getContactListRemoteDataSource(pageNumber: Int): AppResult<List<ContactModals>> =
        safeApiCall(call = { getContactListRemoteDataSourceApi(pageNumber) })
}
