package com.example.w;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class FormularioUsuarioActivity extends Activity {

    private EditText etUsername, etPassword;
    private Spinner spRol, spSupervisor;
    private TextView labelSupervisor;
    private Button btnGuardar;

    private Usuario usuarioExistente = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_usuario);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        spRol = findViewById(R.id.spRol);
        spSupervisor = findViewById(R.id.spSupervisor);
        labelSupervisor = findViewById(R.id.labelSupervisor);
        btnGuardar = findViewById(R.id.btnGuardarUsuario);

        // Rellenar roles
        ArrayAdapter<CharSequence> adapterRol = ArrayAdapter.createFromResource(this,
                R.array.roles_array, android.R.layout.simple_spinner_item);
        adapterRol.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spRol.setAdapter(adapterRol);

        // Mostrar/ocultar spinner según rol
        spRol.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(android.widget.AdapterView<?> parent, View view, int position, long id) {
                String rolSeleccionado = spRol.getSelectedItem().toString();
                if (rolSeleccionado.equalsIgnoreCase("ejecutivo")) {
                    mostrarSupervisores();
                } else {
                    spSupervisor.setVisibility(View.GONE);
                    labelSupervisor.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(android.widget.AdapterView<?> parent) {
            }
        });

        // Si viene para editar
        String usernameEditar = getIntent().getStringExtra("usuario_editado");
        if (usernameEditar != null) {
            for (Usuario u : DataStore.listaUsuarios) {
                if (u.username.equals(usernameEditar)) {
                    usuarioExistente = u;
                    break;
                }
            }
            if (usuarioExistente != null) {
                etUsername.setText(usuarioExistente.username);
                etPassword.setText(usuarioExistente.password);
                spRol.setSelection(adapterRol.getPosition(usuarioExistente.rol));

                if (usuarioExistente.rol.equalsIgnoreCase("ejecutivo")) {
                    mostrarSupervisores();
                    spSupervisor.post(() -> {
                        int pos = ((ArrayAdapter<String>) spSupervisor.getAdapter())
                                .getPosition(usuarioExistente.supervisorAsignado);
                        if (pos >= 0) spSupervisor.setSelection(pos);
                    });
                }
            }
        }

        // Guardar
        btnGuardar.setOnClickListener(v -> {
            String username = etUsername.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            String rol = spRol.getSelectedItem().toString();
            String supervisor = spSupervisor.getSelectedItem() != null ? spSupervisor.getSelectedItem().toString() : "";

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }

            if (rol.equalsIgnoreCase("ejecutivo") && supervisor.isEmpty()) {
                Toast.makeText(this, "Debes seleccionar un supervisor", Toast.LENGTH_SHORT).show();
                return;
            }

            if (usuarioExistente != null) {
                usuarioExistente.username = username;
                usuarioExistente.password = password;
                usuarioExistente.rol = rol;
                usuarioExistente.supervisorAsignado = rol.equalsIgnoreCase("ejecutivo") ? supervisor : "";
            } else {
                Usuario nuevo = new Usuario(username, password, rol);
                if (rol.equalsIgnoreCase("ejecutivo")) {
                    nuevo.supervisorAsignado = supervisor;
                }
                DataStore.listaUsuarios.add(nuevo);
            }

            DataStore.guardarUsuarios(this);
            Toast.makeText(this, "Guardado correctamente", Toast.LENGTH_SHORT).show();
            finish();
        });
    }

    private void mostrarSupervisores() {
        List<String> supervisores = new ArrayList<>();
        for (Usuario u : DataStore.listaUsuarios) {
            if (u.rol.equalsIgnoreCase("supervisor")) {
                supervisores.add(u.username);
            }
        }

        ArrayAdapter<String> adapterSup = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, supervisores);
        adapterSup.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spSupervisor.setAdapter(adapterSup);

        labelSupervisor.setVisibility(View.VISIBLE);
        spSupervisor.setVisibility(View.VISIBLE);
    }
}
