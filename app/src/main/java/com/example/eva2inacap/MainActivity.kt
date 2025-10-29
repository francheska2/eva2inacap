package com.example.eva2inacap

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
// Importaciones de ViewCompat/WindowInsetsCompat/enableEdgeToEdge se mantienen si las necesitas
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    // 1. Declarar las propiedades para los elementos de la UI
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button

    // 2. Definir credenciales y Activity de destino
    private companion object {
        const val VALID_EMAIL = "yamilett"
        const val VALID_PASSWORD = "ttelimay"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Mantenemos tu configuración de EdgeToEdge
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // 3. Inicializar (obtener referencias) de los elementos de la UI
        emailEditText = findViewById(R.id.editTextuser)
        passwordEditText = findViewById(R.id.editTextPassword)
        loginButton = findViewById(R.id.buttonLogin)

        // 4. Configurar el Listener para el botón de login
        loginButton.setOnClickListener {
            attemptLogin()
        }

        // Mantenemos la lógica de ViewCompat si la necesitas para tu diseño
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    /**
     * Lógica para intentar iniciar sesión.
     */
    private fun attemptLogin() {
        // Obtener el texto del email y la contraseña, y limpiar espacios
        val email = emailEditText.text.toString().trim()
        val password = passwordEditText.text.toString().trim()

        // 1. Validación de campos vacíos
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Por favor, ingresa tu email y contraseña.", Toast.LENGTH_SHORT).show()
            return // Detiene la función
        }

        // 2. Comparación de credenciales
        // Usamos '==' que en Kotlin compara el contenido de las Strings
        if (email == VALID_EMAIL && password == VALID_PASSWORD) {
            // Éxito en el inicio de sesión
            Toast.makeText(this, "¡Bienvenido! Iniciando sesión...", Toast.LENGTH_LONG).show()

            // 3. Navegación a la siguiente Activity (debes crearla)
            val intent = Intent(this, MainActivity2::class.java) // ⬅️ Cambia HomeActivity por el nombre de tu Activity de destino
            startActivity(intent)
            finish() // Opcional: Cierra MainActivity para que el usuario no pueda volver al login con el botón 'Atrás'

        } else {
            // Error en las credenciales
            Toast.makeText(this, "Email o contraseña incorrectos. Intenta de nuevo.", Toast.LENGTH_LONG).show()
        }
    }
}