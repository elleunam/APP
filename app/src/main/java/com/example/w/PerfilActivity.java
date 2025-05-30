package com.example.w;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class PerfilActivity extends AppCompatActivity {

    TextView tvNombre, tvCorreo, tvRol;
    Button btnCerrarSesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        tvNombre = findViewById(R.id.tvNombre);
        tvCorreo = findViewById(R.id.tvCorreo);
        tvRol = findViewById(R.id.tvRol);
        btnCerrarSesion = findViewById(R.id.btnCerrarSesion);

        // Simulando datos temporales
        tvNombre.setText("Jair Alexander Torres");
        tvCorreo.setText("jair@example.com");
        tvRol.setText("Ejecutivo de Campo");

        btnCerrarSesion.setOnClickListener(view -> {
            Intent i = new Intent(PerfilActivity.this, LoginActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
            finish();
        });
    }
}
