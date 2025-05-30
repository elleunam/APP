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
    private List<Atencion> listaAtenciones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial);

        recyclerView = findViewById(R.id.recyclerHistorial);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Datos simulados de ejemplo
        listaAtenciones = new ArrayList<>();
        listaAtenciones.add(new Atencion("24/05/2025", "Presencial", "Interesado", "Cliente quiere propuesta."));
        listaAtenciones.add(new Atencion("25/05/2025", "Telefónica", "No interesado", "Sin presupuesto."));
        listaAtenciones.add(new Atencion("26/05/2025", "Presencial", "Seguimiento", "Volver a contactar la próxima semana."));

        adapter = new HistorialAdapter(listaAtenciones);
        recyclerView.setAdapter(adapter);
    }
}
