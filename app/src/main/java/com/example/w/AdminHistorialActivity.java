package com.example.w;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class AdminHistorialActivity extends AppCompatActivity {

    private RecyclerView recyclerViewHistorial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_historial);

        recyclerViewHistorial = findViewById(R.id.recyclerAdminHistorial);
        recyclerViewHistorial.setLayoutManager(new LinearLayoutManager(this));

        // Cargar desde JSON
        DataStore.cargarClientes(this);

        // Mostrar
        HistorialAdminAdapter adapter = new HistorialAdminAdapter(DataStore.listaClientes);
        recyclerViewHistorial.setAdapter(adapter);
    }
}
