package com.demotxt.myapp.myapplication.activities;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;


import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.demotxt.myapp.myapplication.R;
import com.demotxt.myapp.myapplication.adapters.RecyclerViewAdapter;
import com.demotxt.myapp.myapplication.model.Book;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class about extends AppCompatActivity {

    private final String JSON_URL = "https://gist.githubusercontent.com/Joseph-beep-tech/38014563cab12e177009a3ff4a53703c/raw/6fecdd67c93a54bc87a5792920d252d3c1d15fbc/Library.json" ;

    private JsonArrayRequest request ;
    private RequestQueue requestQueue ;
    private List<Book> lstBook;
    private RecyclerView recyclerView ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        lstBook = new ArrayList<>() ;
        recyclerView = findViewById(R.id.recyclerviewid);
        jsonrequest();



    }

    private void jsonrequest() {

        request = new JsonArrayRequest(JSON_URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                JSONObject jsonObject  = null ;

                for (int i = 0 ; i < response.length(); i++ ) {


                    try {
                        jsonObject = response.getJSONObject(i) ;
                        Book book = new Book() ;
                        book.setName(jsonObject.getString("name"));
                        book.setDescription(jsonObject.getString("description"));
                        book.setRating(jsonObject.getString("Rating"));
                        book.setCategorie(jsonObject.getString("categorie"));
                        book.setNb_episode(jsonObject.getInt("episode"));
                        book.setStudio(jsonObject.getString("studio"));
                        book.setImage_url(jsonObject.getString("img"));
                        lstBook.add(book);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }

                setuprecyclerview(lstBook);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });


        requestQueue = Volley.newRequestQueue(about.this);
        requestQueue.add(request) ;


    }

    private void setuprecyclerview(List<Book> lstBook) {


        RecyclerViewAdapter myadapter = new RecyclerViewAdapter(this, lstBook) ;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(myadapter);

    }


}
