package com.example.w;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import java.util.ArrayList;
import java.util.List;

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
            if ("ejecutivo".equals(u.rol)) {
                usuariosEjecutivos.add(u.username);
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

            // 🔍 Verificar si ya existe un cliente con el mismo nombre
            for (Cliente c : DataStore.listaClientes) {
                if (c.nombreCliente.equalsIgnoreCase(nombreCliente)) {
                    Toast.makeText(this, "Este cliente ya ha sido asignado", Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            // ✅ Crear y guardar cliente
            Cliente nuevo = new Cliente(nombreCliente, usuarioSeleccionado);
            DataStore.listaClientes.add(nuevo);
            DataStore.guardarClientes(getApplicationContext());

            Toast.makeText(this, "Cliente asignado correctamente", Toast.LENGTH_SHORT).show();
            etNombreCliente.setText("");
        });
    }
}
