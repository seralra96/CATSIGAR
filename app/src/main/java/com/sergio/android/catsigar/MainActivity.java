package com.sergio.android.catsigar;


import android.app.Activity;
import android.content.Intent;

import android.os.Bundle;

import android.view.MenuItem;
import android.view.View;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }



/*
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
*/
    public void mod_geo(View view) {

        Intent getNameScreenIntent = new Intent(this,
                Mod_geo.class);

        final int result = 1;
        getNameScreenIntent.putExtra("callingActivity", "MainActivity");
        startActivityForResult(getNameScreenIntent, result);
    }


/*
    public void sendintent (View v){

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        startActivity(intent);

    }*/

}
