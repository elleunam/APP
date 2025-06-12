package com.example.w;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class SupervisorDashboardActivity extends AppCompatActivity {

    private String usernameSupervisor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supervisor_dashboard);

        // Recibir el nombre de usuario del supervisor logueado
        usernameSupervisor = getIntent().getStringExtra("username");

        // Vincular botones con IDs del layout
        LinearLayout btnResumenClientes = findViewById(R.id.btnResumenClientes);
        LinearLayout btnAsignarClientes = findViewById(R.id.btnAsignarClientes);
        LinearLayout btnEquipoVentas = findViewById(R.id.btnEquipoVentas);
        Button btnCerrarSesion = findViewById(R.id.btnCerrarSesion);

        // Navegación hacia Resumen de Clientes
        btnResumenClientes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SupervisorDashboardActivity.this, SupervisorResumenClientesActivity.class);
                intent.putExtra("username", usernameSupervisor);
                startActivity(intent);
            }
        });

        // Navegación hacia Asignar Clientes
        btnAsignarClientes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SupervisorDashboardActivity.this, SupervisorAsignarClientesActivity.class);
                intent.putExtra("username", usernameSupervisor);
                startActivity(intent);
            }
        });

        // Navegación hacia Listado de equipo de ventas
        btnEquipoVentas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SupervisorDashboardActivity.this, EquipoVentasActivity.class);
                intent.putExtra("username", usernameSupervisor);
                startActivity(intent);
            }
        });

        // Cerrar sesión
        btnCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Regresa al login o cierra sesión
            }
        });
    }
}
