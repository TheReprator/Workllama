package io.bloco.contact.sharedModule.modals

data class ContactModals constructor(
    val id: Long, val name: String, val phone: String,
    val thumbnail: String, val email: String, val isStarred: Boolean, val isUpdate: Boolean = false
) {
    constructor(
        id: Long, name: String = "", phone: String = "",
        thumbnail: String = "", email: String = "", isStarred: Boolean = false
    ) :
            this(id, name, phone, thumbnail, email, isStarred, false)
}