package com.example.weatheapp;

import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private RequestQueue queue;
    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    private TextView temp;
    private TextView minTemp;
    private TextView maxTemp;
    private TextView humidity;
    private TextView cityText;
   // private static final String APPI_KEY = "179f48e2a75510a77c55de8f02e18af2";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        queue = Volley.newRequestQueue(this);
        Objects.requireNonNull(getSupportActionBar()).setElevation(0);

        FloatingActionButton fab = findViewById(R.id.fab);


        temp = findViewById(R.id.temperature);
        minTemp = findViewById(R.id.temperature_min);
        maxTemp = findViewById(R.id.temperature_max);
        humidity = findViewById(R.id.humidity);
        cityText = findViewById(R.id.city_text);





         getWeather("Enugu");




        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchDialog();
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();


            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            searchDialog();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void searchDialog(){
        builder = new AlertDialog.Builder(this);

        View view = getLayoutInflater().inflate(R.layout.search,null);

        final EditText searchText = view.findViewById(R.id.search_text);
        Button submit = view.findViewById(R.id.submit_search);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getWeather(searchText.getText().toString());

                dialog.dismiss();

            }
        });

        builder.setView(view);
        dialog = builder.create();
        dialog.show();


    }

    public void getWeather(String city){

        cityText.setText(city);
        String url = "http://api.openweathermap.org/data/2.5/weather?q="+city+"&units=metric&appid=179f48e2a75510a77c55de8f02e18af2&lang=en";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                // Log.d("JASON", "onResponse: "+ response.toString());

                JSONObject main = null;
                try {
                    main = response.getJSONObject("main");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    temp.setText("Temp: "+main.getString("temp")+"C");
                    minTemp.setText("Mim_Temp: "+main.getString("temp_min")+"C");
                    humidity.setText("Max_Temp: "+main.getString("temp_max")+"C");
                    maxTemp.setText("Humidity: "+main.getString("humidity"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {



            }
        });
        queue.add(jsonObjectRequest);

    }
}
