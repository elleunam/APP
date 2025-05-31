package com.example.w;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HistorialAdapter extends RecyclerView.Adapter<HistorialAdapter.ViewHolder> {

    private final List<Atencion> lista;

    public HistorialAdapter(List<Atencion> lista) {
        this.lista = lista;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_atencion, parent, false);
        return new ViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Atencion atencion = lista.get(position);

        // ✅ Mostrar primero el nombre del cliente
        holder.tvCliente.setText("👤 Cliente: " + atencion.getNombreCliente());

        holder.tvFecha.setText("📅 " + atencion.getFechaHora());
        holder.tvTipo.setText("Tipo: " + atencion.getTipo());
        holder.tvResultado.setText("Resultado: " + atencion.getResultado());
        holder.tvObservacion.setText("Obs: " + atencion.getObservacion());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), ClienteAsignadoActivity.class);
            intent.putExtra("modoEdicion", true);
            intent.putExtra("atencion", atencion);
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvCliente, tvFecha, tvTipo, tvResultado, tvObservacion;

        public ViewHolder(View itemView) {
            super(itemView);

            // ✅ Referenciar también el nuevo TextView
            tvCliente = itemView.findViewById(R.id.tvCliente);
            tvFecha = itemView.findViewById(R.id.tvFecha);
            tvTipo = itemView.findViewById(R.id.tvTipo);
            tvResultado = itemView.findViewById(R.id.tvResultado);
            tvObservacion = itemView.findViewById(R.id.tvObservacion);
        }
    }
}
