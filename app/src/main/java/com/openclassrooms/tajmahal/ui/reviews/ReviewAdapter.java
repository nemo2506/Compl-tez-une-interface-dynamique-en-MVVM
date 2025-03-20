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

import com.bumptech.glide.Glide;
import com.google.android.material.imageview.ShapeableImageView;
import com.openclassrooms.tajmahal.R;
import com.openclassrooms.tajmahal.domain.model.Review;

import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {
    private final List<Review> reviewList;

    public ReviewAdapter(List<Review> reviewList) {
        this.reviewList = reviewList;
    }

    // Crée une nouvelle vue (View) pour un élément de la liste, en utilisant le fichier XML reviews.xml
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reviews, parent, false);
        return new ViewHolder(view);
    }

    // Associe chaque élément Review aux composants de la vue (ViewHolder)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Review review = reviewList.get(position);
        Glide.with(holder.itemView.getContext())
            .load(review.getPicture())
            .into(holder.reviewPicture);
        holder.reviewName.setText(review.getUsername());
        holder.reviewBar.setRating(review.getRate());
        holder.reviewText.setText(review.getComment());
    }

    // permet au RecyclerView de savoir combien d’éléments afficher
    @Override
    public int getItemCount() {
        return reviewList.size();
    }

    // Définit la structure d’un élément de la liste (RecyclerView)
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView reviewName, reviewText;
        RatingBar reviewBar;
        ShapeableImageView reviewPicture;

        // contient des références aux composants graphiques
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            reviewName = itemView.findViewById(R.id.reviewName);
            reviewBar = itemView.findViewById(R.id.reviewBar);
            reviewText = itemView.findViewById(R.id.reviewText);
            reviewPicture = itemView.findViewById(R.id.reviewPicture);
        }
    }
}
