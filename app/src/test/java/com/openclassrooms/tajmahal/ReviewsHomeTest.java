package com.openclassrooms.tajmahal;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.openclassrooms.tajmahal.domain.model.Restaurant;
import com.openclassrooms.tajmahal.domain.model.Review;
import com.openclassrooms.tajmahal.ui.restaurant.DetailsViewModel;
import com.openclassrooms.tajmahal.data.repository.RestaurantRepository;
import com.openclassrooms.tajmahal.ui.reviews.ReviewsViewModel;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

import static org.mockito.Mockito.*;

public class ReviewsHomeTest {

    @Mock
    private RestaurantRepository restaurantRepository;
    @Mock
    private LiveData<Restaurant> liveRestaurant;

    private DetailsViewModel detailsViewModel;
    @Mock
    private ReviewsViewModel reviewsViewModel;
    @Mock
    private MutableLiveData<ArrayList<Review>> liveReviews;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        liveReviews = spy(new MutableLiveData<>());

        ArrayList<Review> reviews = new ArrayList<>();
        reviews.add(new Review("DEDE", "URL1", "TROP BIEN", 5));
        reviews.add(new Review("PEPE", "URL2", "PARFAIT", 4));
        reviews.add(new Review("JO", "URL1", "Nickel", 5));
        reviews.add(new Review("LOLO", "URL2", "Bien", 4));
        reviews.add(new Review("DODO", "URL3", "Moyen", 3));
        when(liveReviews.getValue()).thenReturn(reviews);
        when(restaurantRepository.getLiveReviews()).thenReturn(liveReviews);
        detailsViewModel = new DetailsViewModel(restaurantRepository);
    }

    @Test
    public void testReviewsTotal() {
        int totalDetails = detailsViewModel.getTajMahalReviewsTotal();

        assert (totalDetails == 5);
    }

    @Test
    public void testReviewsMean() {
        double mean = detailsViewModel.getTajMahalReviewsMean();
        // Assert
        assert (mean == 4.2); // Average rating is (5+4+5+4+3)/5 = 4.2
    }

    @Test
    public void testEmptyReviewMean() {
        ArrayList<Review> reviews = new ArrayList<>();
        when(liveReviews.getValue()).thenReturn(reviews);
        when(restaurantRepository.getLiveReviews()).thenReturn(liveReviews);
        detailsViewModel = new DetailsViewModel(restaurantRepository);
        double mean = detailsViewModel.getTajMahalReviewsMean();
        // Assert
        assert (mean == 0); // Average rating is 0 = 0
    }
//
    @Test
    public void testPercentByLevelFrom1To5() {
        int rateTotal5 = detailsViewModel.getTajMahalRateTotalByLevel(5);
        int rateTotal4 = detailsViewModel.getTajMahalRateTotalByLevel(4);
        int rateTotal3 = detailsViewModel.getTajMahalRateTotalByLevel(3);
        int rateTotal2 = detailsViewModel.getTajMahalRateTotalByLevel(2);
        int rateTotal1 = detailsViewModel.getTajMahalRateTotalByLevel(1);

        int total5PourCent = 40 ; // 2 / 5 * 100
        int total4PourCent = 40 ; // 2 / 5 * 100
        int total3PourCent = 20 ; // 1 / 5 * 100
        int total2PourCent = 0 ; // 0
        int total1PourCent = 0 ; // 0

        assert (rateTotal5 == total5PourCent);
        assert (rateTotal4 == total4PourCent);
        assert (rateTotal3 == total3PourCent);
        assert (rateTotal2 == total2PourCent);
        assert (rateTotal1 == total1PourCent);
    }
}
