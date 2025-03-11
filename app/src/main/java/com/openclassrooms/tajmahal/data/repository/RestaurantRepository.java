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
import com.openclassrooms.tajmahal.data.service.User;
import com.openclassrooms.tajmahal.domain.model.Restaurant;
import com.openclassrooms.tajmahal.domain.model.Review;

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
     * Constructs a new instance of {@link RestaurantRepository} with the given {@link RestaurantApi}.
     *
     * @param restaurantApi The network API interface for fetching restaurant data.
     */
    @Inject
    public RestaurantRepository(RestaurantApi restaurantApi) {
        this.restaurantApi = restaurantApi;
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
     * Fetches reviews for size.
     * This method will make a network call using the provided {@link RestaurantApi} instance
     * to fetch reviews data and get size.
     *
     * @return LiveData holding the restaurant details.
     */
    public Integer getReviewsTotal() {
        return restaurantApi.getReviews().size();
    }

    /**
     * Fetches reviews for mean.
     * This method will make a network call using the provided {@link RestaurantApi} instance
     * to fetch reviews data and get rate.
     *
     * @return LiveData holding the review rates mean.
     */
    public Double getReviewsMean() {
        List<Review> reviews = restaurantApi.getReviews();
        if (reviews == null || reviews.isEmpty()) {
            return (double) 0.0;
        }
        int sum = 0;
        for (Review review : reviews) {
            sum += review.getRate();
        }
        return (double) sum / reviews.size();
    }

    /**
     * Fetches reviews to get list of 1 2 3 4 5 notes.
     * This method will make a network call using the provided {@link RestaurantApi} instance
     * to fetch reviews data and get rate.
     *
     * @return LiveData holding the review rates mean.
     */
    public Integer getRateTotalByLevel(int level) {
        List<Review> reviews = restaurantApi.getReviews();
        if (reviews == null || reviews.isEmpty()) {
            return (int) 0;
        }
        int sum = 0;
        int total = reviews.size();
        for (Review review : reviews) {
            int rate = review.getRate();
            if (rate == level) sum += 1;
        }
        return (int) 100 / total * sum;
    }

    /**
     * Fake Name to User TajMahal
     * This method will make a network call using the provided {@link RestaurantApi} instance
     * To get User Name
     */
    public User getUser() {
        return restaurantApi.getUser();
    }
}
