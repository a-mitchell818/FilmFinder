package com.fau.movieapptestthree;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RvAdapter extends RecyclerView.Adapter<RvAdapter.MyViewHolder>{

    private LayoutInflater inflater;
    private ArrayList<MovieModel> dataModelArrayList;

    public RvAdapter(Context ctx, ArrayList<MovieModel> dataModelArrayList){

        inflater = LayoutInflater.from(ctx);
        this.dataModelArrayList = dataModelArrayList;
    }

    @Override
    public RvAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.rv_one, parent, false);
        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

    //    @Override
    //    public void onBindViewHolder(RvAdapter.MyViewHolder holder, int position) {
    @Override
    public void onBindViewHolder(final RvAdapter.MyViewHolder holder, final int position) {

        Picasso.get()
                .load(dataModelArrayList
                        .get(position)
                        .getPosterURL())
                .into(holder.iv);
        holder.title.setText(dataModelArrayList.get(position).getTitle());
        holder.year.setText(dataModelArrayList.get(position).getYear());
        holder.movieID.setText(dataModelArrayList.get(position).getImdbID());

        holder.fav_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.fav_btn.setImageResource(R.drawable.love_it);
                Integer x = holder.getAdapterPosition();
                System.out.println(x);
                saveFav(x);
                //.setImageResource(R.drawable.imageview_change_2);

            }
        });

    }



    @Override
    public int getItemCount() {
        return dataModelArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView country, name, city;
        TextView title, year, movieID;
        ImageView iv, fav_btn;

        public MyViewHolder(View itemView) {
            super(itemView);

            year = (TextView) itemView.findViewById(R.id.movie_year);
            title = (TextView) itemView.findViewById(R.id.movie_name);
            movieID = (TextView) itemView.findViewById(R.id.movie_id);
            iv = (ImageView) itemView.findViewById(R.id.imageView4);
            fav_btn = (ImageView) itemView.findViewById(R.id.fav_btn);

         /*   fav_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fav_btn.setImageResource(R.drawable.love_it);
                    //.setImageResource(R.drawable.imageview_change_2);


                }
            });*/

        }

    }

    private void saveFav(int x) {
        FavList favoriteList = new FavList();

        String idMovie = dataModelArrayList.get(x).getImdbID();
        String image = dataModelArrayList.get(x).getPosterURL();
        String name= dataModelArrayList.get(x).getTitle();

        System.out.println(idMovie);
        System.out.println(image);
        System.out.println(name);

        //favoriteList.setId(1);
        favoriteList.setPosterURL(image);
        favoriteList.setTitle(name);

        MainActivity.favDatabase.favoriteDao().addData(favoriteList);
    }


}
