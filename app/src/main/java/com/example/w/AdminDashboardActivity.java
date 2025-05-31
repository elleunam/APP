package com.example.w;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;

public class AdminDashboardActivity extends Activity {

    LinearLayout btnVerUsuarios, btnVerHistorial, btnAsignarClientes;
    Button btnCerrarSesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        btnVerUsuarios = findViewById(R.id.btnVerUsuarios);
        btnVerHistorial = findViewById(R.id.btnVerHistorial);
        btnAsignarClientes = findViewById(R.id.btnAsignarClientes);
        btnCerrarSesion = findViewById(R.id.btnCerrarSesion);

        btnVerUsuarios.setOnClickListener(v ->
                startActivity(new Intent(this, VerUsuariosActivity.class)));

        // 🔁 CAMBIO AQUÍ: va al historial del admin
        btnVerHistorial.setOnClickListener(v ->
                startActivity(new Intent(this, AdminHistorialActivity.class)));

        btnAsignarClientes.setOnClickListener(v ->
                startActivity(new Intent(this, AsignarClientesActivity.class)));

        btnCerrarSesion.setOnClickListener(v -> {
            DataStore.usuarioActual = "";
            DataStore.rolActual = "";
            Intent intent = new Intent(AdminDashboardActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });
    }
}
