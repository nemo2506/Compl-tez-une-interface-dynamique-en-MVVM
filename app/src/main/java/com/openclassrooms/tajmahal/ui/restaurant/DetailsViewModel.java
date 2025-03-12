package com.openclassrooms.tajmahal.ui.restaurant;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.openclassrooms.tajmahal.R;
import com.openclassrooms.tajmahal.data.repository.RestaurantRepository;
import com.openclassrooms.tajmahal.domain.model.Restaurant;
import com.openclassrooms.tajmahal.domain.model.Review;

import javax.inject.Inject;

import java.util.Calendar;
import java.util.List;

import dagger.hilt.android.lifecycle.HiltViewModel;

/**
 * MainViewModel is responsible for preparing and managing the data for the {@link DetailsFragment}.
 * It communicates with the {@link RestaurantRepository} to fetch restaurant details and provides
 * utility methods related to the restaurant UI.
 * This ViewModel is integrated with Hilt for dependency injection.
 */
@HiltViewModel
public class DetailsViewModel extends ViewModel {

    private final RestaurantRepository restaurantRepository;
    List<Review> reviews;

    /**
     * Constructor that Hilt will use to create an instance of MainViewModel.
     *
     * @param restaurantRepository The repository which will provide restaurant data.
     */
    @Inject
    public DetailsViewModel(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
        reviews = restaurantRepository.getReviews();
    }

    /**
     * Fetches the details of the Taj Mahal restaurant.
     *
     * @return LiveData object containing the details of the Taj Mahal restaurant.
     */
    public LiveData<Restaurant> getTajMahalRestaurant() {
        return restaurantRepository.getRestaurant();
    }

    /**
     * Fetches reviews for size.
     * This method will make a network call using the provided {@link RestaurantRepository } instance
     * to fetch reviews data and get size.
     *
     * @return LiveData holding the restaurant details.
     */
    public Integer getTajMahalReviewsTotal() {
        return reviews.size();
    }

    /**
     * Fetches reviews for mean.
     * This method will make a network call using the provided {@link RestaurantRepository } instance
     * to fetch reviews data and get rate.
     *
     * @return LiveData holding the review rates mean.
     */
    public Double getTajMahalReviewsMean() {
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
     * This method will make a network call using the provided {@link RestaurantRepository} instance
     * to fetch reviews data and get rate.
     *
     * @return LiveData holding the review rates mean.
     */
    public Integer getTajMahalRateTotalByLevel(int level) {
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
     * Retrieves the current day of the week in French.
     *
     * @return A string representing the current day of the week in French.
     */
    public String getCurrentDay(Context context) {
        Calendar calendar = Calendar.getInstance();
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        String dayString;

        switch (dayOfWeek) {
            case Calendar.MONDAY:
                dayString = context.getString(R.string.monday);
                break;
            case Calendar.TUESDAY:
                dayString = context.getString(R.string.tuesday);
                break;
            case Calendar.WEDNESDAY:
                dayString = context.getString(R.string.wednesday);
                break;
            case Calendar.THURSDAY:
                dayString = context.getString(R.string.thursday);
                break;
            case Calendar.FRIDAY:
                dayString = context.getString(R.string.friday);
                break;
            case Calendar.SATURDAY:
                dayString = context.getString(R.string.saturday);
                break;
            case Calendar.SUNDAY:
                dayString = context.getString(R.string.sunday);
                break;
            default:
                dayString = "";
        }
        return dayString;
    }

}
