package io.bloco.contact.sharedModule.datasource.remote.modal.base

import com.fasterxml.jackson.annotation.JsonProperty

class MetaEntity(
    @JsonProperty("message")
    val message: String?,
    @JsonProperty("pageNumber")
    val pageNumber: Int?,
    @JsonProperty("pageSize")
    val pageSize: Int?,
    @JsonProperty("success")
    val success: Boolean
)