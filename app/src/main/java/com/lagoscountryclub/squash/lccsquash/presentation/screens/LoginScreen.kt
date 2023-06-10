package com.lagoscountryclub.squash.lccsquash.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.lagoscountryclub.squash.lccsquash.R
import com.lagoscountryclub.squash.lccsquash.presentation.HandleLoadingAndError
import com.lagoscountryclub.squash.lccsquash.presentation.composables.AppButton
import com.lagoscountryclub.squash.lccsquash.presentation.composables.AppTextField
import com.lagoscountryclub.squash.lccsquash.presentation.viewmodels.AdminViewModel
import com.lagoscountryclub.squash.lccsquash.showToast

@Composable
fun LoginScreen(
    navController: NavHostController? = null,
    showPreview: Boolean = false,
    viewModel: AdminViewModel? = hiltViewModel()
) {
    if (!showPreview && viewModel!!.user.id != -1) {
        showToast( "${viewModel.user.name} logged in")
        navController?.navigateUp()
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 48.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            imageVector = Icons.Default.Close,
            contentDescription = stringResource(R.string.close),
            alignment = Alignment.TopEnd,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
                .clickable { navController?.popBackStack() }
        )
        AppTextField(
            text = if (showPreview) "" else viewModel!!.loginRequest.email,
            onValueChange = { viewModel?.updateEmail(it) },
            hint = "Email",
            keyboardType = KeyboardType.Email,
            enabled = true
        )

        AppTextField(
            text = if (showPreview) "" else viewModel!!.loginRequest.password,
            onValueChange = { viewModel?.updatePassword(it) },
            hint = "Password",
            keyboardType = KeyboardType.Password,
            enabled = true
        )

        AppButton(modifier = Modifier.fillMaxWidth(), text = "Login") {
            viewModel?.login()
        }
    }

    if (!showPreview) {
        HandleLoadingAndError(viewModels = arrayOf(viewModel!!))
    }
}

@Preview
@Composable
fun LoginScreenPreview() {
    LoginScreen(showPreview = true, viewModel = null)
}