package com.anhar.atcadaptor.presentation.mainApp.details.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.anhar.atcadaptor.common.Dimens.BottomBarHeight
import com.anhar.atcadaptor.common.Dimens.MediumPadding
import com.anhar.atcadaptor.common.Dimens.SmallPadding
import com.anhar.atcadaptor.presentation.mainApp.details.DetailsState

@Composable
fun LaboResultsInformationCard(modifier : Modifier = Modifier, state: DetailsState){

    val details = state.detailsData

    if (details?.creatinine != null || details?.dfg != null || details?.alat != null ||
        details?.asat != null || details?.pal != null || details?.tbil != null) {

        Card(
            modifier = modifier.fillMaxWidth()
                .padding(bottom = SmallPadding + BottomBarHeight),
            shape = RoundedCornerShape(SmallPadding),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(
                modifier = Modifier.padding(horizontal = MediumPadding)
                    .padding(vertical = SmallPadding)
            ) {
                Text(
                    "🧪 Lab Results",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier.padding(bottom = SmallPadding)
                )
                details.creatinine?.let { Text("🩸 Creatinine : $it mg/dL") }
                details.dfg?.let { Text("💧 DFG (Glomerular filtration rate) : $it mL/min") }
                details.alat?.let { Text("🧬 ALAT (TGP) : $it UI/L") }
                details.asat?.let { Text("🧬 ASAT (TGO) : $it UI/L") }
                details.pal?.let { Text("🦴 PAL (Alkaline phosphatase) : $it UI/L") }
                details.tbil?.let { Text("🩸 Total bilirubin(TBIL) : $it mg/L") }
            }
        }
    }
//    Card(
//        modifier = modifier.fillMaxWidth()
//            .padding(bottom = SmallPadding + BottomBarHeight),
//        shape = RoundedCornerShape(SmallPadding),
//        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
//        colors = CardDefaults.cardColors(containerColor = Color.White)
//    ) {
//        Column(modifier = Modifier.padding(horizontal = MediumPadding)
//            .padding(vertical = SmallPadding)) {
//            Text(
//                "🧪 Résultats de Laboratoire",
//                style = MaterialTheme.typography.bodyLarge.copy(
//                    fontWeight = FontWeight.Bold
//                ),
//                modifier = Modifier.padding(bottom = SmallPadding)
//            )
//            Text("🩸 Créatinine : ${state.detailsData?.creatinine} mg/dL")
//            Text("💧 DFG (Débit de Filtration Glomérulaire) : ${state.detailsData?.dfg} mL/min")
//            Text("🧬 ALAT (TGP) : ${state.detailsData?.alat} UI/L")
//            Text("🧬 ASAT (TGO) : ${state.detailsData?.asat} UI/L")
//            Text("🦴 PAL (Phosphatases Alcalines) : ${state.detailsData?.pal} UI/L")
//            Text("🩸 Bilirubine Totale : ${state.detailsData?.tbil} mg/L")
//        }
//    }
}