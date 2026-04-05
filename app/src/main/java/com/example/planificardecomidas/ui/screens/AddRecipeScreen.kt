package com.example.planificardecomidas.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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

private fun validateRecipe(recipeName: String, ingredients: List<Ingredient>): String? {
    return when {
        recipeName.isBlank() -> "Debes ingresar el nombre de la receta"
        ingredients.isEmpty() -> "Debes agregar al menos un ingrediente"
        ingredients.any { it.name.isBlank() || it.quantity.isBlank() } -> "Completa todos los ingredientes"
        else -> null
    }
}
@Composable
fun AddRecipeScreen(viewModel: RecipeViewModel,
                    onRecipeSave: () -> Unit) {

    var recipeName by remember { mutableStateOf("") }
    val ingredients = remember { mutableStateListOf<Ingredient>() }

    // Snackbar
    val mesjEmergente = remember { SnackbarHostState() }
    val errorMessage = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { SnackbarHost(mesjEmergente) }
    ) { innerPadding ->

        Text(
            "Crear Receta",
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        )

        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
        ) {

            // Campo nombre de receta
            item {
                OutlinedTextField(
                    value = recipeName,
                    onValueChange = { recipeName = it },
                    label = { Text("Nombre de receta") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                )
            }

            // Botón agregar ingrediente
            item {
                Button(
                    onClick = { ingredients.add(Ingredient("", "")) },
                    modifier = Modifier.padding(10.dp)
                ) {
                    Text("Agregar ingrediente")
                }
            }

            // Lista dinámica de ingredientes
            items(ingredients) { ing ->

                val index = ingredients.indexOf(ing)

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = ing.name,
                        onValueChange = {
                            ingredients[index] = ing.copy(name = it)
                        },
                        modifier = Modifier
                            .weight(1f)
                            .padding(10.dp),
                        label = { Text("Ingrediente") }
                    )

                    OutlinedTextField(
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
                    }) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Eliminar"
                        )
                    }
                }
            }

            // Botón guardar receta
            item {
                Button(
                    onClick = {
                        val message = validateRecipe(recipeName, ingredients)

                        if (message == null) {
                            viewModel.addReceta(
                                Recipe(recipeName, ingredients.toMutableList())
                            )
                            recipeName = ""
                            ingredients.clear()
                            onRecipeSave()
                        } else {
                            errorMessage.launch {
                                mesjEmergente.showSnackbar(message)
                            }
                        }
                    },
                    modifier = Modifier.padding(10.dp)
                ) {
                    Text("Guardar receta")
                }
            }
        }
    }
}