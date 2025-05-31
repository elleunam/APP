package com.example.w;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class RegistroAtencionActivity extends AppCompatActivity {

    private EditText fechaInput, observacionesInput;
    private Spinner spinnerTipoAtencion, spinnerResultado;
    private Button btnGuardar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_atencion);

        // Referencias
        fechaInput = findViewById(R.id.fechaInput);
        observacionesInput = findViewById(R.id.observacionesInput);
        spinnerTipoAtencion = findViewById(R.id.spinnerTipoAtencion);
        spinnerResultado = findViewById(R.id.spinnerResultado);
        btnGuardar = findViewById(R.id.btnGuardar);

        // Spinner: Tipo de atención
        String[] tipos = {"Presencial", "Telefónica"};
        ArrayAdapter<String> tipoAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, tipos);
        spinnerTipoAtencion.setAdapter(tipoAdapter);

        // Spinner: Resultado
        String[] resultados = {"Interesado", "No interesado", "Seguimiento"};
        ArrayAdapter<String> resultadoAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, resultados);
        spinnerResultado.setAdapter(resultadoAdapter);

        // Selector de fecha
        fechaInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarSelectorFecha();
            }
        });

        // Botón guardar
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                guardarAtencion();
            }
        });
    }

    private void mostrarSelectorFecha() {
        final Calendar calendar = Calendar.getInstance();
        int año = calendar.get(Calendar.YEAR);
        int mes = calendar.get(Calendar.MONTH);
        int dia = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePicker = new DatePickerDialog(this,
                (view, year, month, dayOfMonth) -> {
                    String fecha = String.format("%02d/%02d/%04d", dayOfMonth, month + 1, year);
                    fechaInput.setText(fecha);
                },
                año, mes, dia);
        datePicker.show();
    }

    private void guardarAtencion() {
        DataStore.cargarClientes(this); // 🔥 CRÍTICO para cargar listaClientes desde JSON

        String fecha = fechaInput.getText().toString();
        String tipo = spinnerTipoAtencion.getSelectedItem().toString();
        String resultado = spinnerResultado.getSelectedItem().toString();
        String observaciones = observacionesInput.getText().toString();

        if (fecha.isEmpty() || observaciones.isEmpty()) {
            Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        // 🔹 Recuperar el nombre del cliente enviado por Intent
        String nombreCliente = getIntent().getStringExtra("cliente");
        if (nombreCliente == null) nombreCliente = "Sin cliente específico";

        // 🔸 Crear y guardar atención
        Atencion nueva = new Atencion(
                nombreCliente,
                tipo,
                resultado,
                observaciones,
                DataStore.usuarioActual,
                fecha
        );

        DataStore.listaAtenciones.add(nueva);
        DataStore.guardarAtenciones(this);

        // 🔴 ACTUALIZAR CLIENTE COMO ATENDIDO
        for (Cliente c : DataStore.listaClientes) {
            if (c.nombreCliente.equals(nombreCliente)) {
                c.atendido = true;
                c.atendidoPor = DataStore.usuarioActual;
                c.fechaAtencion = fecha;
                break;
            }
        }

        DataStore.guardarClientes(this); // ✅ Esto guarda la lista actualizada al JSON

        Toast.makeText(this, "Atención registrada y cliente actualizado", Toast.LENGTH_LONG).show();
        finish();
    }
}
