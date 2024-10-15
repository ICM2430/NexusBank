package com.example.snaphunters

import android.content.Context
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import java.util.concurrent.Executor

class FingerprintHelper(private val activity: AppCompatActivity) {

    private val executor: Executor = ContextCompat.getMainExecutor(activity)
    private val biometricPrompt: BiometricPrompt
    private val promptInfo: BiometricPrompt.PromptInfo

    // Interfaz para manejar la autenticación
    interface AuthenticationCallback {
        fun onAuthenticationSuccess()
        fun onAuthenticationError(errorMessage: String)
    }

    private var callback: AuthenticationCallback? = null

    init {
        biometricPrompt = BiometricPrompt(activity, executor, object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationError(errMsgId: Int, errString: CharSequence) {
                super.onAuthenticationError(errMsgId, errString)
                callback?.onAuthenticationError(errString.toString())
                showToast("Error de autenticación: $errString")
            }

            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                showToast("¡Autenticación exitosa!")
                callback?.onAuthenticationSuccess() // Llama a la interfaz
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                callback?.onAuthenticationError("Autenticación fallida")
                showToast("Autenticación fallida")
            }
        })

        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Iniciar sesión")
            .setSubtitle("Autentíquese usando su huella digital")
            .setNegativeButtonText("Cancelar")
            .build()
    }

    fun authenticate(callback: AuthenticationCallback) {
        this.callback = callback // Establecer el callback
        biometricPrompt.authenticate(promptInfo)
    }

    private fun showToast(message: String) {
        activity.runOnUiThread {
            Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
        }
    }
}
