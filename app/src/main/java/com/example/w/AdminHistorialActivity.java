package com.example.w;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Button;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class AdminHistorialActivity extends AppCompatActivity {

    private EditText etFiltroFecha;
    private Spinner spFiltroEstado, spFiltroSupervisor;
    private RecyclerView recycler;
    private HistorialClienteAdapter adapter;
    private List<Cliente> listaOriginal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_historial);

        // ✅ Vincular vistas
        etFiltroFecha = findViewById(R.id.etFiltroFecha);
        spFiltroEstado = findViewById(R.id.spFiltroEstado);
        spFiltroSupervisor = findViewById(R.id.spFiltroSupervisor);
        recycler = findViewById(R.id.recyclerAdminHistorial);
        recycler.setLayoutManager(new LinearLayoutManager(this));

        // ✅ Cargar lista de clientes desde JSON
        DataStore.cargarClientes(this);
        listaOriginal = new ArrayList<>(DataStore.listaClientes);

        // ✅ Crear adaptador base
        adapter = new HistorialClienteAdapter(listaOriginal);
        recycler.setAdapter(adapter);

        Button btnLimpiarFiltros = findViewById(R.id.btnLimpiarFiltros);
        btnLimpiarFiltros.setOnClickListener(v -> {
            etFiltroFecha.setText("");
            spFiltroEstado.setSelection(0);       // "Todos"
            spFiltroSupervisor.setSelection(0);   // "Todos"
            aplicarFiltros();                     // Vuelve a mostrar todo
        });

        // ✅ Filtro Fecha
        etFiltroFecha.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
                String fechaSeleccionada = String.format(Locale.getDefault(), "%02d/%02d/%04d", dayOfMonth, month + 1, year);
                etFiltroFecha.setText(fechaSeleccionada);
                aplicarFiltros();
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
        });

        // ✅ Filtro Estado
        ArrayAdapter<String> adapterEstado = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
                new String[]{"Todos", "Pendiente", "Cerrado", "En curso"});
        adapterEstado.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spFiltroEstado.setAdapter(adapterEstado);
        spFiltroEstado.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) { aplicarFiltros(); }
            @Override public void onNothingSelected(AdapterView<?> parent) {}
        });

        // ✅ Filtro Supervisor
        List<String> supervisores = new ArrayList<>();
        supervisores.add("Todos");
        for (Usuario u : DataStore.listaUsuarios) {
            if ("supervisor".equalsIgnoreCase(u.rol)) {
                supervisores.add(u.username);
            }
        }
        ArrayAdapter<String> adapterSupervisor = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, supervisores);
        adapterSupervisor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spFiltroSupervisor.setAdapter(adapterSupervisor);
        spFiltroSupervisor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) { aplicarFiltros(); }
            @Override public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void aplicarFiltros() {
        String fechaFiltro = etFiltroFecha.getText().toString();
        String estadoFiltro = spFiltroEstado.getSelectedItem().toString();
        String supervisorFiltro = spFiltroSupervisor.getSelectedItem().toString();

        List<Cliente> filtrados = new ArrayList<>();
        for (Cliente c : listaOriginal) {
            boolean cumple = true;

            // Filtro por fecha de asignación
            if (!TextUtils.isEmpty(fechaFiltro) &&
                    (c.getFechaAsignacion() == null || !c.getFechaAsignacion().contains(fechaFiltro))) {
                cumple = false;
            }

            // Filtro por estado
            if (!estadoFiltro.equals("Todos")) {
                String estado = c.isAtendido() ? "Cerrado" : "Pendiente";
                if (!estado.equalsIgnoreCase(estadoFiltro)) {
                    cumple = false;
                }
            }

            // Filtro por supervisor
            if (!supervisorFiltro.equals("Todos")) {
                String supervisor = "";
                for (Usuario u : DataStore.listaUsuarios) {
                    if (u.getUsername().equals(c.getAsignadoA())) {
                        supervisor = u.getSupervisorAsignado();
                        break; // ✅ Rompe el bucle una vez encontrado
                    }
                }
                if (!supervisorFiltro.equalsIgnoreCase(supervisor)) {
                    cumple = false;
                }
            }

            if (cumple) {
                filtrados.add(c);
            }
        }

        adapter = new HistorialClienteAdapter(filtrados);
        recycler.setAdapter(adapter);
    }

}
