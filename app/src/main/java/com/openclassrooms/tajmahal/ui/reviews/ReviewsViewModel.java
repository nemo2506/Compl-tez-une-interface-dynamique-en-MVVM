/*
 * Copyright (c) 2025. Copyright © 2025 Taj Mahal. Tous droits réservés.
 *
 * Cette application, y compris son code source, sa conception, son contenu et ses fonctionnalités, est la propriété exclusive de Taj Mahal.
 * Toute reproduction, distribution ou utilisation non autorisée, en tout ou en partie, est strictement interdite sans l'accord écrit préalable de Taj Mahal.
 *
 * Développé par Marc Navarro pour Taj Mahal.
 */

package com.openclassrooms.tajmahal.ui.reviews;

import androidx.lifecycle.LiveData;
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

    @Inject
    public ReviewsViewModel(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    public List<Review> getTajMahalReviews() {
        return restaurantRepository.getReviews();
    }

    public User getTajMahalUser() {
        return restaurantRepository.getUser();
    }

    public Restaurant getTajMahalRestaurant() {
        return restaurantRepository.getRestaurant().getValue();
    }

    public LiveData<ArrayList<Review>> getTajMahalLiveReviews() {
        return restaurantRepository.getLiveReviews();
    }
}
