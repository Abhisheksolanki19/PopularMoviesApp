package com.merchpandas.moviesapp.allAdapters;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.merchpandas.moviesapp.R;

import java.util.ArrayList;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {

    private ArrayList<String> movieLinkList;
    private Context mContext;

    public VideoAdapter(ArrayList<String> movieLinkList, Context context) {
        this.movieLinkList = movieLinkList;
        mContext = context;
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.teaser, parent, false);
        return new VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, final int position) {
        holder.trailerView.setText("Trailer " + String.valueOf(position + 1));
        holder.trailerView.setOnClickListener(new View.OnClickListener() {
            String id = movieLinkList.get(position);

            @Override
            public void onClick(View v) {

                Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(id));
                try {
                    mContext.startActivity(webIntent);
                } catch (ActivityNotFoundException ex) {
                    mContext.startActivity(webIntent);
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return movieLinkList.size();
    }

    public class VideoViewHolder extends RecyclerView.ViewHolder {
        private TextView trailerView;

        public VideoViewHolder(@NonNull View itemView) {
            super(itemView);
            trailerView = itemView.findViewById(R.id.trailer_textView);


        }
    }
}
