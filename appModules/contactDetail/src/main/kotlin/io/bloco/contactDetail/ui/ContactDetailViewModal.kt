package io.bloco.contactDetail.ui

import androidx.lifecycle.*
import app.template.base.extensions.computationalBlock
import app.template.base.useCases.AppError
import app.template.base.useCases.AppSuccess
import app.template.base.util.isNotNull
import app.template.base.util.network.AppCoroutineDispatchers
import app.template.base_android.util.event.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import io.bloco.contact.sharedModule.SharedCoroutine
import io.bloco.contact.sharedModule.domain.usecase.ContactIsFavouriteUseCase
import io.bloco.contact.sharedModule.modals.ContactModals
import io.bloco.contactDetail.domain.usecase.ContactDetailUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ContactDetailViewModal @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val coroutineDispatcherProvider: AppCoroutineDispatchers,
    private val contactDetailUseCase: ContactDetailUseCase,
    private val contactIsFavouriteUseCase: ContactIsFavouriteUseCase,
    private val sharedCoroutine: SharedCoroutine
) : ViewModel() {

    companion object {
        const val BUNDLE_USER_ID = "userId"
    }

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMsg = MutableLiveData("")
    val errorMsg: LiveData<String> = _errorMsg

    private val _errorUpdateMsg = MutableLiveData<Event<String>>()
    val errorUpdateMsg: LiveData<Event<String>> = _errorUpdateMsg

    private val _contact = MutableLiveData<ContactModals>()
    val contact: LiveData<ContactModals> = _contact

    init {
        Timber.e("sharedCoroutine in detail:: $sharedCoroutine")
        viewModelScope.launch {
            sharedCoroutine.tickFlow.collect {

                if (contact.value.isNotNull()) {
                    Timber.e("sharedCoroutine collect detail contact:: $it")
                    if (contact.value!!.id == it.second.id)
                        _contact.value = it.second
                }
                Timber.e("sharedCoroutine collect detail:: $it")
            }
        }
    }

    fun getUser() = computationalBlock {
        val data = contactDetailUseCase(savedStateHandle.get<String>(BUNDLE_USER_ID)!!.toLong())
        withContext(coroutineDispatcherProvider.main) {
            when (data) {
                is AppSuccess -> {
                    _contact.value = data.data
                }
                is AppError -> {
                    _errorMsg.value = data.message ?: data.throwable?.message ?: ""
                }
                else -> throw IllegalArgumentException("Illegal State")
            }
        }
    }

    fun markIsFavourite() {
        _isLoading.value = true

        computationalBlock {
            contactIsFavouriteUseCase(contact.value!!).flowOn(coroutineDispatcherProvider.io)
                .catch { e ->
                    _errorMsg.value = e.localizedMessage ?: ""
                }.onStart {
                    _isLoading.value = true
                }.onCompletion {
                    _isLoading.value = false
                }.onEach {
                    when (it) {
                        is AppSuccess -> {
                            _contact.value = it.data
                            sharedCoroutine.updateFlow(it.data)
                        }
                        is AppError -> {
                            _errorUpdateMsg.value =
                                Event(it.message ?: it.throwable?.message ?: "")
                        }
                        else -> throw IllegalArgumentException("Illegal State")
                    }
                }.flowOn(coroutineDispatcherProvider.main)
                .launchIn(viewModelScope)
        }
    }

    private fun computationalBlock(
        coroutineExceptionHandler: CoroutineExceptionHandler? = null,
        block: suspend CoroutineScope.() -> Unit
    ) {
        viewModelScope.computationalBlock(
            coroutineDispatcherProvider,
            coroutineExceptionHandler,
            block
        )
    }
}
