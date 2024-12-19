package com.example.verifybiometric

import android.content.Intent
import android.hardware.biometrics.BiometricPrompt
import android.os.Build
import android.os.Bundle
import android.os.CancellationSignal
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import com.example.verifybiometric.ui.theme.VerifyBiometricTheme
import java.util.concurrent.Executor

class BiometricAuthActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            val executor: Executor = ContextCompat.getMainExecutor(this)

            val biometricPrompt = BiometricPrompt.Builder(this)
                .setTitle("Biometric Authentication")
                .setSubtitle("Authenticate to access the app")
                .setDescription("Use your fingerprint or face to continue")
                .setNegativeButton(
                    "Cancel",
                    executor,
                    { _, _ -> Toast.makeText(this, "Authentication canceled", Toast.LENGTH_SHORT).show() }
                ).build()

            biometricPrompt.authenticate(
                CancellationSignal(),
                executor,
                object : BiometricPrompt.AuthenticationCallback() {
                    override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                        super.onAuthenticationSucceeded(result)
                        startActivity(Intent(this@BiometricAuthActivity, MainActivity::class.java))
                        finish()
                    }

                    override fun onAuthenticationFailed() {
                        super.onAuthenticationFailed()
                        Toast.makeText(this@BiometricAuthActivity, "Authentication failed", Toast.LENGTH_SHORT).show()
                    }

                    override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                        super.onAuthenticationError(errorCode, errString)
                        Toast.makeText(this@BiometricAuthActivity, "Authentication error: $errString", Toast.LENGTH_SHORT).show()
                    }
                }
            )
        } else {
            Toast.makeText(this, "Biometric authentication not supported on this device", Toast.LENGTH_SHORT).show()
        }
        enableEdgeToEdge()
        setContent {
            VerifyBiometricTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                 Box {

                 }
                }
            }
        }
    }
}
