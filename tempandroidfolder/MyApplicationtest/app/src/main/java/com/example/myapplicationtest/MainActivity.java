package com.example.myapplicationtest;

import androidx.appcompat.app.AppCompatActivity;

import android.app.FragmentTransaction;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.Manifest;
import android.app.Activity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.content.pm.PackageManager;
import android.widget.Button;

import com.example.myapplicationtest.GPSTracker;
import com.example.myapplicationtest.R;



import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {
    private static final int REQ_CODE =10;

    TextToSpeech tts;
    EditText editText;
    private static final int REQUEST_CODE_PERMISSION = 2;
    String mPermission = Manifest.permission.ACCESS_FINE_LOCATION;

    // GPSTracker class
    GPSTracker gps;
    double latitude;
    double longitude;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tts=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    tts.setLanguage(Locale.US);
                }
            }
        });



    }

    public void speak(View view){
        editText = findViewById(R.id.input);

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Need to speak");
        try {
            startActivityForResult(intent, REQ_CODE);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    "Sorry your device not supported",
                    Toast.LENGTH_SHORT).show();
        }

        try {
            if (ActivityCompat.checkSelfPermission(this, mPermission)
                    != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this, new String[]{mPermission},
                        REQUEST_CODE_PERMISSION);

                // If any permission above not allowed by user, this condition will
                // execute every time, else your else part will work
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void track(View view){
        // create class object
        gps = new GPSTracker(MainActivity.this);

        // check if GPS enabled
        if(gps.canGetLocation()) {

            latitude = gps.getLatitude();
            longitude = gps.getLongitude();

            // \n is for new line
            Toast.makeText(getApplicationContext(), "Your Location is - \nLat: "
                    + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();

            final TextView locationT = findViewById(R.id.location);
            final TextView descriptionT = findViewById(R.id.description);
            final TextView tempT = findViewById(R.id.temp);
            final TextView feels_likeT = findViewById(R.id.feelsLike);


            editText = (EditText)findViewById(R.id.input);
            String zipcode = editText.getText().toString();

            RequestQueue queue = Volley.newRequestQueue(this);
            String url = "https://api.openweathermap.org/data/2.5/weather?lat=" + latitude + "&lon=" + longitude + "&units=imperial&appid=f3bec5d262e718b80798b2a7e4c19da8";

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                    (Request.Method.GET, url, null,
                            new Response.Listener<JSONObject>() {

                                @Override
                                public void onResponse(JSONObject response) {


                                    try {
                                        JSONObject main = response.getJSONObject("main");
                                        JSONObject sys = response.getJSONObject("sys");
                                        JSONObject weather = response.getJSONArray("weather").getJSONObject(0);


                                        String city = response.getString("name");
                                        String country = sys.getString("country");
                                        String description = "Today there will be " + weather.getString("description");
                                        String temp = "Temperature: " + main.getString("temp") + "°F";
                                        String feelsLike =  "Feels like: " + main.getString("feels_like") + "°F";


                                        locationT.setText("");
                                        descriptionT.setText("");
                                        tempT.setText("");
                                        feels_likeT.setText("");

                                        locationT.setText("Location: " + city + ", " + country);
                                        descriptionT.setText(description);
                                        tempT.append(temp);
                                        feels_likeT.append(feelsLike);




                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }


                                }
                            }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // TODO: Handle error

                        }
                    });
            queue.add(jsonObjectRequest);


            String toSpeak = locationT.getText().toString();
            Toast.makeText(getApplicationContext(), toSpeak,Toast.LENGTH_SHORT).show();
            tts.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);


        }else{
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gps.showSettingsAlert();
        }
    }





    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQ_CODE: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    editText.setText((String)result.get(0));
                }
                break;
            }
        }
    }


    public void requestJSON(View view){
        final TextView locationT = findViewById(R.id.location);
        final TextView descriptionT = findViewById(R.id.description);
        final TextView tempT = findViewById(R.id.temp);
        final TextView feels_likeT = findViewById(R.id.feelsLike);


        editText = (EditText)findViewById(R.id.input);
        String zipcode = editText.getText().toString();

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://api.openweathermap.org/data/2.5/weather?zip=" + zipcode + "&units=imperial&appid=f3bec5d262e718b80798b2a7e4c19da8";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null,
                        new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {


                                try {
                                    JSONObject main = response.getJSONObject("main");
                                    JSONObject sys = response.getJSONObject("sys");
                                    JSONObject weather = response.getJSONArray("weather").getJSONObject(0);


                                    String city = response.getString("name");
                                    String country = sys.getString("country");
                                    String description = "Today there will be " + weather.getString("description");
                                    String temp = "Temperature: " + main.getString("temp") + "°F";
                                    String feelsLike =  "Feels like: " + main.getString("feels_like") + "°F";

                                    locationT.setText("");
                                    descriptionT.setText("");
                                    tempT.setText("");
                                    feels_likeT.setText("");


                                    locationT.setText("Location: " + city + ", " + country);
                                    descriptionT.setText(description);
                                    tempT.setText(temp);
                                    feels_likeT.setText(feelsLike);

                                    String toSpeak = locationT.getText().toString();
                                    Toast.makeText(getApplicationContext(), toSpeak,Toast.LENGTH_SHORT).show();
                                    tts.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }


                            }
                        }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error

                    }
                });
        queue.add(jsonObjectRequest);



    }


}


