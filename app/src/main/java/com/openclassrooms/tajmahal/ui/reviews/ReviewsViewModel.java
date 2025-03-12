/*
 * Copyright (c) 2025. Copyright © 2025 Taj Mahal. Tous droits réservés.
 *
 * Cette application, y compris son code source, sa conception, son contenu et ses fonctionnalités, est la propriété exclusive de Taj Mahal.
 * Toute reproduction, distribution ou utilisation non autorisée, en tout ou en partie, est strictement interdite sans l'accord écrit préalable de Taj Mahal.
 *
 * Développé par Marc Navarro pour Taj Mahal.
 */

package com.openclassrooms.tajmahal.ui.reviews;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.openclassrooms.tajmahal.data.repository.RestaurantRepository;
import com.openclassrooms.tajmahal.data.service.User;
import com.openclassrooms.tajmahal.domain.model.Restaurant;
import com.openclassrooms.tajmahal.domain.model.Review;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class ReviewsViewModel extends ViewModel {

    private final RestaurantRepository restaurantRepository;
    private final MutableLiveData<ArrayList<Review>> liveReviews = new MutableLiveData<>(new ArrayList<>());


    @Inject
    public ReviewsViewModel(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
        liveReviews.setValue(new ArrayList<>(restaurantRepository.getReviews()));
    }

    public User getTajMahalUser() {
        return restaurantRepository.getUser();
    }

    public Restaurant getTajMahalRestaurant() {
        return restaurantRepository.getRestaurant().getValue();
    }


    /**
     * Retrieves Actuals Reviews under MutableLiveData.
     * <p>
     * Fake User saving his review
     * </p>
     *
     * @return The {@link Review } object containing all the reviews of the restaurant.
     */
    public MutableLiveData<ArrayList<Review>> getTajMahalLiveReviews() {
        return liveReviews;
    }

    public void addReview(Review newReview) {
        ArrayList<Review> currentReviews = liveReviews.getValue();
        if (currentReviews == null) currentReviews = new ArrayList<>();
        currentReviews.add(0, newReview);
        liveReviews.setValue(currentReviews);
    }

}
