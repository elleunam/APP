package com.example.w;

import android.content.Context;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DataStore {
    public static String usuarioActual = "";
    public static String rolActual = "";

    public static List<Usuario> listaUsuarios = new ArrayList<>();
    public static List<Atencion> listaAtenciones = new ArrayList<>();
    public static List<Cliente> listaClientes = new ArrayList<>();

    private static final String ARCHIVO_CLIENTES = "clientes.json";
    private static final String ARCHIVO_ATENCIONES = "atenciones.json";
    private static final String ARCHIVO_USUARIOS = "usuarios.json";

    static {
        // Usuarios de prueba
        listaUsuarios.add(new Usuario("admin", "admin123", "admin"));
        listaUsuarios.add(new Usuario("jtorres", "123456", "ejecutivo"));
    }

    // Guardar clientes
    public static void guardarClientes(Context context) {
        try {
            FileOutputStream fos = context.openFileOutput(ARCHIVO_CLIENTES, Context.MODE_PRIVATE);
            String json = new Gson().toJson(listaClientes);
            fos.write(json.getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Cargar clientes
    public static void cargarClientes(Context context) {
        try {
            File archivo = new File(context.getFilesDir(), ARCHIVO_CLIENTES);
            if (archivo.exists()) {
                FileInputStream fis = context.openFileInput(ARCHIVO_CLIENTES);
                BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
                StringBuilder builder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                }
                reader.close();
                fis.close();
                listaClientes = new Gson().fromJson(
                        builder.toString(),
                        new TypeToken<List<Cliente>>() {}.getType()
                );
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Guardar atenciones
    public static void guardarAtenciones(Context context) {
        try {
            FileOutputStream fos = context.openFileOutput(ARCHIVO_ATENCIONES, Context.MODE_PRIVATE);
            String json = new Gson().toJson(listaAtenciones);
            fos.write(json.getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Cargar atenciones
    public static void cargarAtenciones(Context context) {
        try {
            File archivo = new File(context.getFilesDir(), ARCHIVO_ATENCIONES);
            if (archivo.exists()) {
                FileInputStream fis = context.openFileInput(ARCHIVO_ATENCIONES);
                BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
                StringBuilder builder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                }
                reader.close();
                fis.close();
                listaAtenciones = new Gson().fromJson(
                        builder.toString(),
                        new TypeToken<List<Atencion>>() {}.getType()
                );
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Guardar usuarios
    public static void guardarUsuarios(Context context) {
        try {
            FileOutputStream fos = context.openFileOutput(ARCHIVO_USUARIOS, Context.MODE_PRIVATE);
            String json = new Gson().toJson(listaUsuarios);
            fos.write(json.getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Cargar usuarios
    public static void cargarUsuarios(Context context) {
        try {
            File archivo = new File(context.getFilesDir(), ARCHIVO_USUARIOS);
            if (archivo.exists()) {
                FileInputStream fis = context.openFileInput(ARCHIVO_USUARIOS);
                BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
                StringBuilder builder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                }
                reader.close();
                fis.close();
                listaUsuarios = new Gson().fromJson(
                        builder.toString(),
                        new TypeToken<List<Usuario>>() {}.getType()
                );
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // ✅ Asignar cliente a un ejecutivo (usando getters/setters)
    public static void asignarCliente(Context context, String nombreCliente, String ejecutivo) {
        for (Cliente c : listaClientes) {
            if (c.getNombreCliente().equals(nombreCliente)) {
                c.setAsignadoA(ejecutivo);
                c.setFechaAsignacion(obtenerFechaHoraActual());
                c.setAtendido(false);
                c.setAtendidoPor("");
                c.setFechaAtencion("");
                break;
            }
        }
        guardarClientes(context);
    }

    // ✅ Obtener fecha y hora actual
    public static String obtenerFechaHoraActual() {
        return new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date());
    }

    // 🔽 Obtener lista atualizada de usuarios
    public static List<Usuario> getListaUsuarios(Context context) {
        cargarUsuarios(context);
        return listaUsuarios;
    }

    // 🔽 Obtener lista actualizada de clientes
    public static List<Cliente> getListaClientes(Context context) {
        cargarClientes(context);
        return listaClientes;
    }
}
