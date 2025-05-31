package com.example.w;

import android.content.Context;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DataStore {
    public static String usuarioActual = "";
    public static String rolActual = "";

    public static List<Usuario> listaUsuarios = new ArrayList<>();
    public static List<Atencion> listaAtenciones = new ArrayList<>();
    public static List<Cliente> listaClientes = new ArrayList<>();

    private static final String ARCHIVO_CLIENTES = "clientes.json";
    private static final String ARCHIVO_ATENCIONES = "atenciones.json";

    static {
        // Usuarios de prueba
        listaUsuarios.add(new Usuario("admin", "admin123", "admin"));
        listaUsuarios.add(new Usuario("jtorres", "123456", "ejecutivo"));
    }

    // 🔽 Guardar listaClientes a archivo JSON
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

    // 🔼 Cargar listaClientes desde archivo JSON
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
}
