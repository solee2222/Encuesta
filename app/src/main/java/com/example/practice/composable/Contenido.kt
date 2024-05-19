package com.example.practice.composable

import android.widget.Toast
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.practice.R

//Estructura de app
@Composable
fun Contenido() {
    val context = LocalContext.current //al usar mensajes Toast se debe conocer el contexto
    var aceptaTerminos by remember { mutableStateOf(false) } //
    var respuestas by remember { mutableStateOf(List(5) { "" }) }
    var textos by remember { mutableStateOf(List(5) { "" }) }

    LazyColumn(
        // Parte inicial
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 96.dp, bottom = 40.dp)
    ) {
        item {
            Text(text = "Encuesta de Aceptación", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            // Espacio del checkbox
            Spacer(modifier = Modifier.height(20.dp))

            // guarda la respuesta anterior si lo desmarcamos
            //CheckBox(aceptaTerminos) { aceptaTerminos = it }

            //para que este null al desmarcarlo
            CheckBox(aceptaTerminos) {
                aceptaTerminos = it
                if (!aceptaTerminos) {
                    respuestas = List(5) { "" }
                    textos = List(5) { "" }
                }
            }

            // Espacio para preguntas
            Spacer(modifier = Modifier.height(16.dp))
            Encuesta(aceptaTerminos, respuestas, textos) { nuevasRespuestas, nuevosTextos ->
                respuestas = nuevasRespuestas
                textos = nuevosTextos
            }

            // Espacio final de las preguntas
            Spacer(modifier = Modifier.height(16.dp))

            // Botón Enviar
            Button(
                onClick = {
                    if (aceptaTerminos) {
                        val allAnswersValid = respuestas.all { it.isNotEmpty() }
                        if (allAnswersValid) {
                            Toast.makeText(context, "Información enviada", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(
                                context,
                                "Debe responder todas las preguntas para enviar",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        Toast.makeText(context, "Debe aceptar los términos", Toast.LENGTH_SHORT).show()
                    }
                }
            ) {
                Text(text = stringResource(id = R.string.botonEnviar))
            }
        }
    }
}