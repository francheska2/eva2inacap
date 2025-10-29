package com.example.eva2inacap

import android.graphics.Color
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

class CalderasActivity : AppCompatActivity() {

    private lateinit var tvStatusCalderas: TextView
    private lateinit var tvTemperatureValue: TextView
    private lateinit var tvWaterLevelValue: TextView
    private lateinit var tvGasLevelValue: TextView // 💡 NUEVA VARIABLE para el Nivel de Gas

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calderas)

        // Inicializar vistas
        tvStatusCalderas = findViewById(R.id.tvStatusCalderas)
        tvTemperatureValue = findViewById(R.id.tvTemperatureValue)
        tvWaterLevelValue = findViewById(R.id.tvWaterLevelValue)
        tvGasLevelValue = findViewById(R.id.tvGasLevelValue)

        // simular sensor
        simulateSensorReading()
    }

    private fun simulateSensorReading() {
        // Generador random de indicadores de sensor
        val temperature = Random.nextInt(70, 120) // Temperatura: 70°C a 120°C
        val waterLevel = Random.nextInt(10, 100)  // Nivel de Agua: 10% a 100%
        val gasLevel = Random.nextInt(0, 10)     // Simulando unidad de riesgo 1 a 10

        // Mostramos los valores numéricos
        tvTemperatureValue.text = "$temperature °C"
        tvWaterLevelValue.text = "$waterLevel %"
        tvGasLevelValue.text = "$gasLevel unidades" // 💡 Mostrar el valor del gas

        // 💡 Lógica de la Escala de Riesgo (Rojo, Amarillo, Verde)
        when {
            // ROJO: Riesgo Alto (Temperatura muy alta O Nivel de agua muy bajo O ALERTA DE GAS)
            temperature > 110 || waterLevel < 20 || gasLevel > 7 -> {
                tvStatusCalderas.text = "🔴 ALERTA: Falla Crítica o Fuga de Gas"
                tvStatusCalderas.setBackgroundColor(Color.RED)
                tvStatusCalderas.setTextColor(Color.WHITE)
            }
            // AMARILLO: Riesgo Moderado (Temperatura elevada O Nivel de agua bajo O Gas elevado)
            temperature > 100 || waterLevel < 40 || gasLevel > 4 -> {
                tvStatusCalderas.text = "🟡 ADVERTENCIA: Revisar Parámetros y Nivel de Gas"
                tvStatusCalderas.setBackgroundColor(Color.YELLOW)
                tvStatusCalderas.setTextColor(Color.BLACK)
            }
            // VERDE: Estado Normal
            else -> {
                tvStatusCalderas.text = "🟢 ÓPTIMO: Funcionamiento Normal"
                tvStatusCalderas.setBackgroundColor(Color.GREEN)
                tvStatusCalderas.setTextColor(Color.BLACK)
            }
        }
    }
}