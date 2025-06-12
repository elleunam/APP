package com.example.w;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class SupervisorResumenClientesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private HistorialClienteAdapter adapter;
    private List<Cliente> listaClientesSupervisor;
    private String usernameSupervisor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supervisor_resumen_clientes);

        recyclerView = findViewById(R.id.recyclerSupervisorClientes);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        usernameSupervisor = getIntent().getStringExtra("username");

        // Cargar clientes desde DataStore
        List<Cliente> todosLosClientes = DataStore.getListaClientes(this);
        List<Usuario> usuarios = DataStore.getListaUsuarios(this);

        // Filtrar ejecutivos del supervisor
        List<String> ejecutivosAsignados = new ArrayList<>();
        for (Usuario u : usuarios) {
            if (u.getRol().equals("ejecutivo") && usernameSupervisor.equals(u.getSupervisorAsignado())) {
                ejecutivosAsignados.add(u.getUsername());
            }
        }

        // Filtrar clientes asignados a esos ejecutivos
        listaClientesSupervisor = new ArrayList<>();
        for (Cliente c : todosLosClientes) {
            if (ejecutivosAsignados.contains(c.getAsignadoA())) {
                listaClientesSupervisor.add(c);
            }
        }

        adapter = new HistorialClienteAdapter(listaClientesSupervisor);
        recyclerView.setAdapter(adapter);
    }
}
