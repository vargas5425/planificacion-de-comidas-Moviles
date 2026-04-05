package com.example.planificardecomidas.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.style.TextDecoration
import com.example.planificardecomidas.ViewModels.RecipeViewModel

@Composable
fun ShoppingListScreen(viewModel: RecipeViewModel) {

    val list = viewModel.getShoppingList()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {

        Text("Lista de Compras", fontSize = 20.sp,
            modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp))

        if (list.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("No hay recetas asignadas en el plan semanal.",
                    style = MaterialTheme.typography.bodyLarge)
            }
        } else {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(list) { ing ->

                    val isChecked = viewModel.checkedIngredients[ing.name] ?: false

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp)
                    ) {
                        Checkbox(
                            checked = isChecked,
                            onCheckedChange = { viewModel.toggleIngredient(ing.name) }
                        )
                        Text(
                            text = "${ing.name} (${ing.quantity})",
                            textDecoration = if (isChecked) TextDecoration.LineThrough else null,
                            color = if (isChecked) MaterialTheme.colorScheme.outline
                            else MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }
        }
    }
}
