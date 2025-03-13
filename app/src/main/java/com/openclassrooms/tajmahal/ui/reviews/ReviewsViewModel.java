/*
 * Copyright (c) 2025. Copyright © 2025 Taj Mahal. Tous droits réservés.
 *
 * Cette application, y compris son code source, sa conception, son contenu et ses fonctionnalités, est la propriété exclusive de Taj Mahal.
 * Toute reproduction, distribution ou utilisation non autorisée, en tout ou en partie, est strictement interdite sans l'accord écrit préalable de Taj Mahal.
 *
 * Développé par Marc Navarro pour Taj Mahal.
 */

package com.openclassrooms.tajmahal.ui.reviews;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.openclassrooms.tajmahal.data.repository.RestaurantRepository;
import com.openclassrooms.tajmahal.domain.model.User;
import com.openclassrooms.tajmahal.domain.model.Review;

import java.util.ArrayList;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class ReviewsViewModel extends ViewModel {

    private final RestaurantRepository restaurantRepository;

    @Inject
    public ReviewsViewModel(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    /**
     * Retrieves user data.
     * <p>
     * Fake User saving his review
     * </p>
     *
     * @return The {@link User } object containing data of the user.
     */
    public User getTajMahalUser() {
        return restaurantRepository.getUser();
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
        return restaurantRepository.getLiveReviews();
    }

    /**
     * Adding Review to MutableLiveData Actual Reviews.
     */
    public void addTajMahalReview(Review newReview) {
        restaurantRepository.addReview(newReview);
    }

}
