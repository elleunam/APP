package com.example.w;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HistorialClienteAdapter extends RecyclerView.Adapter<HistorialClienteAdapter.ViewHolder> {

    private List<Cliente> lista;

    public HistorialClienteAdapter(List<Cliente> lista) {
        this.lista = lista;
    }

    public void actualizarLista(List<Cliente> nuevaLista) {
        this.lista = nuevaLista;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtNombre, txtEjecutivo, txtSupervisor, txtEstado, txtFecha;

        public ViewHolder(View itemView) {
            super(itemView);
            txtNombre = itemView.findViewById(R.id.txtNombreCliente);
            txtEjecutivo = itemView.findViewById(R.id.txtAsignado);
            txtSupervisor = itemView.findViewById(R.id.txtSupervisor);
            txtEstado = itemView.findViewById(R.id.txtEstado);
            txtFecha = itemView.findViewById(R.id.txtFechaHora);
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
        Cliente cliente = lista.get(position);

        holder.txtNombre.setText("Cliente: " + cliente.getNombreCliente());
        holder.txtEjecutivo.setText("Asignado a: " + cliente.getAsignadoA());

        // Estado visual
        if (cliente.isAtendido()) {
            holder.txtEstado.setText("Estado: Cerrado");
            holder.txtEstado.setTextColor(Color.DKGRAY);
        } else {
            holder.txtEstado.setText("Estado: Pendiente");
            holder.txtEstado.setTextColor(Color.RED);
        }

        // Fecha de asignación
        if (cliente.getFechaAsignacion() != null && !cliente.getFechaAsignacion().isEmpty()) {
            holder.txtFecha.setText("Asignado el: " + cliente.getFechaAsignacion());
        } else {
            holder.txtFecha.setText("Asignado el: (sin fecha)");
        }

        // Buscar supervisor según asignadoA
        String supervisor = "";
        for (Usuario u : DataStore.listaUsuarios) {
            if (u.getUsername().equals(cliente.getAsignadoA())) {
                supervisor = u.getSupervisorAsignado();
                break;
            }
        }
        holder.txtSupervisor.setText("Supervisor: " + (supervisor.isEmpty() ? "No asignado" : supervisor));
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }
}
