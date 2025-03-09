/*
 * Copyright (c) 2025. Copyright © 2025 Taj Mahal. Tous droits réservés.
 *
 * Cette application, y compris son code source, sa conception, son contenu et ses fonctionnalités, est la propriété exclusive de Taj Mahal.
 * Toute reproduction, distribution ou utilisation non autorisée, en tout ou en partie, est strictement interdite sans l'accord écrit préalable de Taj Mahal.
 *
 * Développé par Marc Navarro pour Taj Mahal.
 */

package com.openclassrooms.tajmahal.ui.reviews;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.openclassrooms.tajmahal.R;
import com.openclassrooms.tajmahal.domain.model.Review;

import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {
    private final List<Review> reviewList;

    public ReviewAdapter(List<Review> reviewList) {
        this.reviewList = reviewList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reviews, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Review review = reviewList.get(position);
        holder.reviewName.setText(review.getUsername());
        holder.reviewBar.setRating(review.getRate());
        holder.reviewText.setText(review.getComment());
    }

    @Override
    public int getItemCount() {
        return reviewList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView reviewName, reviewText;
        RatingBar reviewBar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            reviewName = itemView.findViewById(R.id.reviewName);
            reviewBar = itemView.findViewById(R.id.reviewBar);
            reviewText = itemView.findViewById(R.id.reviewText);
        }
    }
}
