package com.example.planificardecomidas.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.planificardecomidas.ViewModels.RecipeViewModel
import com.example.planificardecomidas.models.Recipe

@Composable
fun RecipeListScreen(
    viewModel: RecipeViewModel,
    onNavigateToCreate: () -> Unit
) {
    var buscador by remember { mutableStateOf("") }

    val filtrarRecetas = viewModel.recetas.filter { recipe ->
        val matchesName = recipe.name.contains(buscador, ignoreCase = true)
        val matchesIngredient = recipe.ingredients.any {
            it.name.contains(buscador, ignoreCase = true)
        }
        matchesName || matchesIngredient
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { onNavigateToCreate() }) {
                Icon(Icons.Default.Add, contentDescription = "Agregar receta")
            }
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            Text(
                "Menú de Recetas",
                fontSize = 20.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp)
            )

            OutlinedTextField(
                value = buscador,
                onValueChange = { buscador = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                label = { Text("Buscar receta o ingrediente") }
            )

            if (viewModel.recetas.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(20.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Aún no has creado ninguna receta",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            } else if (filtrarRecetas.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(20.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No se encontraron recetas",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            } else {
                LazyColumn {
                    items(filtrarRecetas) { recipe ->
                        RecipeItem(recipe)
                    }
                }
            }
        }
    }
}

@Composable
fun RecipeItem(recipe: Recipe) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(
                text = recipe.name,
                fontSize = 16.sp,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(4.dp))
            recipe.ingredients.forEach {
                Text(text = "• ${it.name} (${it.quantity})")
            }
        }
    }
}