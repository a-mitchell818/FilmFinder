package com.fau.movieapptestthree;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private String URLstring = "https://www.omdbapi.com/?&apikey=1392019e&s=Star Wars";
    private static ProgressDialog mProgressDialog;
    ArrayList<MovieModel> dataModelArrayList;
    private RvAdapter rvAdapter;
    private RecyclerView recyclerView;
    EditText movieInfo;
    Button Search;

    //SETUP Fav DB
    public static FavDatabase favDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        recyclerView = findViewById(R.id.recycler);

        //fetchingJSON();

        Search = (Button) findViewById(R.id.button3);
        Search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movieInfo = (EditText) findViewById(R.id.editText2);
                String message = movieInfo.getText().toString();
                System.out.println("Entered "+message);
                fetchingJSONTwo(message);
            }
        });

        favDatabase = Room.databaseBuilder(getApplicationContext(),FavDatabase.class,"myfavdb").allowMainThreadQueries().build();

    }

    private void fetchingJSON() {

        showSimpleProgressDialog(this, "Loading...","Fetching Json",false);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLstring,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("strrrrr", ">>" + response);

                        try {

                            removeSimpleProgressDialog();

                            JSONObject obj = new JSONObject(response);
                            //if(obj.optString("status").equals("true")){

                                dataModelArrayList = new ArrayList<>();
                                JSONArray dataArray  = obj.getJSONArray("Search");

                                for (int i = 0; i < dataArray.length(); i++) {

                                    MovieModel playerModel = new MovieModel();
                                    JSONObject dataobj = dataArray.getJSONObject(i);

                                    playerModel.setTitle(dataobj.getString("Title"));
                                    playerModel.setYear(dataobj.getString("Year"));
                                    playerModel.setImdbID(dataobj.getString("imdbID"));
                                    playerModel.setPosterURL(dataobj.getString("Poster"));

                                    dataModelArrayList.add(playerModel);

                                }

                                setupRecycler();

                            //}

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //displaying the error in toast if occurrs
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        // request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        requestQueue.add(stringRequest);


    }

    private void fetchingJSONTwo(String info) {
        //String URLstringTwo = "https://www.omdbapi.com/?&apikey=1392019e&s=Star Wars";
        String urlString= "https://www.omdbapi.com/?&apikey=1392019e&s="+info;
        URL myURL;

        {
            try {
                myURL = new URL(urlString);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }


        showSimpleProgressDialog(this, "Loading...","Fetching Json",false);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, urlString,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("strrrrr", ">>" + response);

                        try {

                            removeSimpleProgressDialog();

                            JSONObject obj = new JSONObject(response);
                            //if(obj.optString("status").equals("true")){

                            dataModelArrayList = new ArrayList<>();
                            JSONArray dataArray  = obj.getJSONArray("Search");

                            for (int i = 0; i < dataArray.length(); i++) {

                                MovieModel playerModel = new MovieModel();
                                JSONObject dataobj = dataArray.getJSONObject(i);

                                playerModel.setTitle(dataobj.getString("Title"));
                                playerModel.setYear(dataobj.getString("Year"));
                                playerModel.setImdbID(dataobj.getString("imdbID"));
                                playerModel.setPosterURL(dataobj.getString("Poster"));

                                dataModelArrayList.add(playerModel);

                            }

                            setupRecycler();

                            //}

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //displaying the error in toast if occurrs
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        // request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        requestQueue.add(stringRequest);


    }




    private void setupRecycler(){

        rvAdapter = new RvAdapter(this,dataModelArrayList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(rvAdapter);
        //recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        //Added by ME the item Decoration divider
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        //ViewCompat.setNestedScrollingEnabled(recyclerView, true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));

    }

    public static void removeSimpleProgressDialog() {
        try {
            if (mProgressDialog != null) {
                if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                    mProgressDialog = null;
                }
            }
        } catch (IllegalArgumentException ie) {
            ie.printStackTrace();

        } catch (RuntimeException re) {
            re.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void showSimpleProgressDialog(Context context, String title,
                                                String msg, boolean isCancelable) {
        try {
            if (mProgressDialog == null) {
                mProgressDialog = ProgressDialog.show(context, title, msg);
                mProgressDialog.setCancelable(isCancelable);
            }

            if (!mProgressDialog.isShowing()) {
                mProgressDialog.show();
            }

        } catch (IllegalArgumentException ie) {
            ie.printStackTrace();
        } catch (RuntimeException re) {
            re.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
