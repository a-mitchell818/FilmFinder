package com.fau.movieapptestthree;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class FavoritesActivity extends AppCompatActivity {
    private static FavDatabase favDatabase3;
    private RecyclerView rv;
    FavAdapter adapter;
    List<FavList> favArrayList;
    FavViewModel favViewModel;
    ImageView FavMovies, home, logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        FavMovies = (ImageView) findViewById(R.id.imageView11);
        FavMovies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inToFavs = new Intent(getApplicationContext(), FavoritesActivity.class);
                startActivity(inToFavs);
                finish();
            }
        });

        logout = (ImageView) findViewById(R.id.imageView10);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inToLogin = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(inToLogin);
                finish();
            }
        });
        home = (ImageView) findViewById(R.id.imageView9);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inToLogin = new Intent(getApplicationContext(), Main3Activity.class);
                startActivity(inToLogin);
                finish();
            }
        });


        rv=(RecyclerView)findViewById(R.id.recyclerFav);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));

        getFavData();
    }

    private void getFavData() {
        final List<FavList> favoriteLists=Main3Activity.favDatabase.favoriteDao().getFavoriteData();
        adapter=new FavAdapter(favoriteLists,getApplicationContext());
        rv.setAdapter(adapter);

        //new DELETE
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                //FavList favoriteList = new FavList();
                Main3Activity.favDatabase.favoriteDao().delete(adapter.getFavAt(viewHolder.getAdapterPosition()));
                adapter.notifyDataSetChanged();
                Toast.makeText(getApplicationContext(),"delete",Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(rv);
    }

    //adapter = new FavAdapter(this,dataModelArrayList);
  /*  private void getFavData() {

        FavAdapter adapter = new FavAdapter(this, favArrayList);
        assert favArrayList !=null;
        //adapter=new FavAdapter(favoriteLists,getApplicationContext());
        rv.setAdapter(adapter);
    }*/

}

//GITHUB
//https://gist.github.com/a-mitchell818/711e68e62a9b3b21df1f43baf9480faa
