package com.github.hemoptysisheart.ble.model

interface PermissionModel {
    /**
     * 권한이 있는지 확인한다.
     *
     * @param permissions 확인하려는 권한.
     * @return 권한이 있으면 `true`, 없으면 `false`.
     *
     * @see android.Manifest.permission
     */
    fun hasAllPermission(vararg permissions: String): Boolean
}
