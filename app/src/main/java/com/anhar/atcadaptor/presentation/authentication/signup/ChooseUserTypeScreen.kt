package com.anhar.atcadaptor.presentation.authentication.signup

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.anhar.atcadaptor.R
import com.anhar.atcadaptor.common.Constant
import com.anhar.atcadaptor.common.Dimens.ExtraSmallPadding2
import com.anhar.atcadaptor.common.Dimens.LargePadding
import com.anhar.atcadaptor.common.Dimens.MediumPadding
import com.anhar.atcadaptor.common.Dimens.SmallPadding
import com.anhar.atcadaptor.common.UserRole
import java.util.Locale

class ChooseUserTypeScreen : Screen {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {

        val navigator = LocalNavigator.currentOrThrow
        val isArabic = LocalContext.current.getSharedPreferences(Constant.APP_LANG, 0)
            .getString(Constant.APP_LANG, Locale.getDefault().language) == "ar"

        Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
            CenterAlignedTopAppBar(colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.background
            ), title = {
                Text(
                    text = stringResource(id = R.string.signup),
                    modifier = Modifier,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }, navigationIcon = {
                IconButton(
                    onClick = {
                        navigator.pop()
                    },
                ) {
                    Image(
                        Icons.Default.KeyboardArrowDown,
                        colorFilter = ColorFilter.tint(color = Color.Gray),
                        modifier = Modifier
                            .rotate(if (isArabic) 90f else -90f)
                            .size(LargePadding),
                        contentDescription = null,
                        contentScale = ContentScale.Fit
                    )
                }
            }, modifier = Modifier.background(
                MaterialTheme.colorScheme.primary
            )
            )
        }) { innerPadding ->
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(innerPadding),

                horizontalAlignment = Alignment.CenterHorizontally
            ) {


                Text(
                    text = "Choose your user type" ,
                    style = MaterialTheme.typography.displayMedium.copy(
                        fontWeight = FontWeight.Bold
                    ) ,
                    textAlign = TextAlign.Center ,
                    modifier = Modifier.padding(top = SmallPadding)
                )

                Image(
                    painterResource(R.drawable.atc_logo),
                    contentDescription = null,
                    modifier = Modifier
                        .size(250.dp).
                    offset(y = (-15).dp)
                )
//                Spacer(modifier = Modifier.weight(0.2f))


                Button(onClick = {
                    navigator.push(SignUpScreen(userType = UserRole.Doctor))
                }, modifier = Modifier.padding(bottom = MediumPadding).width(150.dp)) {
                    Text(
                        "Doctor",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier.padding(
                            vertical = ExtraSmallPadding2
                        )
                    )
                }
                Button(onClick = {
                    navigator.push(SignUpScreen(userType = UserRole.Patient))
                }, modifier = Modifier.padding(bottom = MediumPadding).width(150.dp)) {
                    Text(
                        UserRole.Patient.name,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier.padding(
                            vertical = ExtraSmallPadding2
                        )
                    )
                }
                Spacer(modifier = Modifier.weight(1f))

            }
        }
    }
}
