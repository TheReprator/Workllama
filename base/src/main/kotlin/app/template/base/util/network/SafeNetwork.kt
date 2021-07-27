package app.template.base.util.network

import app.template.base.useCases.AppError
import app.template.base.useCases.AppResult

suspend fun <T : Any> safeApiCall(
    call: suspend () -> AppResult<T>,
    errorMessage: String? = null
): AppResult<T> {
    return try {
        call()
    } catch (e: Exception) {
        AppError(message = errorMessage ?: e.message)
    }
}
