package com.anhar.atcadaptor.presentation.mainApp.details.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.anhar.atcadaptor.common.Dimens.MediumPadding
import com.anhar.atcadaptor.common.Dimens.SmallPadding
import com.anhar.atcadaptor.presentation.components.CustomIcon
import com.anhar.atcadaptor.presentation.mainApp.details.DetailsState

@Composable
fun PatientInformationCard(modifier: Modifier = Modifier, state: DetailsState ,onIconClicked : ()-> Unit) {

    val patient = state.detailsData

    if (patient?.patientId != null || patient?.name != null || patient?.age != null ||
        patient?.poids != null || patient?.taille != null || patient?.genre != null || patient?.race != null
    ) {

        Card(
            modifier = modifier.fillMaxWidth(),
            shape = RoundedCornerShape(SmallPadding),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = MediumPadding)
                    .padding(vertical = SmallPadding)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = SmallPadding)
                ) {
                    Text(
                        "👤 Patient information",
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )

                    Spacer(Modifier.weight(1f))

                    CustomIcon(imageVector = Icons.Default.ArrowBack,
                        backgroundColor = Color.White,
                        modifier = Modifier.rotate(180f)) {
                        onIconClicked()
                    }
                }

                patient.patientId?.let { Text("🆔 ID : $it") }
                patient.name?.let { Text("📛 Name : $it") }
                patient.age?.let { Text("🎂 Age : $it ans") }
                patient.poids?.let { Text("⚖️ Weight : $it kg") }
                patient.taille?.let { Text("📏 Height : $it cm") }
                patient.genre?.let { Text("🚻 Gender : $it") }
                patient.race?.let { Text("🌎 Race : $it") }
            }
        }
    }
}