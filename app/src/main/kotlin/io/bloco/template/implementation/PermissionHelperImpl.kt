package io.bloco.template.implementation

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import app.template.base.util.permission.PermissionHelper
import app.template.base_android.util.isAndroidMOrLater
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class PermissionHelperImpl @Inject constructor(private val context: Context) : PermissionHelper {

    override fun hasPermissions(permission: List<String>): Boolean {
        return hasPermissions(*permission.toTypedArray())
    }

    override fun hasPermissions(permission: String): Boolean {
        return hasPermissions(*arrayOf(permission))
    }

    override fun hasPermissions(vararg perms: String): Boolean {
        if (isAndroidMOrLater)
            for (perm in perms)
                if (ContextCompat.checkSelfPermission(
                        context,
                        perm
                    ) != PackageManager.PERMISSION_GRANTED
                )
                    return false
        return true
    }
}