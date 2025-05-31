package com.example.w;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class HistorialAdminAdapter extends RecyclerView.Adapter<HistorialAdminAdapter.ViewHolder> {

    private List<Cliente> listaClientes;

    public HistorialAdminAdapter(List<Cliente> listaClientes) {
        this.listaClientes = listaClientes;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtNombreCliente, txtAsignado, txtEstado;

        public ViewHolder(View itemView) {
            super(itemView);
            txtNombreCliente = itemView.findViewById(R.id.txtNombreCliente);
            txtAsignado = itemView.findViewById(R.id.txtAsignado);
            txtEstado = itemView.findViewById(R.id.txtEstado);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_historial_admin, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Cliente cliente = listaClientes.get(position);

        holder.txtNombreCliente.setText(cliente.getNombreCliente());
        holder.txtAsignado.setText("Asignado a: " + cliente.asignadoA);

        if (cliente.atendido) {
            holder.txtEstado.setText("Atendido por: " + cliente.atendidoPor + "\nFecha: " + cliente.fechaAtencion);
            holder.txtEstado.setTextColor(Color.BLACK);
        } else {
            holder.txtEstado.setText("Pendiente de atención");
            holder.txtEstado.setTextColor(Color.RED);
        }
    }

    @Override
    public int getItemCount() {
        return listaClientes.size();
    }
}
