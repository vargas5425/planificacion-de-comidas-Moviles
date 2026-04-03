package com.example.planificardecomidas.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.planificardecomidas.ViewModels.RecipeViewModel
import com.example.planificardecomidas.models.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.ui.text.style.TextAlign
import kotlinx.coroutines.launch

@Composable
fun AddRecipeScreen(viewModel: RecipeViewModel,
                    onRecipeSave: () -> Unit) {

    var recipeName by remember { mutableStateOf("") }
    val ingredients = remember { mutableStateListOf<Ingredient>() }

    // Snackbar
    val mesjEmergente = remember { SnackbarHostState() }
    val errorMessage = rememberCoroutineScope()

    val isValid = recipeName.isNotBlank() &&
            ingredients.isNotEmpty() &&
            ingredients.all { it.name.isNotBlank() && it.quantity.isNotBlank() }

    Scaffold(
        snackbarHost = { SnackbarHost(mesjEmergente) }
    ) { innerPadding ->
        Text("Crear Receta", textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp))

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())

        ) {

            TextField(
                value = recipeName,
                onValueChange = { recipeName = it },
                label = { Text("Nombre de receta") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            )

            Button(onClick = {
                ingredients.add(Ingredient("", ""))
            }, modifier = Modifier.padding(10.dp)) {
                Text("Agregar ingrediente")

            }

            // LISTA DINÁMICA
            ingredients.forEachIndexed { index, ing ->

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
                ) {

                    TextField(
                        value = ing.name,
                        onValueChange = {
                            ingredients[index] = ing.copy(name = it)
                        },
                        modifier = Modifier
                            .weight(1f)
                            .padding(10.dp),
                        label = { Text("Ingrediente") }
                    )

                    TextField(
                        value = ing.quantity,
                        onValueChange = {
                            ingredients[index] = ing.copy(quantity = it)
                        },
                        modifier = Modifier
                            .weight(1f)
                            .padding(5.dp),
                        label = { Text("Cantidad") }
                    )

                    // ELIMINAR
                    IconButton(onClick = {
                        ingredients.removeAt(index)
                    }){
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Eliminar"
                        )
                    }
                }
            }

            Button(
                onClick = {

                    val message = when {
                        recipeName.isBlank() ->
                            "Debes ingresar el nombre de la receta"

                        ingredients.isEmpty() ->
                            "Debes agregar al menos un ingrediente"

                        ingredients.any { it.name.isBlank() || it.quantity.isBlank() } ->
                            "Completa todos los ingredientes"

                        else -> null
                    }

                    if (message == null) {
                        viewModel.addReceta(
                            Recipe(recipeName, ingredients.toMutableList())
                        )
                        recipeName = ""
                        ingredients.clear()
                        onRecipeSave()
                    } else {
                        // ERROR → mostrar mensaje específico
                        errorMessage.launch {
                            mesjEmergente.showSnackbar(message)
                        }
                    }
                }, modifier = Modifier.padding(10.dp)
            ) {
                Text("Guardar receta")
            }
        }
    }
}