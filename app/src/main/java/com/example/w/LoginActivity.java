package com.example.w;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText emailInput, passwordInput;
    private Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // ✅ Cargar usuarios desde JSON al iniciar
        DataStore.cargarUsuarios(this);

        // Vincular vistas
        emailInput = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.passwordInput);
        loginBtn = findViewById(R.id.loginBtn);

        // Botón de login
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailInput.getText().toString().trim();
                String password = passwordInput.getText().toString().trim();

                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Por favor completa todos los campos", Toast.LENGTH_SHORT).show();
                } else {
                    boolean encontrado = false;
                    for (Usuario u : DataStore.listaUsuarios) {
                        if (u.username.equals(email) && u.password.equals(password)) {
                            encontrado = true;
                            DataStore.usuarioActual = u.username;
                            DataStore.rolActual = u.rol;

                            // ✅ Cargar clientes desde archivo JSON
                            DataStore.cargarClientes(getApplicationContext());


                            Toast.makeText(LoginActivity.this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();

                            if (u.rol.equalsIgnoreCase("administrador")) {
                                startActivity(new Intent(LoginActivity.this, AdminDashboardActivity.class));
                            } else if (u.rol.equalsIgnoreCase("supervisor")) {
                                Intent intent = new Intent(LoginActivity.this, SupervisorDashboardActivity.class);
                                intent.putExtra("username", u.username); // Si lo necesitas luego
                                startActivity(intent);
                            } else if (u.rol.equalsIgnoreCase("ejecutivo")) {
                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            }
                            finish();
                            break;
                        }
                    }

                    if (!encontrado) {
                        Toast.makeText(LoginActivity.this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
