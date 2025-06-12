package com.example.w;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.*;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ClienteAsignadoActivity extends Activity {

    TextInputEditText nombreClienteInput, fechaAsignacionInput, fechaAtencionInput, observacionesInput;
    Spinner spinnerTipoAtencion, spinnerResultado;
    Button btnGuardar;

    boolean modoEdicion = false;
    int indiceOriginal = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente_asignado);

        // Referencias UI
        nombreClienteInput = findViewById(R.id.nombreClienteInput);
        fechaAsignacionInput = findViewById(R.id.fechaAsignacionInput);
        fechaAtencionInput = findViewById(R.id.fechaAtencionInput);
        observacionesInput = findViewById(R.id.observacionesInput);
        spinnerTipoAtencion = findViewById(R.id.spinnerTipoAtencion);
        spinnerResultado = findViewById(R.id.spinnerResultado);
        btnGuardar = findViewById(R.id.btnGuardar);

        // Adaptadores
        ArrayAdapter<String> tipoAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
                new String[]{"Presencial", "Virtual", "Telefónica"});
        tipoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTipoAtencion.setAdapter(tipoAdapter);

        ArrayAdapter<String> resultadoAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
                new String[]{"Completado", "Pendiente", "Rechazado"});
        resultadoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerResultado.setAdapter(resultadoAdapter);

        // Calendario
        fechaAtencionInput.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            DatePickerDialog dialog = new DatePickerDialog(this,
                    (view, year, month, dayOfMonth) -> {
                        calendar.set(year, month, dayOfMonth);
                        String fechaFormateada = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(calendar.getTime());
                        fechaAtencionInput.setText(fechaFormateada);
                    },
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH));
            dialog.show();
        });

        // Verificar si es modo edición
        modoEdicion = getIntent().getBooleanExtra("modoEdicion", false);
        if (modoEdicion) {
            Atencion atencion = (Atencion) getIntent().getSerializableExtra("atencion");

            if (atencion != null) {
                nombreClienteInput.setText(atencion.getNombreCliente());
                fechaAsignacionInput.setText(atencion.getFechaHora());
                fechaAtencionInput.setText(atencion.getFechaHora());
                observacionesInput.setText(atencion.getObservacion());

                int tipoIndex = tipoAdapter.getPosition(atencion.getTipo());
                int resultadoIndex = resultadoAdapter.getPosition(atencion.getResultado());
                spinnerTipoAtencion.setSelection(tipoIndex >= 0 ? tipoIndex : 0);
                spinnerResultado.setSelection(resultadoIndex >= 0 ? resultadoIndex : 0);

                // Encontrar índice original en la lista
                for (int i = 0; i < DataStore.listaAtenciones.size(); i++) {
                    Atencion a = DataStore.listaAtenciones.get(i);
                    if (a.getNombreCliente().equals(atencion.getNombreCliente()) &&
                            a.getFechaHora().equals(atencion.getFechaHora()) &&
                            a.getUsuario().equals(atencion.getUsuario())) {
                        indiceOriginal = i;
                        break;
                    }
                }
            }
        } else {
            // Si es nuevo
            String nombreCliente = getIntent().getStringExtra("nombreCliente");
            String fechaAsignacion = getIntent().getStringExtra("fechaAsignacion");
            nombreClienteInput.setText(nombreCliente);
            fechaAsignacionInput.setText(fechaAsignacion);
        }

        // Guardar atención
        btnGuardar.setOnClickListener(v -> {
            String nombreCliente = nombreClienteInput.getText().toString().trim();
            String fechaAtencion = fechaAtencionInput.getText().toString().trim();
            String tipo = spinnerTipoAtencion.getSelectedItem().toString();
            String resultado = spinnerResultado.getSelectedItem().toString();
            String observacion = observacionesInput.getText().toString().trim();
            String usuario = DataStore.usuarioActual;

            if (fechaAtencion.isEmpty() || tipo.isEmpty() || resultado.isEmpty()) {
                Toast.makeText(this, "Completa todos los campos requeridos", Toast.LENGTH_SHORT).show();
                return;
            }

            String fechaHora = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(new Date());
            Atencion nueva = new Atencion(nombreCliente, tipo, resultado, observacion, usuario, fechaHora);

            if (modoEdicion && indiceOriginal >= 0) {
                Atencion atencionOriginal = DataStore.listaAtenciones.get(indiceOriginal);

                boolean sinCambios = atencionOriginal.getTipo().equals(tipo) &&
                        atencionOriginal.getResultado().equals(resultado) &&
                        atencionOriginal.getObservacion().equals(observacion) &&
                        fechaAtencion.equals(atencionOriginal.getFechaHora());

                if (sinCambios) {
                    Toast.makeText(this, "No se detectaron cambios", Toast.LENGTH_SHORT).show();
                    return;
                }

                DataStore.listaAtenciones.set(indiceOriginal, nueva);
                Toast.makeText(this, "Atención actualizada", Toast.LENGTH_SHORT).show();
            } else {
                DataStore.listaAtenciones.add(nueva);
                Toast.makeText(this, "Atención registrada", Toast.LENGTH_SHORT).show();
            }

            // ✅ ACTUALIZAR CLIENTE COMO ATENDIDO
            DataStore.cargarClientes(this); // Muy importante antes de modificar

            for (Cliente c : DataStore.listaClientes) {
                if (c.getNombreCliente().equals(nombreCliente)) {
                    c.setAtendido(true);
                    c.setAtendidoPor(DataStore.usuarioActual);
                    c.setFechaAtencion(obtenerFechaActual());
                    break;
                }
            }

            DataStore.guardarAtenciones(this);
            DataStore.guardarClientes(this); // Guarda cambios al cliente

            finish();
        });


    }


    private String obtenerFechaActual() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
        return sdf.format(new Date());
    }
}
