package com.openclassrooms.tajmahal.ui.restaurant;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.openclassrooms.tajmahal.R;
import com.openclassrooms.tajmahal.data.repository.RestaurantRepository;
import com.openclassrooms.tajmahal.domain.model.Restaurant;
import com.openclassrooms.tajmahal.domain.model.Review;

import javax.inject.Inject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

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
    MutableLiveData<ArrayList<Review>> liveReviews;

    /**
     * Constructor that Hilt will use to create an instance of MainViewModel.
     *
     * @param restaurantRepository The repository which will provide restaurant data.
     */
    @Inject
    public DetailsViewModel(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
        liveReviews = (MutableLiveData<ArrayList<Review>>) restaurantRepository.getLiveReviews();
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
     * Fetches liveReviews for size.
     * This method will make a network call using the provided {@link RestaurantRepository } instance
     * to fetch liveReviews data and get size.
     *
     * @return LiveData holding the restaurant details.
     */
    public Integer getTajMahalReviewsTotal() {
        return Objects.requireNonNull(liveReviews.getValue()).size();
    }

    /**
     * Fetches liveReviews for mean.
     * This method will make a network call using the provided {@link RestaurantRepository } instance
     * to fetch liveReviews data and get rate.
     *
     * @return LiveData holding the review rates mean.
     */
    public Double getTajMahalReviewsMean() {
        if (liveReviews == null || Objects.requireNonNull(liveReviews.getValue()).isEmpty()) {
            return (double) 0.0;
        }
        int sum = 0;
        for (Review review : liveReviews.getValue()) {
            sum += review.getRate();
        }
        return (double) sum / liveReviews.getValue().size();
    }

    /**
     * Fetches liveReviews to get list of 1 2 3 4 5 notes.
     * This method will make a network call using the provided {@link RestaurantRepository} instance
     * to fetch liveReviews data and get rate.
     *
     * @return LiveData holding the review rates mean.
     */
    public Integer getTajMahalRateTotalByLevel(int level) {
        if (liveReviews == null || Objects.requireNonNull(liveReviews.getValue()).isEmpty()) {
            return (int) 0;
        }
        int sum = 0;
        double total = liveReviews.getValue().size();
        for (Review review : liveReviews.getValue()) {
            int rate = review.getRate();
            if (rate == level) sum += 1;
        }
        return (int) (sum / total * 100);
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
