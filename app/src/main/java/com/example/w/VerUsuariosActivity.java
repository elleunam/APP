package com.example.w;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class VerUsuariosActivity extends Activity {

    private RecyclerView recyclerUsuarios;
    private Button btnAgregarUsuario;
    private UsuarioAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_usuarios);

        btnAgregarUsuario = findViewById(R.id.btnAgregarUsuario);
        recyclerUsuarios = findViewById(R.id.recyclerUsuarios);
        recyclerUsuarios.setLayoutManager(new LinearLayoutManager(this));

        adapter = new UsuarioAdapter(DataStore.listaUsuarios, this);  // si lo ajustas
        recyclerUsuarios.setAdapter(adapter);

        btnAgregarUsuario.setOnClickListener(v -> {
            Intent intent = new Intent(this, FormularioUsuarioActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.actualizarLista(DataStore.listaUsuarios);  // si tu adapter lo permite
    }
}
