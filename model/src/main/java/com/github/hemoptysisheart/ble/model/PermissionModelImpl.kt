package com.github.hemoptysisheart.ble.model

import android.content.Context
import android.content.pm.PackageManager
import android.util.Log

class PermissionModelImpl(
    private val context: Context
) : PermissionModel {
    companion object {
        private const val TAG = "PermissionModelImpl"
    }

    override fun hasAllPermission(vararg permissions: String): Boolean {
        val has = permissions.all { permission ->
            PackageManager.PERMISSION_GRANTED == context.checkSelfPermission(permission)
        }
        Log.d(TAG, "#hasPermission : ${permissions.toList()} => $has")
        return has
    }
}
