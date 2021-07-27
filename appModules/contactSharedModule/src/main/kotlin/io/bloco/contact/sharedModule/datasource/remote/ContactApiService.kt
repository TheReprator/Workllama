package io.bloco.contact.sharedModule.datasource.remote

import io.bloco.contact.sharedModule.datasource.remote.modal.base.ContentData
import io.bloco.contact.sharedModule.datasource.remote.modal.contact.ContactEntity
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ContactApiService {
    @GET("v1/contacts")
    suspend fun contactList(@Query("pageNumber") pageNumber: Int): Response<ContentData<List<ContactEntity>>>

    @POST("v1/star/{id}")
    suspend fun markFavourite(@Path("id") userId: String): Response<ContentData<ContactEntity>>

    @POST("v1/unstar/{id}")
    suspend fun markUnFavourite(@Path("id") userId: String): Response<ContentData<ContactEntity>>
}