package com.example.final_project;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import android.widget.SearchView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class MainPage extends AppCompatActivity {

    private TextView gInfected, gRecovered, gDeaths, usInfected, usRecovered, usDeaths,
    news1, news2;


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        gInfected = findViewById(R.id.gInfected);
        gDeaths = findViewById(R.id.gDeaths);
        gRecovered = findViewById(R.id.gRecovered);
        usInfected = findViewById(R.id.usInfected);
        usDeaths = findViewById(R.id.usDeaths);
        usRecovered = findViewById(R.id.usRecovered);
        news1 = findViewById(R.id.news1);
        news2 = findViewById(R.id.news2);

        Stats();
        News();

        SearchView simpleSearchView = findViewById(R.id.countries);
        CharSequence query = simpleSearchView.getQueryHint();

    }

    public void MoreNews(View view){
        Intent intent = new Intent(MainPage.this, MoreNews.class);
        startActivity(intent);
    }

    public void MoreStates(View view){
        Intent intent = new Intent(MainPage.this, MoreStates.class);
        startActivity(intent);
    }

    public void MoreGlobal(View view){
        Intent intent = new Intent(MainPage.this, MoreGlobal.class);
        startActivity(intent);
    }

    public void News(){
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://newsapi.org/v2/everything?q=COVID&language=en&sortBy=publishedAt&apiKey=926c3c514983474bbb01decaa3ea3afe&pageSize=10&page=1";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null,
                        new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {

                                try {
                                    JSONObject article1 = response.getJSONArray("articles").getJSONObject(0);
                                    JSONObject article2 = response.getJSONArray("articles").getJSONObject(1);

                                    String articles1 = "Title: " + article1.getString("title") + "\n" +
                                            "Author: " + article1.getString("author") + "\n";

                                    String articles2 = "Title: " + article2.getString("title") + "\n" +
                                            "Author: " + article2.getString("author") + "\n";


                                    news1.setText(articles1);
                                    news2.setText(articles2);

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

    public void Stats(){
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://api.covid19api.com/summary";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null,
                        new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {

                                try {
                                    JSONObject global = response.getJSONObject("Global");
                                    JSONObject us = response.getJSONArray("Countries").getJSONObject(236);

                                    String Ginfected = global.getString("TotalConfirmed");
                                    String Gdeath = global.getString("TotalDeaths");
                                    String Grecovered = global.getString("TotalRecovered");
                                    String USinfected = us.getString("TotalConfirmed");
                                    String USdeath = us.getString("TotalDeaths");
                                    String USrecovered = us.getString("TotalRecovered");

                                    gInfected.setText("Global Infected: " + Ginfected);
                                    gRecovered.setText("Global Recovered: " + Grecovered);
                                    gDeaths.setText("Global Deaths: " + Gdeath);
                                    usInfected.setText("U.S Infected: " + USinfected);
                                    usRecovered.setText("U.S Recovered: " + USrecovered);
                                    usDeaths.setText("U.S Deaths: " + USdeath);

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
