package com.anhar.atcadaptor.presentation.mainApp.calculation.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import com.anhar.atcadaptor.presentation.ui.theme.ATCAdaptorTheme


@Composable
fun PatientMedicationForm() {
    var doctor by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("2024-05-07") }
    var patient by remember { mutableStateOf("") }
    var fileNumber by remember { mutableStateOf("033/2024") }
    var dob by remember { mutableStateOf("1980-12-12") }
    var age by remember { mutableStateOf("43") }
    var weight by remember { mutableStateOf("90.00") }
    var height by remember { mutableStateOf("175.00") }
    var gender by remember { mutableStateOf("Femme") }
    var race by remember { mutableStateOf("Non afro-américain") }
    var medicationName by remember { mutableStateOf("MITOMYCINE") }
    var dose by remember { mutableStateOf("40.00") }
    var dailyDose by remember { mutableStateOf("80.00") }
    var auc by remember { mutableStateOf("5") }
    var creatinine by remember { mutableStateOf("1.20") }
    var dfg by remember { mutableStateOf("MDRD") }
    var alat by remember { mutableStateOf("170.00") }
    var asat by remember { mutableStateOf("") }
    var pal by remember { mutableStateOf("51.00") }
    var tBil by remember { mutableStateOf("<12") }
    var renalToxicity by remember { mutableStateOf("Sélectionner…") }
    var hepaticToxicity by remember { mutableStateOf("Sélectionner…") }
    var dialysis by remember { mutableStateOf("Sélectionner…") }

    LazyColumn(modifier = Modifier.padding(16.dp)) {
        item {
            OutlinedTextField(
                value = doctor,
                onValueChange = { doctor = it },
                label = { Text("Docteur") })
        }
        item {
            OutlinedTextField(
                value = date,
                onValueChange = { date = it },
                label = { Text("Date") })
        }
        item {
            OutlinedTextField(
                value = patient,
                onValueChange = { patient = it },
                label = { Text("Patient") })
        }
        item {
            OutlinedTextField(
                value = fileNumber,
                onValueChange = { fileNumber = it },
                label = { Text("Num de dossier") })
        }
        item {
            OutlinedTextField(
                value = dob,
                onValueChange = { dob = it },
                label = { Text("Date de naissance") })
        }
        item {
            OutlinedTextField(
                value = age,
                onValueChange = { age = it },
                label = { Text("Age [année]") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
        }
        item {
            OutlinedTextField(
                value = weight,
                onValueChange = { weight = it },
                label = { Text("Poids (Kg)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
        }
        item {
            OutlinedTextField(
                value = height,
                onValueChange = { height = it },
                label = { Text("Taille (cm)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
        }
        item {
            OutlinedTextField(
                value = medicationName,
                onValueChange = { medicationName = it },
                label = { Text("Nom du Médicament Anticancéreux") })
        }
        item {
            OutlinedTextField(
                value = dose,
                onValueChange = { dose = it },
                label = { Text("Dose [m2]") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
        }
        item {
            OutlinedTextField(
                value = dailyDose,
                onValueChange = { dailyDose = it },
                label = { Text("Dose journalière [mg]") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
        }
        item {
            OutlinedTextField(
                value = auc,
                onValueChange = { auc = it },
                label = { Text("AUC cible [mg-min/L]") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
        }
        item {
            OutlinedTextField(
                value = creatinine,
                onValueChange = { creatinine = it },
                label = { Text("Créat [mg/dl]") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
        }
        item {
            OutlinedTextField(
                value = dfg,
                onValueChange = { dfg = it },
                label = { Text("DFG [mL/min]*") })
        }
        item {
            OutlinedTextField(
                value = alat,
                onValueChange = { alat = it },
                label = { Text("ALAT/TGP [UI/L]") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
        }
        item {
            OutlinedTextField(
                value = pal,
                onValueChange = { pal = it },
                label = { Text("PAL [UI/L]") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
        }
        item {
            OutlinedTextField(
                value = tBil,
                onValueChange = { tBil = it },
                label = { Text("T.Bil [mg/L]") })
        }
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(onClick = { /* Perform SC Calculation */ }) { Text("Calculate SC") }
                Button(onClick = { /* Perform DFG Calculation */ }) { Text("Calculate DFG") }
                Button(onClick = { /* Calculate Dose */ }) { Text("Calculate Dose") }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewForm() {
    ATCAdaptorTheme {
        PatientMedicationForm()
    }
}
