package com.example.final_project;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MoreNews extends AppCompatActivity {

    private RecyclerView nList;
    private LinearLayoutManager linearLayoutManager;
    private DividerItemDecoration dividerItemDecoration;
    private List<News> newsList;
    private RecyclerView.Adapter adapter;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_news);

        nList = findViewById(R.id.newsList);
        newsList = new ArrayList<>();
        adapter = new Adapter(getApplicationContext(),newsList);

        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        dividerItemDecoration = new DividerItemDecoration(nList.getContext(), linearLayoutManager.getOrientation());

        nList.setHasFixedSize(true);
        nList.setLayoutManager(linearLayoutManager);
        nList.addItemDecoration(dividerItemDecoration);
        nList.setAdapter(adapter);

        News();
    }

    public void News(){
        String url = "https://newsapi.org/v2/everything?q=COVID&language=en&sortBy=publishedAt&apiKey=926c3c514983474bbb01decaa3ea3afe&pageSize=100&page=1";
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null,
                        new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {

                                for (int i = 0; i < response.length(); i++) {
                                    try {
                                        JSONObject event = response.getJSONArray("articles").getJSONObject(i);

                                        News news = new News();

                                        news.setTitle(event.getString("title"));
                                        news.setLink(event.getString("url"));
                                        news.setDescription(event.getString("description"));

                                        newsList.add(news);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    adapter.notifyDataSetChanged();
                                    progressDialog.dismiss();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley", error.toString());
                        progressDialog.dismiss();
                    }
                });
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jsonObjectRequest);
    }
}
