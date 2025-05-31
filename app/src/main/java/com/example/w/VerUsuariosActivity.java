package com.example.w;

import android.app.Activity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class VerUsuariosActivity extends Activity {

    private RecyclerView recyclerUsuarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_usuarios);

        recyclerUsuarios = findViewById(R.id.recyclerUsuarios);
        recyclerUsuarios.setLayoutManager(new LinearLayoutManager(this));

        UsuarioAdapter adapter = new UsuarioAdapter(DataStore.listaUsuarios);
        recyclerUsuarios.setAdapter(adapter);
    }
}
