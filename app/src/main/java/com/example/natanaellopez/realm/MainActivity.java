package com.example.natanaellopez.realm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.natanaellopez.realm.models.Persona;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {

    EditText etNombre, etEdad;
    Button btnGuardar, btnVer, btnEliminar;
    TextView txtLog;
    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        realm = Realm.getDefaultInstance(); // opens "app.realm"

        //Editext
        etNombre = findViewById(R.id.nombre);
        etEdad = findViewById(R.id.edad);

        //Button
        btnGuardar = findViewById(R.id.btnGuardar);
        btnVer = findViewById(R.id.btnVer);
        btnEliminar = findViewById(R.id.btnEliminar);

        //TextView
        txtLog = findViewById(R.id.textLog);

        //Funcion de cada Button

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    guardar_en_Base(etNombre.getText().toString().trim(), Integer.parseInt(etEdad.getText().toString().trim()));
            }
        });

        btnVer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                actualizar_Datos ();

            }
        });

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Nombre = etNombre.getText().toString();
                
                borrar_de_base(Nombre);

            }
        });
    }


    //Eliminar
    private void borrar_de_base(String Nombre)
    {
        // obtain the results of a query
        final RealmResults<Persona> personas = realm.where(Persona.class).equalTo("Nombre", Nombre).findAll();

        // All changes to data must happen in a transaction
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                personas.deleteAllFromRealm();
            }
        });
    }

    //Ver
    private void actualizar_Datos()
    {
        RealmResults<Persona> result = realm.where(Persona.class).findAllAsync();
        result.load();
        String salida= "";

        for (Persona persona: result)
        {
            salida+=persona.toString();
        }
        txtLog.setText(salida);
    }

    //Guardar
    private void guardar_en_Base(final String Nombre, final int Edad)
    {


        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                Persona persona = bgRealm.createObject(Persona.class);
                persona.setNombre(Nombre);
                persona.setEdad(Edad);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                // Transaction was a success.
                Log.v("Exito", "-----OK----");
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                // Transaction failed and was automatically canceled.
                Log.e("Error", error.getMessage());
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }
}
