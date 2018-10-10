package com.sergio.android.catsigar;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.google.gson.JsonObject;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.data.geojson.GeoJsonFeature;
import com.google.maps.android.data.geojson.GeoJsonLayer;
import com.google.maps.android.data.geojson.GeoJsonPointStyle;

/**
 * Created by SergioAlexander on 06/05/2015.
 */
public class Mod_geo extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap googleMap;
    private GeoJsonLayer layer;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    final String mGeoJsonUrl
            = "https://ide.proadmintierra.info/geoserver/catsigar/ows?service=WFS&version=1.0.0&request=GetFeature&typeName=catsigar:pdom2&outputFormat=application%2Fjson";

    private ClusterManager mClusterManager;

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
                getMap().setMapType(GoogleMap.MAP_TYPE_HYBRID);
                break;
            case R.id.satellite:
                getMap().setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                break;
            case R.id.maps:
                getMap().setMapType(GoogleMap.MAP_TYPE_NORMAL);
                break;
            case R.id.terrain:
                getMap().setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mod_geo);
        Intent activityThatCalled = getIntent();
        setUpMap();
    }


    private void setUpMap() {

        ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.modgeo)).getMapAsync(this);
    }

    protected void startClustering(){
        try {


            getMap().setMapType(GoogleMap.MAP_TYPE_HYBRID);
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            getMap().setMyLocationEnabled(true);
            getMap().setTrafficEnabled(true);
            getMap().setIndoorEnabled(true);
            getMap().setBuildingsEnabled(true);
            getMap().getUiSettings().setZoomControlsEnabled(true);
            getMap().getUiSettings().setAllGesturesEnabled(true);
            getMap().getUiSettings().setMapToolbarEnabled(true);
            getMap().getUiSettings().setCompassEnabled(true);
            CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(4.626205, -74.082174));
            CameraUpdate zoom = CameraUpdateFactory.zoomTo(16);
            getMap().moveCamera(center);
            getMap().animateCamera(zoom);
            getMap().setOnInfoWindowClickListener(MyOnInfoWindowClickListener);

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


        mClusterManager = new ClusterManager<MyItem>(this,  getMap());
        DownloadGeoJsonFile downloadGeoJsonFile = new DownloadGeoJsonFile();
        // Download the GeoJSON file
        downloadGeoJsonFile.execute(mGeoJsonUrl);

        getMap().setOnCameraIdleListener(mClusterManager);

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

    @Override
    public void onMapReady(GoogleMap map) {
        if (googleMap != null) {
            return;
        }
        googleMap = map;
        startClustering();
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

                List<MyItem> items = new ArrayList<MyItem>();
                JSONObject json = new JSONObject(result.toString());
                Log.d(mLogTag, json.toString());


                JSONArray array = json.getJSONArray("features");

                for (int i = 0 ;  i < 1000; i++ ){
                    JSONObject object = array.getJSONObject(i);

                    JSONObject  geometria = object.getJSONObject("geometry");
                    JSONArray coordenadas = geometria.getJSONArray("coordinates");
                    String title = null;
                    String snippet = null;
                    double lat = coordenadas.getDouble(1);
                    double lng = coordenadas.getDouble(0);

                    title = object.getJSONObject("properties").getString("pdoclote");
                    snippet = object.getJSONObject("properties").getString("pdotexto");

                    items.add(new MyItem(lat, lng, title, snippet));
                    Log.d(mLogTag, "añadiendo !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!" + lat + " - " + lng+ " - " + title+ " - " +  snippet);
                    mClusterManager.addItems(items);
                }
                Log.d(mLogTag, "termino!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                // Convert result to JSONObject
                //return new JSONObject(result.toString());
            } catch (IOException e) {
                Log.e(mLogTag, "GeoJSON file could not be read" + e.getMessage());
            } catch (JSONException e) {
                Log.e(mLogTag, "GeoJSON file could not be converted to a JSONObject" + e.getMessage());
            }
            return null;
        }


        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            /*
            if (jsonObject != null) {
                // Create a new GeoJsonLayer, pass in downloaded GeoJSON file as JSONObject
                layer = new GeoJsonLayer(getMap(), jsonObject);
                // Add the layer onto the map
                addInfoPoints();
                layer.addLayerToMap();
            }*/

        }

    }
        protected GoogleMap getMap() {
            return googleMap;
        }





}
