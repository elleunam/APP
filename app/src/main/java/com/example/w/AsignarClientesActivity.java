package com.example.w;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AsignarClientesActivity extends Activity {

    private Spinner spinnerUsuarios;
    private EditText etNombreCliente;
    private Button btnAsignar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asignar_clientes);

        spinnerUsuarios = findViewById(R.id.spinnerUsuarios);
        etNombreCliente = findViewById(R.id.etNombreCliente);
        btnAsignar = findViewById(R.id.btnAsignar);

        // Llenar el spinner solo con ejecutivos
        List<String> usuariosEjecutivos = new ArrayList<>();
        for (Usuario u : DataStore.listaUsuarios) {
            if ("ejecutivo".equalsIgnoreCase(u.getRol())) {
                usuariosEjecutivos.add(u.getUsername());
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, usuariosEjecutivos);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerUsuarios.setAdapter(adapter);

        btnAsignar.setOnClickListener(v -> {
            String nombreCliente = etNombreCliente.getText().toString().trim();
            String usuarioSeleccionado = (String) spinnerUsuarios.getSelectedItem();

            if (nombreCliente.isEmpty()) {
                Toast.makeText(this, "Por favor, ingresa el nombre del cliente", Toast.LENGTH_SHORT).show();
                return;
            }

            // Verificar si ya existe un cliente con el mismo nombre
            for (Cliente c : DataStore.listaClientes) {
                if (c.getNombreCliente().equalsIgnoreCase(nombreCliente)) {
                    Toast.makeText(this, "Este cliente ya ha sido asignado", Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            // Crear y guardar cliente con fecha de asignación
            Cliente nuevo = new Cliente(nombreCliente, usuarioSeleccionado);
            nuevo.setFechaAsignacion(obtenerFechaActual());
            DataStore.listaClientes.add(nuevo);
            DataStore.guardarClientes(getApplicationContext());

            Toast.makeText(this, "Cliente asignado correctamente", Toast.LENGTH_SHORT).show();
            etNombreCliente.setText("");
        });
    }

    // Método para obtener la fecha actual formateada
    private String obtenerFechaActual() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
        return sdf.format(new Date());
    }
}
