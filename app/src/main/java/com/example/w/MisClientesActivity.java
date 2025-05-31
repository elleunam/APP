package com.example.w;

import android.app.Activity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MisClientesActivity extends Activity {

    private RecyclerView recyclerClientes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_clientes);

        recyclerClientes = findViewById(R.id.recyclerClientes);
        recyclerClientes.setLayoutManager(new LinearLayoutManager(this));

        // ✅ Cargar lista actualizada desde JSON
        DataStore.cargarClientes(this);

        // Filtrar clientes asignados y marcar como vistos
        List<Cliente> asignados = new ArrayList<>();
        for (Cliente c : DataStore.listaClientes) {
            if (c.asignadoA.equals(DataStore.usuarioActual)) {
                asignados.add(c);
                c.vistoPorUsuario = true;
            }
        }

        // ✅ Guardar cambios
        DataStore.guardarClientes(this);

        // Mostrar en RecyclerView
        ClienteAdapter adapter = new ClienteAdapter(this, asignados);
        recyclerClientes.setAdapter(adapter);
    }
}
