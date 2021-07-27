package io.bloco.contactDetail.ui

import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import app.template.base.util.isNull
import app.template.base_android.extensions.shortToast
import app.template.base_android.util.event.EventObserver
import app.template.navigation.ContactDetailNavigator
import dagger.hilt.android.AndroidEntryPoint
import io.bloco.contactDetail.R
import io.bloco.contactDetail.databinding.FragmentContactdetailBinding
import javax.inject.Inject

@AndroidEntryPoint
class ContactDetail : Fragment(R.layout.fragment_contactdetail) {

    private var _binding: FragmentContactdetailBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var contactDetailNavigator: ContactDetailNavigator

    private val viewModel: ContactDetailViewModal by viewModels()

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(this) {
            contactDetailNavigator.navigateToBack(findNavController())
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentContactdetailBinding.bind(view).also {
            it.lifecycleOwner = viewLifecycleOwner
            it.contactDetailViewModel = viewModel
        }

        binding.contactDetailToolbar.setOnClickListener {
            contactDetailNavigator.navigateToBack(findNavController())
        }

        setupObservers()

        if (savedInstanceState.isNull())
            viewModel.getUser()
    }

    private fun setupObservers() {
        viewModel.errorUpdateMsg.observe(viewLifecycleOwner, EventObserver {
            requireContext().shortToast(it)
        })
    }
}
