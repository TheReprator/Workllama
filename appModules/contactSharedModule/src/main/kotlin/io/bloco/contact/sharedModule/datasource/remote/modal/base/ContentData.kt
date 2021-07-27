package io.bloco.contact.sharedModule.datasource.remote.modal.base

import com.fasterxml.jackson.annotation.JsonProperty

data class ContentData<out T>(
    @JsonProperty("content")
    val content: T?,
    @JsonProperty("meta")
    val meta: MetaEntity
)