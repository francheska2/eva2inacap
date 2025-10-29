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

class CocinaActivity : AppCompatActivity() {

    private lateinit var tvStatusGas: TextView
    private lateinit var tvPressureValue: TextView
    private lateinit var tvGasLevelValue: TextView
    private lateinit var tvPressureLabel: TextView
    private lateinit var tvGasLevelLabel: TextView
    private lateinit var separator: View
    private lateinit var switchSensor: Switch

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
        setContentView(R.layout.activity_cocina)

        tvStatusGas = findViewById(R.id.tvStatusGas)
        tvPressureValue = findViewById(R.id.tvPressureValue)
        tvGasLevelValue = findViewById(R.id.tvGasLevelValue)
        tvPressureLabel = findViewById(R.id.tvPressureLabel)
        tvGasLevelLabel = findViewById(R.id.tvGasLevelLabel)
        separator = findViewById(R.id.separator)
        switchSensor = findViewById(R.id.switchSensor)

        switchSensor.isChecked = isSensorActive

        switchSensor.setOnCheckedChangeListener { _, isChecked ->
            isSensorActive = isChecked
            updateUIState(isChecked)
        }

        startSensorUpdates()
    }

    private fun startSensorUpdates() {
        handler.post(sensorRunnable)
    }

    private fun stopSensorUpdates() {
        handler.removeCallbacks(sensorRunnable)
    }

    private fun updateUIState(active: Boolean) {
        if (active) {
            tvStatusGas.text = "Cargando Estado..."
            tvStatusGas.setBackgroundColor(Color.parseColor("#88000000"))
            tvStatusGas.setTextColor(Color.WHITE)
            tvPressureLabel.visibility = View.VISIBLE
            tvPressureValue.visibility = View.VISIBLE
            tvGasLevelLabel.visibility = View.VISIBLE
            tvGasLevelValue.visibility = View.VISIBLE
            separator.visibility = View.VISIBLE
            startSensorUpdates()
        } else {
            tvStatusGas.text = "🛑 SENSOR DESACTIVADO 🛑\nNo hay lectura de datos."
            tvStatusGas.setBackgroundColor(Color.DKGRAY)
            tvStatusGas.setTextColor(Color.WHITE)

            tvPressureLabel.visibility = View.GONE
            tvPressureValue.visibility = View.GONE
            tvGasLevelLabel.visibility = View.GONE
            tvGasLevelValue.visibility = View.GONE
            separator.visibility = View.GONE

            tvPressureValue.text = "-- kPa"
            tvGasLevelValue.text = "-- ppm"

            stopSensorUpdates()
        }
    }

    private fun simulateSensorReading() {
        if (!isSensorActive) return

        val pressure = Random.nextInt(10, 40)
        val gasLevel = Random.nextInt(0, 100)

        tvPressureValue.text = "$pressure kPa"
        tvGasLevelValue.text = "$gasLevel ppm"

        when {
            pressure > 35 || gasLevel > 60 -> {
                tvStatusGas.text = "🔴 ALERTA: Fuga O Presión Anormal"
                tvStatusGas.setBackgroundColor(Color.RED)
                tvStatusGas.setTextColor(Color.WHITE)
            }
            pressure > 30 || gasLevel > 30 -> {
                tvStatusGas.text = "🟡 ADVERTENCIA: Nivel de Gas Elevado"
                tvStatusGas.setBackgroundColor(Color.YELLOW)
                tvStatusGas.setTextColor(Color.BLACK)
            }
            else -> {
                tvStatusGas.text = "🟢 SEGURO: Parámetros Normales"
                tvStatusGas.setBackgroundColor(Color.GREEN)
                tvStatusGas.setTextColor(Color.BLACK)
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
