package com.example.eva2inacap

import android.graphics.Color
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

class CocinaActivity : AppCompatActivity() {

    private lateinit var tvStatusGas: TextView
    private lateinit var tvPressureValue: TextView
    private lateinit var tvGasLevelValue: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cocina)

        // Inicializar vistas
        tvStatusGas = findViewById(R.id.tvStatusGas)
        tvPressureValue = findViewById(R.id.tvPressureValue)
        tvGasLevelValue = findViewById(R.id.tvGasLevelValue)

        // 💡 Simulación: Llamar a la función que simula la lectura del sensor
        simulateSensorReading()
    }

    // Función para simular la lectura de un sensor y actualizar la UI
    private fun simulateSensorReading() {
        // Generamos valores aleatorios para simular la lectura del sensor
        val pressure = Random.nextInt(10, 40) // Simulación de Presión: 10 a 40 kPa
        val gasLevel = Random.nextInt(0, 100)  // Simulación de Nivel de Gas: 0 a 100 ppm

        // Mostramos los valores numéricos
        tvPressureValue.text = "$pressure kPa"
        tvGasLevelValue.text = "$gasLevel ppm"

        // 💡 Lógica de la Escala de Riesgo (Rojo, Amarillo, Verde)
        when {
            // ROJO: Riesgo Alto (Presión alta O Nivel de Gas peligroso)
            pressure > 35 || gasLevel > 60 -> {
                tvStatusGas.text = "🔴 ALERTA: Fuga O Presión Anormal"
                tvStatusGas.setBackgroundColor(Color.RED)
                tvStatusGas.setTextColor(Color.WHITE)
            }
            // AMARILLO: Riesgo Moderado (Presión ligeramente alta O Nivel de Gas elevado)
            pressure > 30 || gasLevel > 30 -> {
                tvStatusGas.text = "🟡 ADVERTENCIA: Nivel de Gas Elevado"
                tvStatusGas.setBackgroundColor(Color.YELLOW)
                tvStatusGas.setTextColor(Color.BLACK)
            }
            // VERDE: Estado Normal
            else -> {
                tvStatusGas.text = "🟢 SEGURO: Parámetros Normales"
                tvStatusGas.setBackgroundColor(Color.GREEN)
                tvStatusGas.setTextColor(Color.BLACK)
            }
        }
    }
}