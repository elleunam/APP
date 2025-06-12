package com.example.w;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    LinearLayout btnRegistrarAtencion, btnHistorial, btnPerfil, btnVerMisClientes;
    Button btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Vincular vistas con IDs
        btnRegistrarAtencion = findViewById(R.id.btnRegistrarAtencion);
        btnHistorial = findViewById(R.id.btnHistorial);
        btnPerfil = findViewById(R.id.btnPerfil);
        btnVerMisClientes = findViewById(R.id.btnVerMisClientes); // nuevo
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

        TextView badgeClientes = findViewById(R.id.badgeClientes);
        int nuevos = 0;
        for (Cliente c : DataStore.listaClientes) {
            if (c.getAsignadoA().equals(DataStore.usuarioActual) && !c.isVistoPorUsuario()) {

                nuevos++;
            }
        }
        if (nuevos > 0) {
            badgeClientes.setVisibility(View.VISIBLE);
            badgeClientes.setText(String.valueOf(nuevos));
        } else {
            badgeClientes.setVisibility(View.GONE);
        }

        // Ir a Ver Mis Clientes
        btnVerMisClientes.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, MisClientesActivity.class));
        });

        // Cerrar sesión
        btnLogout.setOnClickListener(v -> {
            DataStore.usuarioActual = "";
            DataStore.rolActual = "";
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        TextView badgeClientes = findViewById(R.id.badgeClientes);
        int nuevos = 0;
        for (Cliente c : DataStore.listaClientes) {
            if (c.getAsignadoA().equals(DataStore.usuarioActual) && !c.isVistoPorUsuario()) {
                nuevos++;
            }
        }

        if (nuevos > 0) {
            badgeClientes.setVisibility(View.VISIBLE);
            badgeClientes.setText(String.valueOf(nuevos));
        } else {
            badgeClientes.setVisibility(View.GONE);
        }
    }
}
