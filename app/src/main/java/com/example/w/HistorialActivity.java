package com.example.w;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class HistorialActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private HistorialAdapter adapter;
    private List<Atencion> listaFiltrada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial);

        recyclerView = findViewById(R.id.recyclerHistorial);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // ✅ Cargar la lista real desde el archivo JSON
        DataStore.cargarAtenciones(this);

        listaFiltrada = new ArrayList<>();
        String usuarioLogueado = DataStore.usuarioActual;

        for (Atencion at : DataStore.listaAtenciones) {
            if (at.getUsuario().equals(usuarioLogueado)) {
                listaFiltrada.add(at);
            }
        }

        adapter = new HistorialAdapter(listaFiltrada);
        recyclerView.setAdapter(adapter);
    }
}
