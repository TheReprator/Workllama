package io.bloco.contactlist.ui

interface ItemClickListener {
    fun itemClicked(userId: Long)
    fun markFavouriteUnFavourite(position: Int)
}