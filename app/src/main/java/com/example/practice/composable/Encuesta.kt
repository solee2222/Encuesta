package com.example.practice.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.RadioButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.example.practice.R

//Seleccion multiple y preguntas
@Composable
fun Encuesta(
    aceptaTerminos: Boolean,
    respuestas: List<String>,
    textos: List<String>,
    onRespuestasChange: (List<String>, List<String>) -> Unit
) {
    val preguntas = listOf(
        R.string.p1, R.string.p2, R.string.p3, R.string.p4, R.string.p5
    )
    val opciones = listOf("Definitivamente no", "Tal vez no", "No estoy seguro", "Tal vez sí", "Definitivamente sí", "Otro")

    Column(
        modifier = Modifier
            .padding(19.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) { // todos los valores dentro de column tendrán ese padding
        preguntas.forEachIndexed { indice, preguntaId ->
            // Preguntas
            Spacer(modifier = Modifier.padding(5.dp))
            Text(
                text = stringResource(id = preguntaId),
                modifier = Modifier.padding(vertical = 8.dp),
                textAlign = TextAlign.Center,
                color = if (aceptaTerminos) androidx.compose.ui.graphics.Color.Black else androidx.compose.ui.graphics.Color.Gray
            )
            opciones.forEach { opcion ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    RadioButton(
                        selected = respuestas[indice] == opcion,
                        onClick = {
                            if (aceptaTerminos) {
                                val nuevasRespuestas = respuestas.toMutableList().also { it[indice] = opcion }
                                onRespuestasChange(nuevasRespuestas, textos)
                            }
                        },
                        enabled = aceptaTerminos
                    )
                    Text(
                        text = opcion,
                        modifier = Modifier.padding(start = 8.dp),
                        color = if (aceptaTerminos) androidx.compose.ui.graphics.Color.Black else androidx.compose.ui.graphics.Color.Gray
                    )
                }
                if (opcion == "Otro" && respuestas[indice] == opcion) {
                    // Campo de texto
                    TextField(
                        value = textos[indice],
                        onValueChange = { nuevoTexto ->
                            if (aceptaTerminos) {
                                val nuevosTextos = textos.toMutableList().also { it[indice] = nuevoTexto }
                                onRespuestasChange(respuestas, nuevosTextos)
                            }
                        },
                        modifier = Modifier
                            .width(380.dp)
                            .padding(start = 16.dp),
                        enabled = aceptaTerminos
                    )
                }
            }
        }
    }
}