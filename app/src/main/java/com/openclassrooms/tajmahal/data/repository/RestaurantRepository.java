/*
 * Copyright (c) 2025. Copyright © 2025 Taj Mahal. Tous droits réservés.
 *
 * Cette application, y compris son code source, sa conception, son contenu et ses fonctionnalités, est la propriété exclusive de Taj Mahal.
 * Toute reproduction, distribution ou utilisation non autorisée, en tout ou en partie, est strictement interdite sans l'accord écrit préalable de Taj Mahal.
 *
 * Développé par Marc Navarro pour Taj Mahal.
 */

package com.openclassrooms.tajmahal.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.openclassrooms.tajmahal.data.service.RestaurantApi;
import com.openclassrooms.tajmahal.domain.model.User;
import com.openclassrooms.tajmahal.domain.model.Restaurant;
import com.openclassrooms.tajmahal.domain.model.Review;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;


/**
 * This is the repository class for managing restaurant data. Repositories are responsible
 * for coordinating data operations from data sources such as network APIs, databases, etc.
 * <p>
 * Typically in an Android app built with architecture components, the repository will handle
 * the logic for deciding whether to fetch data from a network source or use data from a local cache.
 *
 * @see Restaurant
 * @see RestaurantApi
 */
@Singleton
public class RestaurantRepository {

    // The API interface instance that will be used for network requests related to restaurant data.
    private final RestaurantApi restaurantApi;

    /**
     * Initialize reviews with cast and type.
     * <p>
     * reviews to mutable list with ArrayList and MutableLiveData
     * </p>
     */
    private final MutableLiveData<ArrayList<Review>> liveReviews = new MutableLiveData<>(new ArrayList<>());

    /**
     * Constructs a new instance of {@link RestaurantRepository} with the given {@link RestaurantApi}.
     *
     * @param restaurantApi The network API interface for fetching restaurant data.
     */
    @Inject
    public RestaurantRepository(RestaurantApi restaurantApi) {
        this.restaurantApi = restaurantApi;
        liveReviews.setValue(new ArrayList<>(restaurantApi.getReviews()));
    }

    /**
     * Retrieves Actuals Reviews under MutableLiveData.
     * <p>
     * Fake User saving his review
     * </p>
     *
     * @return The {@link Review } object containing all the reviews of the restaurant.
     */
    public MutableLiveData<ArrayList<Review>> getLiveReviews() {
        return liveReviews;
    }

    /**
     * Adding Review to MutableLiveData Actual Reviews.
     */
    public void addReview(Review newReview) {
        ArrayList<Review> currentReviews = liveReviews.getValue();
        if (currentReviews == null) currentReviews = new ArrayList<>();
        currentReviews.add(0, newReview);
        liveReviews.setValue(currentReviews);
    }

    /**
     * Fetches the restaurant details.
     * <p>
     * This method will make a network call using the provided {@link RestaurantApi} instance
     * to fetch restaurant data. Note that error handling and any transformations on the data
     * would need to be managed.
     *
     * @return LiveData holding the restaurant details.
     */
    public LiveData<Restaurant> getRestaurant() {
        return new MutableLiveData<>(restaurantApi.getRestaurant());
    }

    /**
     * Fetches the restaurant reviews.
     * <p>
     * This method will make a network call using the provided {@link RestaurantApi} instance
     * to fetch reviews data.
     * nouveau list de reviews modifiable en vue du nouveau API
     *
     * @return LiveData holding the restaurant details.
     */

    public List<Review> getReviews() {
        return restaurantApi.getReviews();
    }

    /**
     * Fake Name TajMahal User
     * This method will make a network call using the provided {@link RestaurantApi} instance
     * To get User Name
     */
    public User getUser() {
        return restaurantApi.getUser();
    }

}
