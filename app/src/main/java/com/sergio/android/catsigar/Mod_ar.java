package com.sergio.android.catsigar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.wikitude.architect.ArchitectStartupConfiguration;
import com.wikitude.architect.ArchitectView;
import com.wikitude.common.camera.CameraSettings;

public class Mod_ar extends AppCompatActivity {

    private ArchitectView architectView;
    String idLote;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();

        setContentView(R.layout.mod_ar);



        this.architectView = (ArchitectView)this.findViewById( R.id.architectView );
        final ArchitectStartupConfiguration config = new ArchitectStartupConfiguration();
        config.setCameraResolution(CameraSettings.CameraResolution.AUTO);
        config.setCamera2Enabled(true);
        config.setLicenseKey(getString(R.string.wikitude_license_key));
        this.architectView.onCreate( config );
    }

    @Override
    protected void onPostCreate (Bundle savedInstaceState) {
        super.onPostCreate(savedInstaceState);
        architectView.onPostCreate();
        String idLote = getIntent().getStringExtra("idLote_int");

        try {
            this.architectView.load("file:///android_asset/Object_track/index.html?"+"codpre="+idLote);
        } catch (Exception e){}

    }

    @Override
    protected void onResume() {
        super.onResume();

        architectView.onResume();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();

        architectView.onDestroy();
    }

    @Override
    protected void onPause(){
        super.onPause();

        architectView.onPause();
    }



}
