package com.example.w;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class EquipoVentasActivity extends AppCompatActivity {

    private ListView listView;
    private String usernameSupervisor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equipo_ventas);

        listView = findViewById(R.id.listaEjecutivos);
        usernameSupervisor = getIntent().getStringExtra("username");

        List<Usuario> usuarios = DataStore.listaUsuarios; // ✅ Correcto
        List<String> ejecutivos = new ArrayList<>();

        for (Usuario u : usuarios) {
            if ("ejecutivo".equals(u.getRol()) && usernameSupervisor.equals(u.getSupervisorAsignado())) {
                ejecutivos.add("👤 " + u.getUsername());
            }
        }

        listView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, ejecutivos));
    }
}
