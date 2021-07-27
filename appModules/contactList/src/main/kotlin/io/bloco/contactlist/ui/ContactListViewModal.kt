package io.bloco.contactlist.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.template.base.extensions.computationalBlock
import app.template.base.useCases.AppError
import app.template.base.useCases.AppSuccess
import app.template.base.util.network.AppCoroutineDispatchers
import dagger.hilt.android.lifecycle.HiltViewModel
import io.bloco.contact.sharedModule.SharedCoroutine
import io.bloco.contact.sharedModule.domain.usecase.ContactIsFavouriteUseCase
import io.bloco.contact.sharedModule.modals.ContactModals
import io.bloco.contactlist.domain.usecase.ContactListUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ContactListViewModal @Inject constructor(
    private val coroutineDispatcherProvider: AppCoroutineDispatchers,
    private val contactListUseCase: ContactListUseCase,
    private val contactIsFavouriteUseCase: ContactIsFavouriteUseCase,
    private val sharedCoroutine: SharedCoroutine
) : ViewModel() {

    var pageNumber: Int = 1
    var isLoadInProgress = false

    private val _isLoading = MutableLiveData(true)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMsg = MutableLiveData("")
    val errorMsg: LiveData<String> = _errorMsg

    private val _contactList = MutableLiveData(emptyList<ContactModals>())
    val contactList: LiveData<List<ContactModals>> = _contactList

    init {
        Timber.e("sharedCoroutine in list:: $sharedCoroutine")
        viewModelScope.launch {
            sharedCoroutine.tickFlow.collect {

                Timber.e("sharedCoroutine collect list:: $it")

                val newContactList = contactList.value!!.toMutableList()
                newContactList[it.first] = it.second

                _contactList.value = newContactList
            }

        }
    }

    fun fetchContactList() {
        if (contactList.value!!.isNullOrEmpty())
            useCaseCall({
                _isLoading.value = it
            }, {
                _errorMsg.value = it
            })
    }

    fun retryContactList() {
        _isLoading.value = true
        _errorMsg.value = ""
        fetchContactList()
    }

    private fun useCaseCall(
        blockLoader: (Boolean) -> Unit,
        blockError: (String) -> Unit,
        blockComplete: () -> Unit = {}
    ) {
        computationalBlock {
            contactListUseCase(pageNumber, contactList.value!!.size).flowOn(
                coroutineDispatcherProvider.io
            ).catch { e ->
                blockError(e.localizedMessage ?: "")
            }.onStart {
                blockLoader(true)
            }.onCompletion {
                blockLoader(false)
            }.flowOn(coroutineDispatcherProvider.main)
                .collect {

                    withContext(coroutineDispatcherProvider.main) {
                        blockComplete()

                        when (it) {
                            is AppSuccess -> {
                                val previousList = contactList.value!!.toMutableList()
                                previousList.addAll(it.data)
                                _contactList.value = previousList
                            }
                            is AppError -> {
                                blockError(it.message ?: it.throwable?.message ?: "")
                            }
                            else -> throw IllegalArgumentException("Illegal State")
                        }
                    }
                }
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

    fun loadMore() {

        if (isLoadInProgress) {
            return
        }

        isLoadInProgress = true

        val contactLoadElement = ContactModals(-1L)

        val newList = contactList.value!!.toMutableList()
        newList.add(contactLoadElement)

        _contactList.value = newList

        pageNumber++

        useCaseCall({
        }, {
            val lastElement = contactList.value!!.last()
            if (-1L == lastElement.id) {
                removeLoaderElement()
                isLoadInProgress = false
            }
        }, {
            removeLoaderElement()
            isLoadInProgress = false
        })
    }

    private fun removeLoaderElement() {
        val newList = contactList.value!!.toMutableList()
        newList.removeAt(contactList.value!!.size - 1)

        _contactList.value = newList
    }

    fun markFavouriteUnFavourite(position: Int) {
        val item = contactList.value!![position].copy(isUpdate = true)

        val newContactList = contactList.value!!.toMutableList()
        newContactList[position] = item

        _contactList.value = newContactList

        markIsFavourite(position, item)
    }

    private fun markIsFavourite(position: Int, contactModal: ContactModals) {
        computationalBlock {
            contactIsFavouriteUseCase(contactModal).flowOn(coroutineDispatcherProvider.io)
                .catch { e ->
                    _errorMsg.value = e.localizedMessage ?: ""
                    updateIsFavouriteStatus(position)
                }.onStart {
                    _isLoading.value = true
                }.onCompletion {
                    _isLoading.value = false
                }.onEach {
                    when (it) {
                        is AppSuccess -> {
                            updateIsFavouriteStatus(position, true, it.data)
                            sharedCoroutine.updateFlow(it.data, position)
                        }
                        is AppError -> {
                            updateIsFavouriteStatus(position)
                        }
                        else -> throw IllegalArgumentException("Illegal State")
                    }
                }.flowOn(coroutineDispatcherProvider.main)
                .launchIn(viewModelScope)
        }
    }

    private fun updateIsFavouriteStatus(
        position: Int, isSuccess: Boolean = false,
        contactModal: ContactModals? = null
    ) {
        val newContactList = contactList.value!!.toMutableList()
        if (isSuccess) {
            newContactList[position] = contactModal!!
        } else {
            val item = contactList.value!![position].copy(isUpdate = false)

            newContactList[position] = item
        }
        _contactList.value = newContactList
    }
}
