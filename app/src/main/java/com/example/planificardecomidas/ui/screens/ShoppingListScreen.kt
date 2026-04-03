package com.example.planificardecomidas.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.planificardecomidas.ViewModels.RecipeViewModel

@Composable
fun ShoppingListScreen(viewModel: RecipeViewModel) {

    val list = viewModel.getShoppingList()

    Column(modifier = Modifier.fillMaxSize()) {

        Text(
            "Lista de Compras",
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .padding(bottom = 10.dp)
        )

        if (list.isEmpty()) {

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No se ha agregado ninguna receta a la semana.",
                    style = MaterialTheme.typography.bodyLarge
                )
            }

        } else {

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
            ) {

                items(list) { ing ->

                    var checkedState by remember { mutableStateOf(ing.checked) }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(vertical = 5.dp)
                    ) {
                        Checkbox(
                            checked = checkedState,
                            onCheckedChange = {
                                checkedState = it
                                ing.checked = it
                            }
                        )

                        Text("${ing.name} (${ing.quantity})")
                    }
                }
            }
        }
    }
}