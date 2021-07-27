package io.bloco.template.implementation

import app.template.base.util.network.AppCoroutineDispatchers
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppCoroutineDispatchersImpl @Inject constructor(
    override val main: CoroutineDispatcher,
    override val computation: CoroutineDispatcher,
    override val io: CoroutineDispatcher,
    override val default: CoroutineDispatcher,
    override val singleThread: CoroutineDispatcher
) : AppCoroutineDispatchers