package com.heltonbustos.ejemplosqlite01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText txtCodigo, txtDescripcion, txtPrecio;
    Button btnBuscar, btnGuardar, btnEditar, btnBorrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtCodigo = findViewById(R.id.txtCodigo);
        txtDescripcion = findViewById(R.id.txtDescripcion);
        txtPrecio = findViewById(R.id.txtPrecio);

        btnBuscar = findViewById(R.id.btnBuscar);
        btnGuardar = findViewById(R.id.btnGuardar);
        btnEditar = findViewById(R.id.btnEditar);
        btnBorrar = findViewById(R.id.btnBorrar);

    }

    public void guardar(View view){
        ConexionBD conexion = new ConexionBD(this, "administracion", null, 1);
        SQLiteDatabase bd = conexion.getWritableDatabase();

        String codigo = txtCodigo.getText().toString();
        String descripcion = txtDescripcion.getText().toString();
        String precio = txtPrecio.getText().toString();

        ContentValues registro = new ContentValues();
        registro.put("codigo", codigo);
        registro.put("descripcion", descripcion);
        registro.put("precio", precio);

        bd.insert("articulos", null, registro);
        bd.close();

        txtCodigo.setText("");
        txtDescripcion.setText("");
        txtPrecio.setText("");

        Toast.makeText(this, "Registrado correctamente", Toast.LENGTH_SHORT).show();
    }

    public void buscar(View view){
       ConexionBD conexion = new ConexionBD(this, "administracion", null, 1);
       SQLiteDatabase bd = conexion.getWritableDatabase();

       String codigo = txtCodigo.getText().toString();

       Cursor fila = bd.rawQuery("SELECT descripcion, precio FROM articulos WHERE codigo='"+codigo+"'", null);

       if(fila.moveToFirst()){
           txtDescripcion.setText(fila.getString(0));
           txtPrecio.setText(fila.getString(1));
           bd.close();
           Toast.makeText(this, "Producto encontrado!", Toast.LENGTH_SHORT).show();
       }
       else{
           bd.close();
           Toast.makeText(this, "No se encuentra el producto", Toast.LENGTH_SHORT).show();
           txtCodigo.setText("");
           txtDescripcion.setText("");
           txtPrecio.setText("");
       }
    }

    public void editar(View view){
        ConexionBD conexion = new ConexionBD(this, "administracion", null, 1);
        SQLiteDatabase bd = conexion.getWritableDatabase();

        String codigo = txtCodigo.getText().toString();
        String descripcion = txtDescripcion.getText().toString();
        String precio = txtPrecio.getText().toString();

        ContentValues registro = new ContentValues();
        registro.put("codigo", codigo);
        registro.put("descripcion", descripcion);
        registro.put("precio", precio);

        int x = bd.update("articulos", registro, "codigo='"+codigo+"'", null);

        if(x>0){
            Toast.makeText(this, "Modificado correctamente", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this, "No se pudo modificar", Toast.LENGTH_SHORT).show();
        }
        bd.close();
    }
}