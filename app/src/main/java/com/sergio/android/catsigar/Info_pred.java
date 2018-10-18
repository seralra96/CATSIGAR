package com.sergio.android.catsigar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.jetbrains.annotations.Nullable;

/**
 * Created by Administrador on 11/04/2016.
 */
public class Info_pred extends Activity {
    String idLote;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_pred);
        String idLote = getIntent().getStringExtra("idLote_int");
        TextView idLote_T = (TextView)findViewById(R.id.get_codlot_);
        idLote_T.setText(idLote);


    }

    public void launchAR(View view) {

        String idLote = getIntent().getStringExtra("idLote_int");
        Intent intent = new Intent(this, Mod_ar.class);
        //String message = mMessageEditText.getText().toString();
        intent.putExtra("idLote_int",idLote);
        Toast.makeText(getApplicationContext(), "Se envía el valor de codigo predial "+idLote , Toast.LENGTH_LONG).show();

        //intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

}
