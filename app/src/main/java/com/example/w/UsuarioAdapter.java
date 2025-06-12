package com.example.w;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UsuarioAdapter extends RecyclerView.Adapter<UsuarioAdapter.ViewHolder> {

    private List<Usuario> lista;
    private final Context context;

    public UsuarioAdapter(List<Usuario> lista, Context context) {
        this.lista = lista;
        this.context = context;
    }

    public void actualizarLista(List<Usuario> nuevaLista) {
        this.lista = nuevaLista;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_usuario, parent, false);
        return new ViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Usuario usuario = lista.get(position);
        holder.tvNombre.setText(usuario.username);
        holder.tvRol.setText("Rol: " + usuario.rol);

        // Mostrar supervisor solo si es ejecutivo
        if (usuario.rol.equalsIgnoreCase("ejecutivo") && usuario.supervisorAsignado != null && !usuario.supervisorAsignado.isEmpty()) {
            holder.tvSupervisor.setText("Supervisor: " + usuario.supervisorAsignado);
            holder.tvSupervisor.setVisibility(View.VISIBLE);
        } else {
            holder.tvSupervisor.setVisibility(View.GONE);
        }

        holder.btnEditar.setOnClickListener(v -> {
            Intent intent = new Intent(context, FormularioUsuarioActivity.class);
            intent.putExtra("usuario_editado", usuario.username);
            context.startActivity(intent);
        });

        holder.btnEliminar.setOnClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setTitle("¿Eliminar usuario?")
                    .setMessage("¿Estás seguro de eliminar a " + usuario.username + "?")
                    .setPositiveButton("Sí", (dialog, which) -> {
                        DataStore.listaUsuarios.remove(position);
                        DataStore.guardarUsuarios(context);
                        notifyItemRemoved(position);
                    })
                    .setNegativeButton("Cancelar", null)
                    .show();
        });
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombre, tvRol, tvSupervisor;
        Button btnEditar, btnEliminar;

        public ViewHolder(View itemView) {
            super(itemView);
            tvNombre = itemView.findViewById(R.id.tvNombreUsuario);
            tvRol = itemView.findViewById(R.id.tvRolUsuario);
            tvSupervisor = itemView.findViewById(R.id.tvSupervisorUsuario);
            btnEditar = itemView.findViewById(R.id.btnEditarUsuario);
            btnEliminar = itemView.findViewById(R.id.btnEliminarUsuario);
        }
    }
}
