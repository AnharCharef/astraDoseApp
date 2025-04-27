package com.anhar.atcadaptor.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Accessibility
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.anhar.atcadaptor.R
import com.anhar.atcadaptor.common.Dimens.BottomBarHeight
import com.anhar.atcadaptor.common.Dimens.LargePadding
import com.anhar.atcadaptor.common.Dimens.MediumPadding
import com.anhar.atcadaptor.common.Dimens.SmallPadding

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NeedToBeLogged(modifier: Modifier = Modifier,goToLoginScreen: ()-> Unit , onDismissRequest: () -> Unit) {

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    ModalBottomSheet(
        modifier = modifier
            .fillMaxSize()
            .padding(top = BottomBarHeight),
        onDismissRequest = { onDismissRequest() },
        containerColor = MaterialTheme.colorScheme.background,
        sheetState = sheetState
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = MediumPadding, vertical = SmallPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Illustration
            Image(
                imageVector = Icons.Default.Accessibility, // Remplacez avec votre ressource
                contentDescription = null,
                modifier = Modifier
                    .size(120.dp)
                    .padding(bottom = MediumPadding)
            )

            // Titre
            Text(
                text = "Un compte est requis",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(SmallPadding))

            // Sous-titre
            Text(
                text = "Créez un compte ou connectez-vous pour accéder à cette fonctionnalité.",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = colorResource(R.color.body)
                ),
                textAlign = TextAlign.Center,
            )

            Spacer(modifier = Modifier.height(LargePadding))

            // Bouton "Créer un compte"
            Button (
                onClick = {
                    goToLoginScreen()
                },
                modifier = Modifier.padding(horizontal = MediumPadding),
                shape = CircleShape
            ) {
                Text("Créer un compte" , style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Bold
                ))
            }
        }
    }
}



