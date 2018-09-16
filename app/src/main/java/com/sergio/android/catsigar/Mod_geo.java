package com.sergio.android.catsigar;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Scanner;
import com.google.maps.android.geojson.GeoJsonFeature;
import com.google.maps.android.geojson.GeoJsonLayer;
import com.google.maps.android.geojson.GeoJsonPointStyle;
import com.google.maps.android.ui.BubbleIconFactory;

/**
 * Created by SergioAlexander on 06/05/2015.
 */
public class Mod_geo extends AppCompatActivity {

    private GoogleMap googleMap;
    private GeoJsonLayer layer;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    final String mGeoJsonUrl
            = "https://ide.proadmintierra.info/geoserver/catsigar/ows?service=WFS&version=1.0.0&request=GetFeature&typeName=catsigar:pdom2&outputFormat=application%2Fjson";


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_geo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.hybrid:
                googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                break;
            case R.id.satellite:
                googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                break;
            case R.id.maps:
                googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                break;
            case R.id.terrain:
                googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mod_geo);
        Intent activityThatCalled = getIntent();




        try {
            if (googleMap == null) {
                googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.modgeo)).getMap();
            }

            googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
            googleMap.setMyLocationEnabled(true);
            googleMap.setTrafficEnabled(true);
            googleMap.setIndoorEnabled(true);
            googleMap.setBuildingsEnabled(true);
            googleMap.getUiSettings().setZoomControlsEnabled(true);
            googleMap.getUiSettings().setAllGesturesEnabled(true);
            googleMap.getUiSettings().setMapToolbarEnabled(true);
            googleMap.getUiSettings().setCompassEnabled(true);
            CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(4.626205, -74.082174));
            CameraUpdate zoom = CameraUpdateFactory.zoomTo(18);
            googleMap.moveCamera(center);
            googleMap.animateCamera(zoom);
            googleMap.setOnInfoWindowClickListener(MyOnInfoWindowClickListener);

        } catch (Exception e) {
            e.printStackTrace();
        }

/*
        try {
            layer = new GeoJsonLayer(getMap(), R.raw.pdom, getApplicationContext());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }



        addInfoPoints();
        layer.addLayerToMap();
*/
        DownloadGeoJsonFile downloadGeoJsonFile = new DownloadGeoJsonFile();
        // Download the GeoJSON file
        downloadGeoJsonFile.execute(mGeoJsonUrl);

    }

    GoogleMap.OnInfoWindowClickListener MyOnInfoWindowClickListener
            = new GoogleMap.OnInfoWindowClickListener(){
        @Override
        public void onInfoWindowClick(Marker marker) {
            Toast.makeText(Mod_geo.this,
                    "onInfoWindowClick():\n" +
                            marker.getId() + "\n" +
                            marker.getPosition().longitude + "\n" +
                            marker.getSnippet(),

                    Toast.LENGTH_LONG).show();
            String idLote_int = marker.getSnippet();
            Intent intent = new Intent(Mod_geo.this,Info_pred.class);
            intent.putExtra("idLote_int",idLote_int);
            startActivity(intent);
        }
    };


    private void addInfoPoints(){
        //Iteración sobre cada uno de los features del layer
        for (GeoJsonFeature feature : layer.getFeatures()){
            //Verifica si existen los atributos
            if (feature.hasProperty("pdotexto")&& feature.hasProperty("pdoclote")){
                //String cLotp = feature.getProperty("PDoTexto");
                // String cLote = feature.getProperty("PDoCLote");
                BitmapDescriptor pointIcon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE);
                //Crea un estilo de punto
                GeoJsonPointStyle pointStyle = new GeoJsonPointStyle();
                //setea las opciones para el estilo de los puntos
                pointStyle.setTitle(feature.getProperty("pdotexto"));
                pointStyle.setSnippet(feature.getProperty("pdoclote"));
                pointStyle.setIcon(pointIcon);
                //Asigna el estilo creado a cada característica
                feature.setPointStyle(pointStyle);
            }
        }
    }

    private class DownloadGeoJsonFile extends AsyncTask<String, Void, JSONObject> {
        final String mLogTag = "GeoJsonDemo";
        @Override
        protected JSONObject doInBackground(String... params) {
            try {
                // Open a stream from the URL
                InputStream stream = new URL(params[0]).openStream();

                String line;
                StringBuilder result = new StringBuilder();
                BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

                while ((line = reader.readLine()) != null) {
                    // Read and save each line of the stream
                    result.append(line);
                }

                // Close the stream
                reader.close();
                stream.close();

                // Convert result to JSONObject
                return new JSONObject(result.toString());
            } catch (IOException e) {
                Log.e(mLogTag, "GeoJSON file could not be read");
            } catch (JSONException e) {
                Log.e(mLogTag, "GeoJSON file could not be converted to a JSONObject");
            }
            return null;
        }


        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            if (jsonObject != null) {
                // Create a new GeoJsonLayer, pass in downloaded GeoJSON file as JSONObject
                layer = new GeoJsonLayer(getMap(), jsonObject);
                // Add the layer onto the map
                addInfoPoints();
                layer.addLayerToMap();
            }
        }

    }
        protected GoogleMap getMap() {
            return googleMap;
        }





}
