package com.example.planificardecomidas.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.planificardecomidas.ViewModels.RecipeViewModel

@Composable
fun WeeklyPlanScreen(viewModel: RecipeViewModel) {

    Column(modifier = Modifier.padding(16.dp)) {

        Text("Asignar Comidas", textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp))

        viewModel.planSemanal.forEach { (dia, receta) ->

            var expanded by remember { mutableStateOf(false) }

            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)) {

                Text(dia, modifier = Modifier.weight(1f))

                Box {

                    Button(onClick = {
                        expanded = true
                    }) {
                        Text(receta?.name ?: "Asignar")
                    }

                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {

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

            //Spacer(modifier = Modifier.height(5.dp))
        }
    }
}