package com.kevinbrenes.proyecto_diabetes;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    String strConfigFile = "config";
    SharedPreferences configFile;
    JSONObject jsonConfig = new JSONObject();
    boolean nuevoUsuario = false;
    //////////////////////////////////////////////////////////////////////////////////////////////
    private EditText txtNombre;
    private EditText txtEdad;
    private EditText txtPeso;
    private EditText txtAltura;
    private EditText txtDescripcion;
    private Button btnGuardar;
    //////////////////////////////////////////////////////////////////////////////////////////////

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mapearComponentes();
        obtenerConfiguracion();
        irSiguienteActividad();
    }

    private void mapearComponentes() {
        txtNombre = (EditText) findViewById(R.id.vtxtNombre);
        txtEdad = (EditText) findViewById(R.id.vtxtEdad);
        txtPeso = (EditText) findViewById(R.id.vtxtPeso);
        txtAltura = (EditText) findViewById(R.id.vtxtAltura);
        txtDescripcion = (EditText) findViewById(R.id.vtxtDescripcion);
        btnGuardar = (Button) findViewById(R.id.vbtnGuardar);
    }

    private void obtenerConfiguracion() {
        SharedPreferences configFile = this.getSharedPreferences(strConfigFile, this.MODE_PRIVATE);
        try {
            jsonConfig = new JSONObject();
            String strConfiguracion = configFile.getString("configuracion", "");
            if(strConfiguracion == "") nuevoUsuario = true;
            jsonConfig = new JSONObject(strConfiguracion);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void guardarConfiguracion(View target) {
        String strNombre = txtNombre.getText().toString();
        int intEdad = Integer.parseInt(txtEdad.getText().toString());
        double doublePeso = Double.parseDouble(txtPeso.getText().toString());
        double doubleAltura = Double.parseDouble(txtAltura.getText().toString());
        String strDescripcion = txtDescripcion.getText().toString();
        try {
            jsonConfig.put("nombre", strNombre);
            jsonConfig.put("edad",intEdad);
            jsonConfig.put("peso", doublePeso);
            jsonConfig.put("altura", doubleAltura);
            jsonConfig.put("descripcion", strDescripcion);
            SharedPreferences configFile = this.getSharedPreferences(strConfigFile, this.MODE_PRIVATE);
            SharedPreferences.Editor editor = configFile.edit();
            editor.putString("configuracion", jsonConfig.toString());
            editor.commit();
            Toast.makeText(this, "Configuración Guardada con Éxito", Toast.LENGTH_SHORT).show();
            irSiguienteActividad();
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, "No se guardó la configuración", Toast.LENGTH_SHORT).show();
        }

    }

    private void irSiguienteActividad() {
        if(nuevoUsuario == false) {
            Intent nuevaActividad = new Intent(this, MedicamentosActivity.class);
            startActivity(nuevaActividad);
            finish();
        }
    }
}
