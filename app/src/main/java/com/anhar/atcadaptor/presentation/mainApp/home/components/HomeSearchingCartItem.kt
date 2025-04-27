package com.anhar.atcadaptor.presentation.mainApp.home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Medication
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.anhar.atcadaptor.common.Dimens.MediumPadding
import com.anhar.atcadaptor.common.Dimens.SmallPadding
import com.anhar.atcadaptor.domain.model.home.HomeSearchItemData
import com.anhar.atcadaptor.presentation.ui.theme.ATCAdaptorTheme


@Stable
@Composable
fun HomeSearchingCartItem(
    modifier: Modifier = Modifier,
    homeSearchItemData: HomeSearchItemData,
    userType: Int,
    onClick: () -> Unit,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(SmallPadding)
            .clickable { onClick() },
        shape = RoundedCornerShape(SmallPadding),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier

                .padding(horizontal = MediumPadding )
                .padding(bottom = SmallPadding)

        ) {
            if (userType == 1) {
                // Doctors can see patient info
                Text(
                    text = homeSearchItemData.patientName ?: "",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "ID: ${homeSearchItemData.patientId}",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )


            }

            // Medication & Dose (visible to both doctors & patients)
            homeSearchItemData.medicationName?.let { medicationName ->
//                Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.padding(top = if (userType == 1) 0.dp else SmallPadding)
                    .offset(x = (-5).dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Default.Medication, contentDescription = "Medication")
                Spacer(modifier = Modifier.width(8.dp))
                    homeSearchItemData.dose?.let { dose ->
                        Text(
                            text = "$medicationName - $dose",
                            style = MaterialTheme.typography.bodyLarge,
                        )
                    }
                }
            }

            if (userType == 1) {


                // AUC & DFG (Doctors only)
                if (homeSearchItemData.auc != null || homeSearchItemData.dfg != null) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        homeSearchItemData.auc?.let { Text(text = "AUC: $it", fontWeight = FontWeight.Medium) }
                        homeSearchItemData.dfg?.let { Text(text = "DFG: $it mL/min", fontWeight = FontWeight.Medium) }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }

                // Toxicity Alert (Doctors only)
                homeSearchItemData.toxicity?.takeIf { it.isNotEmpty() }?.let {
                    Text(
                        text = "⚠️ Toxicité : $it",
                        color = Color.Red,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

//            Row(modifier = Modifier.padding(top = if(userType == 1) 0.dp else SmallPadding).offset(x= (-5).dp),
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                Icon(Icons.Default.Medication, contentDescription = "Medication")
//                Spacer(modifier = Modifier.width(8.dp))
//                Text(
//                    text = "${homeSearchItemData.medicationName} - ${homeSearchItemData.dose}",
//                    style = MaterialTheme.typography.bodyLarge ,
//                )
//            }
//
//            if (userType == 1) {
//                Spacer(modifier = Modifier.height(8.dp))
//
//                // AUC & DFG (Doctors only)
//                Row(
//                    horizontalArrangement = Arrangement.SpaceBetween,
//                    modifier = Modifier.fillMaxWidth()
//                ) {
//                    Text(text = "AUC: ${homeSearchItemData.auc}", fontWeight = FontWeight.Medium)
//                    Text(
//                        text = "DFG: ${homeSearchItemData.dfg} mL/min",
//                        fontWeight = FontWeight.Medium
//                    )
//                }
//
//                Spacer(modifier = Modifier.height(8.dp))
//
//                // Toxicity Alert (Doctors only)
//                if (homeSearchItemData.toxicity?.isNotEmpty() == true) {
//                    Text(
//                        text = "⚠️ Toxicité : ${homeSearchItemData.toxicity}",
//                        color = Color.Red,
//                        fontWeight = FontWeight.Bold
//                    )
//                }
//            }
        }
    }

}


@Preview(showBackground = true)
@Composable
fun HomeSearchingCartItemPreview() {
    ATCAdaptorTheme {
        HomeSearchingCartItem(
            homeSearchItemData = HomeSearchItemData(

                patientName = "John Doe",
                patientId = 33,
                medicationName = "Carboplatine",
                dose = "400 mg",
                auc = "5",
                dfg = "60",
                toxicity = "None"
            ),
            userType = 1,
            onClick = {}
        )
    }

}
