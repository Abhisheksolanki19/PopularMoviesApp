package com.merchpandas.moviesapp.allAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.merchpandas.moviesapp.POJO.Reviewpojo;
import com.merchpandas.moviesapp.R;

import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewHolder> {
    private List<Reviewpojo> mList;
    private Context mContext;

    public ReviewAdapter(List<Reviewpojo> list, Context context) {
        mList = list;
        mContext = context;
    }

    @NonNull
    @Override
    public ReviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.review, parent, false);
        return new ReviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewHolder holder, int position) {

        holder.author.setText("Author: " + mList.get(position).getReviewAuthor());
        holder.content.setText(mList.get(position).getReviewDetail());

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ReviewHolder extends RecyclerView.ViewHolder {

        private TextView author;
        private TextView content;

        public ReviewHolder(@NonNull View itemView) {
            super(itemView);
            author = itemView.findViewById(R.id.author_name);
            content = itemView.findViewById(R.id.review_content);

        }
    }
}
