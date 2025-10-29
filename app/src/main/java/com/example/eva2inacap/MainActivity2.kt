package com.example.eva2inacap

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Switch
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity2 : AppCompatActivity() {

    private lateinit var switchBluetooth: Switch
    private lateinit var listViewDevices: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Asegúrate de que el layout es R.layout.activity_main2
        setContentView(R.layout.activity_main2)

        // 1. Inicializar vistas
        switchBluetooth = findViewById(R.id.switchBluetooth)
        listViewDevices = findViewById(R.id.listViewDevices)

        // 2. Definir la lista ESTÁTICA de los dos dispositivos
        val staticDevices = arrayOf(
            "Sensor Calderas",
            "Sensor Cocina"
        )

        // 3. Configurar el ArrayAdapter
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1, // Layout simple para cada item en la lista
            staticDevices
        )

        // 4. Asignar el adaptador al ListView
        listViewDevices.adapter = adapter

        // 5. Manejar el cambio de estado del Switch (Simulación de Bluetooth)
        switchBluetooth.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                // Bluetooth ACTIVADO
                Toast.makeText(this, "Bluetooth ACTIVADO. Sensores visibles.", Toast.LENGTH_SHORT).show()
                listViewDevices.isEnabled = true
                listViewDevices.alpha = 1.0f // Visible y activo
            } else {
                // Bluetooth DESACTIVADO
                Toast.makeText(this, "Bluetooth DESACTIVADO. Sensores ocultos.", Toast.LENGTH_SHORT).show()
                listViewDevices.isEnabled = false
                listViewDevices.alpha = 0.5f // Desactivado y atenuado
            }
        }

        // Inicializar el estado de la lista al inicio (desactivada por defecto)
        switchBluetooth.isChecked = false
        listViewDevices.isEnabled = false
        listViewDevices.alpha = 0.5f

        // 6. Manejar la selección de un dispositivo y realizar el cambio de Activity
        listViewDevices.setOnItemClickListener { parent, view, position, id ->
            if (switchBluetooth.isChecked) {
                val selectedDevice = parent.getItemAtPosition(position) as String

                // Usamos 'when' para decidir a qué Activity navegar basándonos en el nombre
                val intent = when (selectedDevice) {
                    "Sensor Calderas" -> {
                        Toast.makeText(this, "Conectando a Sensor Calderas...", Toast.LENGTH_SHORT).show()
                        // Navega a la Activity de Calderas
                        Intent(this, CalderasActivity::class.java)
                    }
                    "Sensor Cocina" -> {
                        Toast.makeText(this, "Conectando a Sensor Cocina...", Toast.LENGTH_SHORT).show()
                        // Navega a la Activity de Cocina
                        Intent(this, CocinaActivity::class.java)
                    }
                    else -> null
                }

                // Iniciamos la nueva Activity
                intent?.let {
                    startActivity(it)
                }

            } else {
                Toast.makeText(this, "Debe activar Bluetooth primero para conectar.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}