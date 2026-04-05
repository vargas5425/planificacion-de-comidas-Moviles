package com.example.planificardecomidas.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.planificardecomidas.ViewModels.RecipeViewModel
import com.example.planificardecomidas.models.*
import kotlinx.coroutines.launch

@Composable
fun AddRecipeScreen(
    viewModel: RecipeViewModel,
    onRecipeSave: () -> Unit
) {
    var recipeName by remember { mutableStateOf("") }
    val ingredients = remember { mutableStateListOf<Ingredient>() }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                "Crear Receta",
                fontSize = 20.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp)
            )

            OutlinedTextField(
                value = recipeName,
                onValueChange = { recipeName = it },
                label = { Text("Nombre de receta") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )

            Button(
                onClick = { ingredients.add(Ingredient("", "")) },
                modifier = Modifier.padding(bottom = 8.dp)
            ) {
                Text("+ Agregar ingrediente")
            }

            ingredients.forEachIndexed { index, ing ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = ing.name,
                        onValueChange = { ingredients[index] = ing.copy(name = it) },
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 4.dp),
                        label = { Text("Ingrediente") }
                    )
                    OutlinedTextField(
                        value = ing.quantity,
                        onValueChange = { ingredients[index] = ing.copy(quantity = it) },
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 4.dp),
                        label = { Text("Cantidad") }
                    )
                    IconButton(onClick = { ingredients.removeAt(index) }) {
                        Icon(Icons.Default.Delete, contentDescription = "Eliminar")
                    }
                }
                Spacer(modifier = Modifier.height(4.dp))
            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = {
                    val message = when {
                        recipeName.isBlank() ->
                            "Debes ingresar el nombre de la receta"
                        ingredients.isEmpty() ->
                            "Debes agregar al menos un ingrediente"
                        ingredients.any { it.name.isBlank() || it.quantity.isBlank() } ->
                            "Completa todos los campos de ingredientes"
                        else -> null
                    }
                    if (message == null) {
                        viewModel.addReceta(Recipe(recipeName, ingredients.toMutableList()))
                        recipeName = ""
                        ingredients.clear()
                        onRecipeSave()
                    } else {
                        scope.launch { snackbarHostState.showSnackbar(message) }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Guardar receta")
            }
        }
    }
}