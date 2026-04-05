package com.example.planificardecomidas.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.planificardecomidas.ViewModels.RecipeViewModel

@Composable
fun WeeklyPlanScreen(viewModel: RecipeViewModel) {

    //Lista fija de días en el orden correcto
    val daysInOrder = listOf("Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            "Plan Semanal",
            fontSize = 20.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp)
        )

        // Itera sobre la lista ordenada, no sobre el mapa directamente
        daysInOrder.forEach { dia ->

            val receta = viewModel.planSemanal[dia]
            var expanded by remember { mutableStateOf(false) }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = dia,
                        modifier = Modifier.weight(1f),
                        style = MaterialTheme.typography.bodyLarge
                    )

                    Box {
                        Button(onClick = { expanded = true }) {
                            Text(receta?.name ?: "Asignar")
                        }

                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            DropdownMenuItem(
                                text = { Text("— Sin receta —") },
                                onClick = {
                                    viewModel.asignarReceta(dia, null)
                                    expanded = false
                                }
                            )

                            if (viewModel.recetas.isEmpty()) {
                                DropdownMenuItem(
                                    text = { Text("No hay recetas creadas") },
                                    onClick = { expanded = false },
                                    enabled = false
                                )
                            } else {
                                viewModel.recetas.forEach { rec ->
                                    DropdownMenuItem(
                                        text = { Text(rec.name) },
                                        onClick = {
                                            viewModel.asignarReceta(dia, rec)
                                            expanded = false
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}