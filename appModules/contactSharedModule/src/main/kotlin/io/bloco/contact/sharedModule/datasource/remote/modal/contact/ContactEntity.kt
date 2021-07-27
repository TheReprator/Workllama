package io.bloco.contact.sharedModule.datasource.remote.modal.contact

import com.fasterxml.jackson.annotation.JsonProperty

class ContactEntity(
    @JsonProperty("email")
    val email: String?,
    @JsonProperty("id")
    val id: Long?,
    @JsonProperty("isStarred")
    val isStarred: Boolean?,
    @JsonProperty("name")
    val name: String?,
    @JsonProperty("phone")
    val phone: String?,
    @JsonProperty("thumbnail")
    val thumbnail: String?
)