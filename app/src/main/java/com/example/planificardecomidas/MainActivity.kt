package com.example.planificardecomidas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.planificardecomidas.ui.theme.PlanificarDeComidasTheme
import com.example.planificardecomidas.ViewModels.RecipeViewModel
import com.example.planificardecomidas.ui.screens.RecipeListScreen
import com.example.planificardecomidas.ui.screens.AddRecipeScreen
import com.example.planificardecomidas.ui.screens.WeeklyPlanScreen
import com.example.planificardecomidas.ui.screens.ShoppingListScreen


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PlanificarDeComidasTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen() {

    val viewModel = remember { RecipeViewModel() }//remember para que no se cree un nuevo viewmodel

    var screen by remember { mutableStateOf("lista") }

    Scaffold(
        bottomBar = {
            NavigationBar {

                NavigationBarItem(
                    selected = screen == "lista",
                    onClick = { screen = "lista" },
                    label = { Text("Recetas") },
                    icon = {}
                )

                NavigationBarItem(
                    selected = screen == "crear",
                    onClick = { screen = "crear" },
                    label = { Text("Crear") },
                    icon = {}

                )

                NavigationBarItem(
                    selected = screen == "plan",
                    onClick = { screen = "plan" },
                    label = { Text("Plan") },
                    icon = {}
                )

                NavigationBarItem(
                    selected = screen == "compras",
                    onClick = { screen = "compras" },
                    label = { Text("Compras") },
                    icon = {}
                )
            }
        }
    ) { innerPadding ->

        Box(modifier = Modifier.padding(innerPadding)) {

            when (screen) {
                "lista" -> RecipeListScreen(
                    viewModel,
                    onNavigateToCreate = { screen = "crear" }
                )
                "crear" -> AddRecipeScreen(
                    viewModel,
                    onRecipeSave = {screen = "lista"})
                "plan" -> WeeklyPlanScreen(viewModel)
                "compras" -> ShoppingListScreen(viewModel)
            }
        }
    }
}