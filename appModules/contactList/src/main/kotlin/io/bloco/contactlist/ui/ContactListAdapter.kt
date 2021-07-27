package io.bloco.contactlist.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import app.template.base_android.util.GeneralDiffUtil
import dagger.hilt.android.scopes.FragmentScoped
import io.bloco.contact.sharedModule.modals.ContactModals
import io.bloco.contactlist.databinding.RowContactBinding
import io.bloco.contactlist.databinding.RowFooterBinding
import javax.inject.Inject

@FragmentScoped
class ContactListAdapter @Inject constructor(private val itemClickListener: ItemClickListener) :
    ListAdapter<ContactModals, RecyclerView.ViewHolder>(GeneralDiffUtil<ContactModals>()) {

    companion object {
        const val LOADER = 0
        const val NORMAL = 1
    }

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        return if (-1L == item.id)
            LOADER
        else
            NORMAL
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (LOADER == viewType)
            return VHLoader(
                RowFooterBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent, false
                )
            )

        val binding = RowContactBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return VHFact(binding, itemClickListener)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        when (holder) {
            is VHFact -> {
                holder.binding.contactModal = item
                holder.binding.executePendingBindings()
            }
            is VHLoader -> {
                holder.binding.executePendingBindings()
            }
        }
    }


inner class VHFact(val binding: RowContactBinding, itemClickListener: ItemClickListener) :
    RecyclerView.ViewHolder(binding.root) {
        init {
            binding.contactIsFavourite.setOnClickListener {
                if(-1 < absoluteAdapterPosition)
                itemClickListener.markFavouriteUnFavourite(absoluteAdapterPosition)
            }

            binding.root.setOnClickListener {
                if(-1 < absoluteAdapterPosition)
                itemClickListener.itemClicked(getItem(absoluteAdapterPosition).id)
            }
        }
    }

class VHLoader(val binding: RowFooterBinding) :
    RecyclerView.ViewHolder(binding.root)

}