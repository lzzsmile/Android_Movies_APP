package zhuangzhi.android.movies;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import zhuangzhi.android.movies.network.Review;

/**
 * Created by zhuangzhili on 2018-03-26.
 */

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {

    private ArrayList<Review> reviews;

    public ReviewAdapter(ArrayList<Review> reviews) {this.reviews = reviews;}

    @Override
    public ReviewAdapter.ReviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layoutId = R.layout.review_list_item;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(layoutId, parent, false);
        ReviewViewHolder viewHolder = new ReviewViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ReviewViewHolder holder, int position) {
        final Review review = reviews.get(position);
        holder.reviewAuthor.setText(review.getAuthor());
        holder.reviewContent.setText(review.getContent());
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.getContext().startActivity(
                        new Intent(Intent.ACTION_VIEW, Uri.parse(review.getUrl())));
            }
        });
    }


    @Override
    public int getItemCount() {
        return reviews.size();
    }

    public void addAll(List<Review> data) {
        if (reviews.size() > 0) {
            reviews.clear();
        }
        reviews.addAll(data);
        notifyDataSetChanged();
    }

    class ReviewViewHolder extends RecyclerView.ViewHolder{

        final TextView reviewAuthor;
        final TextView reviewContent;
        final View mView;

        ReviewViewHolder(View itemView) {
            super(itemView);
            this.mView = itemView;
            reviewAuthor = itemView.findViewById(R.id.review_author);
            reviewContent = itemView.findViewById(R.id.review_content);
        }
    }

}
