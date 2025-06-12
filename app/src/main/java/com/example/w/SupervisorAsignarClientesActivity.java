package com.example.w;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class SupervisorAsignarClientesActivity extends AppCompatActivity {

    private EditText etNombreCliente;
    private Spinner spEjecutivos;
    private Button btnAsignar;
    private String usernameSupervisor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supervisor_asignar_clientes);

        etNombreCliente = findViewById(R.id.etNombreCliente);  // ← CAMBIO
        spEjecutivos = findViewById(R.id.spEjecutivosAsignables);
        btnAsignar = findViewById(R.id.btnAsignarCliente);
        usernameSupervisor = getIntent().getStringExtra("username");

        List<Usuario> usuarios = DataStore.getListaUsuarios(this);

        List<String> ejecutivos = new ArrayList<>();
        for (Usuario u : usuarios) {
            if ("ejecutivo".equalsIgnoreCase(u.getRol()) && usernameSupervisor.equals(u.getSupervisorAsignado())) {
                ejecutivos.add(u.getUsername());
            }
        }

        spEjecutivos.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, ejecutivos));

        btnAsignar.setOnClickListener(v -> {
            String nombreCliente = etNombreCliente.getText().toString().trim();
            String ejecutivoSeleccionado = spEjecutivos.getSelectedItem().toString();

            if (nombreCliente.isEmpty()) {
                Toast.makeText(this, "Ingresa el nombre del cliente", Toast.LENGTH_SHORT).show();
                return;
            }

            if (ejecutivoSeleccionado.isEmpty()) {
                Toast.makeText(this, "Selecciona un ejecutivo", Toast.LENGTH_SHORT).show();
                return;
            }

            // Verificar si ya existe
            boolean yaExiste = false;
            for (Cliente c : DataStore.listaClientes) {
                if (c.getNombreCliente().equalsIgnoreCase(nombreCliente)) {
                    yaExiste = true;
                    break;
                }
            }

            if (yaExiste) {
                Toast.makeText(this, "El cliente ya existe. Usa otra pantalla para reasignarlo.", Toast.LENGTH_LONG).show();
            } else {
                Cliente nuevo = new Cliente(nombreCliente, ejecutivoSeleccionado);
                nuevo.setFechaAsignacion(DataStore.obtenerFechaHoraActual());
                DataStore.listaClientes.add(nuevo);
                DataStore.guardarClientes(this);
                Toast.makeText(this, "Cliente asignado correctamente", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}
