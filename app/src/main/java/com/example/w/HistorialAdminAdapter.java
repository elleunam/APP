package com.example.w;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HistorialAdminAdapter extends RecyclerView.Adapter<HistorialAdminAdapter.ViewHolder> {

    private final List<Atencion> listaAtenciones;

    public HistorialAdminAdapter(List<Atencion> listaAtenciones) {
        this.listaAtenciones = listaAtenciones;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtNombreCliente, txtAsignado, txtSupervisor, txtEstado, txtFechaHora;

        public ViewHolder(View itemView) {
            super(itemView);
            txtNombreCliente = itemView.findViewById(R.id.txtNombreCliente);
            txtAsignado = itemView.findViewById(R.id.txtAsignado);
            txtSupervisor = itemView.findViewById(R.id.txtSupervisor);
            txtEstado = itemView.findViewById(R.id.txtEstado);
            txtFechaHora = itemView.findViewById(R.id.txtFechaHora);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_historial_admin, parent, false);
        return new ViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Atencion atencion = listaAtenciones.get(position);

        holder.txtNombreCliente.setText("Cliente: " + atencion.getNombreCliente());
        holder.txtAsignado.setText("Asignado a: " + atencion.getUsuario());

        // Mostrar fecha de asignación o mensaje por defecto
        if (atencion.getFechaHora() != null && !atencion.getFechaHora().isEmpty()) {
            holder.txtFechaHora.setText("Asignado el: " + atencion.getFechaHora());
        } else {
            holder.txtFechaHora.setText("Asignado el: (sin fecha)");
        }

        // Estado visual
        if (atencion.getResultado().equalsIgnoreCase("Pendiente")) {
            holder.txtEstado.setText("Estado: Pendiente");
            holder.txtEstado.setTextColor(Color.RED);
        } else {
            holder.txtEstado.setText("Estado: " + atencion.getResultado());
            holder.txtEstado.setTextColor(Color.DKGRAY);
        }

        // Buscar y mostrar supervisor
        String supervisor = "";
        for (Usuario u : DataStore.listaUsuarios) {
            if (u.username.equals(atencion.getUsuario())) {
                supervisor = u.supervisorAsignado;
                break;
            }
        }
        holder.txtSupervisor.setText("Supervisor: " + (supervisor.isEmpty() ? "No asignado" : supervisor));
    }

    @Override
    public int getItemCount() {
        return listaAtenciones.size();
    }
}
