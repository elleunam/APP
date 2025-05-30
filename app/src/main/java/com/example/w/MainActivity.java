package com.example.w;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    LinearLayout btnRegistrarAtencion, btnHistorial, btnPerfil;
    Button btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Vincular vistas con IDs
        btnRegistrarAtencion = findViewById(R.id.btnRegistrarAtencion);
        btnHistorial = findViewById(R.id.btnHistorial);
        btnPerfil = findViewById(R.id.btnPerfil);
        btnLogout = findViewById(R.id.btnLogout);

        // Ir a Registro de Atención
        btnRegistrarAtencion.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, RegistroAtencionActivity.class));
        });

        // Ir a Historial
        btnHistorial.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, HistorialActivity.class));
        });

        // Ir a Perfil
        btnPerfil.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, PerfilActivity.class));
        });

        // Cerrar sesión
        btnLogout.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });
    }
}
