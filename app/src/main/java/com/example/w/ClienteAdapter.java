package com.example.w;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ClienteAdapter extends RecyclerView.Adapter<ClienteAdapter.ViewHolder> {

    private final List<Cliente> lista;
    private final Context context;

    public ClienteAdapter(Context context, List<Cliente> lista) {
        this.context = context;
        this.lista = lista;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_cliente, parent, false);
        return new ViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Cliente cliente = lista.get(position);

        // Usar getters en lugar de acceso directo
        holder.tvNombre.setText(cliente.getNombreCliente());
        holder.tvAsignado.setText("Asignado a: " + cliente.getAsignadoA());

        // Si el usuario actual es quien lo tiene asignado, puede atenderlo
        holder.itemView.setOnClickListener(v -> {
            if (cliente.getAsignadoA().equals(DataStore.usuarioActual)) {
                Intent intent = new Intent(context, ClienteAsignadoActivity.class);
                intent.putExtra("nombreCliente", cliente.getNombreCliente());
                intent.putExtra("fechaAsignacion", "Asignado previamente"); // Puedes agregar más datos si deseas
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombre, tvAsignado;

        ViewHolder(View itemView) {
            super(itemView);
            tvNombre = itemView.findViewById(R.id.tvNombreCliente);
            tvAsignado = itemView.findViewById(R.id.tvAsignado);
        }
    }
}
