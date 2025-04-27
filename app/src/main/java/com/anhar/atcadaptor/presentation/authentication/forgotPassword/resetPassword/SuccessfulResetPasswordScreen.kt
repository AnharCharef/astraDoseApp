package com.anhar.atcadaptor.presentation.authentication.forgotPassword.resetPassword

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import com.anhar.atcadaptor.R
import com.anhar.atcadaptor.common.Dimens
import com.anhar.atcadaptor.common.Dimens.LargePadding
import com.anhar.atcadaptor.presentation.authentication.login.LoginScreen


class SuccessfulResetPasswordScreen : Screen {
    @Composable
    override fun Content() {

        val navigator = LocalNavigator.current

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(LargePadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Column(
                modifier = Modifier.padding(
                    vertical = Dimens.MediumPadding
                ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.successfull_img),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(150.dp)
                )
                Text(
                    text = stringResource(id = R.string.successfullyResetPassword), Modifier.padding(vertical = Dimens.ExtraSmallPadding),
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
            }

            Button(
                onClick = {
                    navigator?.push(LoginScreen())
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        vertical = Dimens.MediumPadding
                    ),
                shape = RoundedCornerShape(30)
            ) {
                Text(
                    text = stringResource(id = R.string.goToLoginScreen),
                    Modifier.padding(vertical = Dimens.ExtraSmallPadding),
                    style = MaterialTheme.typography.displaySmall.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    textAlign = TextAlign.Center
                )
            }

        }
    }

}