package com.example.forecast;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    TextView tv;
    EditText et;
    String city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        tv = findViewById(R.id.tv);
        et = findViewById(R.id.et);


        findViewById(R.id.getInfoButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getWeather();
            }
        });
    }

    private void getWeather() {

        city = et.getText().toString();


        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());


        String url = "https://api.openweathermap.org/data/2.5/weather?q=" + city.toLowerCase() + "&appid=fff43c79a92585b1a599a429070acccf";


        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject object = response.getJSONObject("main");
                    double temperature = object.getDouble("temp")-273.15;
                    String humid=object.getString("humidity");
                    JSONObject windS=response.getJSONObject("wind");
                    String windSpeed=windS.getString("speed");

                    tv.setText("Temperature: " +String.format("%.2f", temperature)+"°C\nHumidity: "+humid+"%\nWind-Speed: "+windSpeed+"m/s");

                } catch (JSONException e) {
                    e.printStackTrace();
                    tv.setText("Error parsing JSON");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                tv.setText("Error fetching weather data");
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        });


        queue.add(request);
    }
}
