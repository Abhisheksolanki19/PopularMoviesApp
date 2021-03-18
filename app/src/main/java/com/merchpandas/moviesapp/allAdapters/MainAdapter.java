package com.merchpandas.moviesapp.allAdapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.merchpandas.moviesapp.MovieDetailActivity;
import com.merchpandas.moviesapp.POJO.Moviespojo;
import com.merchpandas.moviesapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;


public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MyViewHolder> {

    private Context mContext;
    private List<Moviespojo> mMovieDetailsList;

    public MainAdapter(Context context, List<Moviespojo> movieDetailsList) {
        mContext = context;
        mMovieDetailsList = movieDetailsList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        view = layoutInflater.inflate(R.layout.item_layout, parent, false);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        holder.title.setText(mMovieDetailsList.get(position).getMovieTitle());
        holder.rating.setText(mMovieDetailsList.get(position).getMovieRatings());
        final String link = "https://image.tmdb.org/t/p/w500/" + mMovieDetailsList.get(position).getMovieImage();

        Picasso.get().load(link).placeholder(R.drawable.load5).into(holder.imageView);
        holder.imageView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, MovieDetailActivity.class);
                Moviespojo movieDetails = mMovieDetailsList.get(position);

                intent.putExtra("title", movieDetails.getMovieTitle());
                intent.putExtra("rating", movieDetails.getMovieRatings());
                intent.putExtra("overview", movieDetails.getMovieSynopsis());
                intent.putExtra("date", movieDetails.getMovieDate());
                intent.putExtra("image", link);
                intent.putExtra("id", movieDetails.getMovieId());
                mContext.startActivity(intent);
            }
        });

    }


    @Override
    public int getItemCount() {
        return mMovieDetailsList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView rating;
        ImageView imageView;

        public MyViewHolder(View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.movie_title);
            rating = itemView.findViewById(R.id.movie_rating);
            imageView = itemView.findViewById(R.id.movie_image);


        }

    }
}
