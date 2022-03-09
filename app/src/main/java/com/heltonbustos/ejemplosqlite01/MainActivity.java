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

import com.heltonbustos.ejemplosqlite01.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding x;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        x = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(x.getRoot());

    }

    public void borrar(View view){
        ConexionBD conexion = new ConexionBD(this, "administracion", null, 1);
        SQLiteDatabase bd = conexion.getWritableDatabase();

        String codigo = x.txtCodigo.getText().toString();

        int a = bd.delete("articulos", "codigo="+codigo, null);

        if (a > 0){
            Toast.makeText(this, "Borrado correctamente!", Toast.LENGTH_SHORT).show();
        }

        bd.close();

        x.txtCodigo.setText("");
        x.txtDescripcion.setText("");
        x.txtPrecio.setText("");
    }

    public void guardar(View view){
        ConexionBD conexion = new ConexionBD(this, "administracion", null, 1);
        SQLiteDatabase bd = conexion.getWritableDatabase();

        String codigo = x.txtCodigo.getText().toString();
        String descripcion = x.txtDescripcion.getText().toString();
        String precio = x.txtPrecio.getText().toString();

        ContentValues registro = new ContentValues();
        registro.put("codigo", codigo);
        registro.put("descripcion", descripcion);
        registro.put("precio", precio);

        bd.insert("articulos", null, registro);
        bd.close();

        x.txtCodigo.setText("");
        x.txtDescripcion.setText("");
        x.txtPrecio.setText("");

        Toast.makeText(this, "Registrado correctamente", Toast.LENGTH_SHORT).show();
    }

    public void buscar(View view){
       ConexionBD conexion = new ConexionBD(this, "administracion", null, 1);
       SQLiteDatabase bd = conexion.getWritableDatabase();

       String codigo = x.txtCodigo.getText().toString();

       Cursor fila = bd.rawQuery("SELECT descripcion, precio FROM articulos WHERE codigo='"+codigo+"'", null);

       if(fila.moveToFirst()){
           x.txtDescripcion.setText(fila.getString(0));
           x.txtPrecio.setText(fila.getString(1));
           bd.close();
           Toast.makeText(this, "Producto encontrado!", Toast.LENGTH_SHORT).show();
       }
       else{
           bd.close();
           Toast.makeText(this, "No se encuentra el producto", Toast.LENGTH_SHORT).show();
           x.txtCodigo.setText("");
           x.txtDescripcion.setText("");
           x.txtPrecio.setText("");
       }
    }

    public void editar(View view){
        ConexionBD conexion = new ConexionBD(this, "administracion", null, 1);
        SQLiteDatabase bd = conexion.getWritableDatabase();

        String codigo = x.txtCodigo.getText().toString();
        String descripcion = x.txtDescripcion.getText().toString();
        String precio = x.txtPrecio.getText().toString();

        ContentValues registro = new ContentValues();
        registro.put("codigo", codigo);
        registro.put("descripcion", descripcion);
        registro.put("precio", precio);

        int a = bd.update("articulos", registro, "codigo='"+codigo+"'", null);

        if(a>0){
            Toast.makeText(this, "Modificado correctamente", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this, "No se pudo modificar", Toast.LENGTH_SHORT).show();
        }
        bd.close();
    }




}