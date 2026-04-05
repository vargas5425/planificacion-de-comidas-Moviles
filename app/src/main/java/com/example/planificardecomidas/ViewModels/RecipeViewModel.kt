package com.example.planificardecomidas.ViewModels

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.ViewModel
import com.example.planificardecomidas.models.*

class RecipeViewModel : ViewModel() {

    var recetas = mutableStateListOf<Recipe>()//crea una lista observable para almacenar las recetas
    private set

    var planSemanal = mutableStateMapOf(
        "Lunes" to null as Recipe?,
        "Martes" to null,
        "Miércoles" to null,
        "Jueves"    to null,
        "Viernes"   to null,
        "Sábado"    to null,
        "Domingo"   to null
    )
        private set

    val checkedIngredients = mutableStateMapOf<String, Boolean>()

    fun addReceta(recipe: Recipe) {
        recetas.add(recipe)
    }

    fun asignarReceta(day: String, receta: Recipe?) {
        planSemanal[day] = receta
    }

    fun toggleIngredient(name: String) {
        checkedIngredients[name] = !(checkedIngredients[name] ?: false)
    }

    fun getShoppingList(): List<Ingredient> {

        val cantidades = mutableMapOf<String, Int>()//crea cantidad = {clave, valor}
        val unidades = mutableMapOf<String, String>()

        planSemanal.values.filterNotNull()// elimina los null
            .forEach { receta ->// itera cada receta para obtebner los ingredientes
                receta.ingredients.forEach { ingrediente ->//recorre cada ingrediente de la receta
                    val nombre = ingrediente.name
                    val numero = ingrediente.quantity.filter { it.isDigit() }.toIntOrNull() ?: 0//toma solo el numero y convierte a INT
                    val unidad = ingrediente.quantity.filter { !it.isDigit() }.trim()// toma lo que no es numero y elimina los espacios

                    cantidades[nombre] = (cantidades[nombre] ?: 0) + numero// aqui usa si 0 si es que el nombre aparece la primera vez despues si aparece otra ves toma el valor de la ultima cantidad
                    unidades[nombre] = unidad
                }
            }

        return cantidades.map { (nombre, cantidad) ->//convierte los mapas en lista de ingredientes
            Ingredient(
                name = nombre,
                quantity = "$cantidad ${unidades[nombre]}".trim()
            )
        }
    }
}