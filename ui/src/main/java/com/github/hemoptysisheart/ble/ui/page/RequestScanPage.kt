package com.github.hemoptysisheart.ble.ui.page

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.github.hemoptysisheart.ble.ui.atom.AndroidBleTheme
import com.github.hemoptysisheart.ble.ui.navigator.RequestScanNavigator
import com.github.hemoptysisheart.ble.viewmodel.RequestScanViewModel
import com.github.hemoptysisheart.ui.compose.preview.PreviewPage
import com.github.hemoptysisheart.ui.navigation.compose.baseNavigator
import com.github.hemoptysisheart.ui.navigation.compose.baseViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState

@Composable
@OptIn(ExperimentalPermissionsApi::class)
fun RequestScanPage(
    navigator: RequestScanNavigator,
    viewModel: RequestScanViewModel = baseViewModel()
) {
    Log.v(TAG, "#RequestScanPage args : navigator=$navigator")

    val granted by viewModel.granted.collectAsStateWithLifecycle()
    val permissionRequested by viewModel.permissionRequested.collectAsStateWithLifecycle()

    val permissionState = rememberMultiplePermissionsState(viewModel.permission)

    RequestScanPageContent(
        navigator = navigator,
        granted = granted,
        permissionRequested = permissionRequested,
        onClickRequestPermission = {
            viewModel.onClickRequestPermission(permissionState::launchMultiplePermissionRequest)
        }
    )
}

@Composable
internal fun RequestScanPageContent(
    navigator: RequestScanNavigator,
    granted: Boolean,
    permissionRequested: Boolean,
    onClickRequestPermission: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(100.dp))
        when {
            granted -> {
                Text(
                    text = "이미 Bluetooth 기기 검색 권한이 있습니다.",
                    modifier = Modifier.padding(16.dp),
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.weight(1F))
                Button(onClick = navigator::main, modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "메인 화면",
                        color = MaterialTheme.colorScheme.onPrimary,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            permissionRequested -> {
                Text(
                    text = "Bluetooth 기기 검색 권한을 요청합니다.",
                    modifier = Modifier.padding(16.dp),
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleMedium
                )

                Text(
                    text = "기기 검색 권한은 필수입니다.",
                    modifier = Modifier.padding(16.dp, 8.dp),
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "'기기 검색 권한 요청' 버튼을 클릭해도 권한 승인 다이얼로그가 보이지 않는다면 앱 설정에서 권한을 수동으로 설정해주세요.",
                    modifier = Modifier.padding(16.dp, 8.dp),
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyMedium
                )

                Button(onClick = navigator::settings, modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "앱 설정 열기",
                        color = MaterialTheme.colorScheme.onPrimary,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                Spacer(modifier = Modifier.weight(1F))
                Button(onClick = onClickRequestPermission, modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "기기 검색 권한 요청",
                        color = MaterialTheme.colorScheme.onPrimary,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            else -> {
                Text(
                    text = "Bluetooth 기기 검색 권한을 요청합니다.",
                    modifier = Modifier.padding(16.dp),
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(modifier = Modifier.weight(1F))
                Button(onClick = onClickRequestPermission, modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "기기 검색 권한 요청",
                        color = MaterialTheme.colorScheme.onPrimary,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(50.dp))
    }
}

internal data class RequestScanPageParam(
    val granted: Boolean,
    val permissionRequested: Boolean
)

internal class RequestScanPageParamProvider : PreviewParameterProvider<RequestScanPageParam> {
    override val values = sequenceOf(
        RequestScanPageParam(granted = true, permissionRequested = true),
        RequestScanPageParam(granted = true, permissionRequested = false),
        RequestScanPageParam(granted = false, permissionRequested = true),
        RequestScanPageParam(granted = false, permissionRequested = false)
    )
}

@Composable
@PreviewPage
internal fun PreviewRequestScanPageContent(
    @PreviewParameter(RequestScanPageParamProvider::class) param: RequestScanPageParam
) {
    AndroidBleTheme {
        RequestScanPageContent(
            navigator = RequestScanNavigator(baseNavigator()),
            granted = param.granted,
            permissionRequested = param.permissionRequested
        )
    }
}
