package io.bloco.contactlist.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.template.base.util.isNull
import app.template.base_android.util.ItemOffsetDecoration
import app.template.navigation.ContactListNavigator
import dagger.hilt.android.AndroidEntryPoint
import io.bloco.contactlist.R
import io.bloco.contactlist.databinding.FragmentContactBinding
import javax.inject.Inject

@AndroidEntryPoint
class ContactList : Fragment(R.layout.fragment_contact), ItemClickListener {

    private var _binding: FragmentContactBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var contactAdapter: ContactListAdapter

    @Inject
    lateinit var contactListNavigator: ContactListNavigator

    private val viewModel: ContactListViewModal by viewModels()

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentContactBinding.bind(view).also {
            it.lifecycleOwner = viewLifecycleOwner
            it.contactListAdapter = contactAdapter
            it.contactViewModal = viewModel
        }

        setUpRecyclerView()
        initializeObserver()

        if (savedInstanceState.isNull())
            viewModel.fetchContactList()
    }

    private fun setUpRecyclerView() {
        with(binding.contactListRecyclerView) {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)

            addItemDecoration(
                ItemOffsetDecoration(requireContext(), R.dimen.list_item_padding)
            )
            addOnScrollListener(object :
                EndlessRecyclerViewScrollListener(layoutManager as LinearLayoutManager) {
                override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                    viewModel.loadMore()
                }
            })
            adapter = contactAdapter
            (adapter as ContactListAdapter).stateRestorationPolicy =
                RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        }
    }

    private fun initializeObserver() {
        viewModel.contactList.observe(viewLifecycleOwner, {
            contactAdapter.submitList(it)
        })
    }

    override fun itemClicked(userId: Long) {
        contactListNavigator.navigateToContactDetailScreen(findNavController(), userId.toString())
    }

    override fun markFavouriteUnFavourite(position: Int) {
        viewModel.markFavouriteUnFavourite(position)
    }
}
