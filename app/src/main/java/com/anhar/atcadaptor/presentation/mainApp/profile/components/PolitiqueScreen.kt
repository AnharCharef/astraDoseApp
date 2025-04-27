package com.anhar.atcadaptor.presentation.mainApp.profile.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.anhar.atcadaptor.presentation.components.CustomTopAppBar
import com.anhar.atcadaptor.presentation.mainApp.home.HomeScreen


class PolitiqueScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val scrollState = rememberScrollState()

        Scaffold(
            topBar = {
                CustomTopAppBar(title = "" , isArabic = false) {
                    if (navigator.canPop) {
                        navigator.pop()
                    } else {
                        navigator.replace(HomeScreen())
                    }
                }
            }
        ) { padding ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .padding(16.dp)
                    .verticalScroll(scrollState)
            ) {
                Text(
                    text = "Politique de confidentialité",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(bottom = 12.dp)
                )

                Text(
                    text = "Chez CancerDose, la confidentialité de vos données est notre priorité. "
                            + "Cette politique décrit les informations que nous collectons, comment nous les utilisons, "
                            + "et vos droits concernant vos données personnelles.",
                    fontSize = 16.sp,
                    lineHeight = 24.sp,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Text(
                    text = "1. Données collectées",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                Text(
                    text = "Nous pouvons collecter les informations suivantes :\n" +
                            "- Informations personnelles saisies dans l’application (âge, poids, taille, etc.)\n" +
                            "- Données d’utilisation anonymes pour améliorer notre service.",
                    fontSize = 16.sp,
                    lineHeight = 24.sp
                )

                Text(
                    text = "2. Utilisation des données",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                Text(
                    text = "Les données sont utilisées uniquement pour fournir des calculs médicaux précis "
                            + "et améliorer votre expérience utilisateur. Aucune donnée ne sera partagée sans votre consentement.",
                    fontSize = 16.sp,
                    lineHeight = 24.sp
                )

                Text(
                    text = "3. Sécurité",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                Text(
                    text = "Nous mettons en œuvre des mesures de sécurité strictes pour protéger vos informations contre "
                            + "tout accès non autorisé ou toute altération.",
                    fontSize = 16.sp,
                    lineHeight = 24.sp
                )

                Text(
                    text = "4. Vos droits",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                Text(
                    text = "Vous pouvez à tout moment demander la suppression de vos données ou accéder à celles-ci.",
                    fontSize = 16.sp,
                    lineHeight = 24.sp
                )

                Text(
                    text = "\nSi vous avez des questions, veuillez nous contacter à : support@cancerdose.app",
                    fontSize = 16.sp,
                    lineHeight = 24.sp,
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.Start
                )
            }
        }
    }


}