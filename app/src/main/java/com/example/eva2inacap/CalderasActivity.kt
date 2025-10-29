package com.example.eva2inacap

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

class CalderasActivity : AppCompatActivity() {

    private lateinit var tvStatusCalderas: TextView
    private lateinit var tvTemperatureValue: TextView
    private lateinit var tvWaterLevelValue: TextView
    private lateinit var tvGasLevelValue: TextView
    private lateinit var tvTemperatureLabel: TextView
    private lateinit var tvWaterLevelLabel: TextView
    private lateinit var tvGasLevelLabel: TextView
    private lateinit var switchSensor: Switch
    private lateinit var separator1: View
    private lateinit var separator2: View

    private val handler = Handler(Looper.getMainLooper())
    private val sensorRunnable = object : Runnable {
        override fun run() {
            simulateSensorReading()
            handler.postDelayed(this, 3000)
        }
    }

    private var isSensorActive = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calderas)

        tvStatusCalderas = findViewById(R.id.tvStatusCalderas)
        tvTemperatureValue = findViewById(R.id.tvTemperatureValue)
        tvWaterLevelValue = findViewById(R.id.tvWaterLevelValue)
        tvGasLevelValue = findViewById(R.id.tvGasLevelValue)
        tvTemperatureLabel = findViewById(R.id.tvTemperatureLabel)
        tvWaterLevelLabel = findViewById(R.id.tvWaterLevelLabel)
        tvGasLevelLabel = findViewById(R.id.tvGasLevelLabel)
        switchSensor = findViewById(R.id.switchSensor)
        separator1 = findViewById(R.id.separator1)
        separator2 = findViewById(R.id.separator2)

        switchSensor.isChecked = isSensorActive

        switchSensor.setOnCheckedChangeListener { _, isChecked ->
            isSensorActive = isChecked
            updateUIState(isChecked)
        }

        updateUIState(isSensorActive)
    }

    private fun startSensorUpdates() {
        handler.post(sensorRunnable)
    }

    private fun stopSensorUpdates() {
        handler.removeCallbacks(sensorRunnable)
    }

    private fun updateUIState(active: Boolean) {
        if (active) {
            tvStatusCalderas.text = "Cargando Estado..."
            tvStatusCalderas.setBackgroundColor(Color.parseColor("#88000000"))
            tvStatusCalderas.setTextColor(Color.WHITE)

            tvTemperatureLabel.visibility = View.VISIBLE
            tvTemperatureValue.visibility = View.VISIBLE
            tvWaterLevelLabel.visibility = View.VISIBLE
            tvWaterLevelValue.visibility = View.VISIBLE
            tvGasLevelLabel.visibility = View.VISIBLE
            tvGasLevelValue.visibility = View.VISIBLE
            separator1.visibility = View.VISIBLE
            separator2.visibility = View.VISIBLE

            startSensorUpdates()
        } else {
            tvStatusCalderas.text = "🛑 SENSOR DESACTIVADO 🛑\nMonitoreo de Calderas Inactivo."
            tvStatusCalderas.setBackgroundColor(Color.DKGRAY)
            tvStatusCalderas.setTextColor(Color.WHITE)

            tvTemperatureLabel.visibility = View.GONE
            tvTemperatureValue.visibility = View.GONE
            tvWaterLevelLabel.visibility = View.GONE
            tvWaterLevelValue.visibility = View.GONE
            tvGasLevelLabel.visibility = View.GONE
            tvGasLevelValue.visibility = View.GONE
            separator1.visibility = View.GONE
            separator2.visibility = View.GONE

            tvTemperatureValue.text = "-- °C"
            tvWaterLevelValue.text = "-- %"
            tvGasLevelValue.text = "-- unidades"

            stopSensorUpdates()
        }
    }

    private fun simulateSensorReading() {
        if (!isSensorActive) return

        val temperature = Random.nextInt(70, 120)
        val waterLevel = Random.nextInt(10, 100)
        val gasLevel = Random.nextInt(0, 10)

        tvTemperatureValue.text = "$temperature °C"
        tvWaterLevelValue.text = "$waterLevel %"
        tvGasLevelValue.text = "$gasLevel unidades"

        when {
            temperature > 110 || waterLevel < 20 || gasLevel > 7 -> {
                tvStatusCalderas.text = "🔴 ALERTA: Falla Crítica o Fuga de Gas"
                tvStatusCalderas.setBackgroundColor(Color.RED)
                tvStatusCalderas.setTextColor(Color.WHITE)
            }
            temperature > 100 || waterLevel < 40 || gasLevel > 4 -> {
                tvStatusCalderas.text = "🟡 ADVERTENCIA: Revisar Parámetros y Nivel de Gas"
                tvStatusCalderas.setBackgroundColor(Color.YELLOW)
                tvStatusCalderas.setTextColor(Color.BLACK)
            }
            else -> {
                tvStatusCalderas.text = "🟢 ÓPTIMO: Funcionamiento Normal"
                tvStatusCalderas.setBackgroundColor(Color.GREEN)
                tvStatusCalderas.setTextColor(Color.BLACK)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (isSensorActive) {
            startSensorUpdates()
        }
    }

    override fun onPause() {
        super.onPause()
        stopSensorUpdates()
    }
}