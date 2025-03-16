package com.openclassrooms.tajmahal;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.openclassrooms.tajmahal.domain.model.Restaurant;
import com.openclassrooms.tajmahal.domain.model.Review;
import com.openclassrooms.tajmahal.ui.restaurant.DetailsViewModel;
import com.openclassrooms.tajmahal.data.repository.RestaurantRepository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

import static org.mockito.Mockito.*;

public class DetailsViewModelTest {

    @Mock
    private RestaurantRepository restaurantRepository;
    @Mock
    private MutableLiveData<ArrayList<Review>> liveReviews;
    @Mock
    private LiveData<Restaurant> liveRestaurant;

    private DetailsViewModel detailsViewModel;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);  // Initialize the mocks

        // Initialize the ViewModel
        detailsViewModel = new DetailsViewModel(restaurantRepository);

        // Mock the repository and live data
        when(restaurantRepository.getLiveReviews()).thenReturn(liveReviews);
        when(restaurantRepository.getRestaurant()).thenReturn(liveRestaurant);
    }

    @Test
    public void testGetTajMahalReviewsTotal() {
        // Arrange
        ArrayList<Review> reviews = new ArrayList<>();
        reviews.add(new Review("JO","URL1","Nickel",5));
        reviews.add(new Review("LOLO","URL2","Bien",4));
        reviews.add(new Review("DODO","URL3","Moyen",3));

        // Mock liveReviews value
        when(liveReviews.getValue()).thenReturn(reviews);

        // Act
        int totalReviews = detailsViewModel.getTajMahalReviewsTotal();

        // Assert
        assert(totalReviews == 3); // There are 3 reviews
    }

    @Test
    public void testGetTajMahalReviewsMean() {
        // Arrange
        ArrayList<Review> reviews = new ArrayList<>();
        reviews.add(new Review("JO","URL1","Nickel",5));
        reviews.add(new Review("LOLO","URL2","Bien",4));
        reviews.add(new Review("DODO","URL3","Moyen",3));

        // Mock liveReviews value
        when(liveReviews.getValue()).thenReturn(reviews);

        // Act
        double mean = detailsViewModel.getTajMahalReviewsMean();

        // Assert
        assert(mean == 4.0); // Average rating is (5+4+3)/3 = 4.0
    }

    @Test
    public void testGetTajMahalRateTotalByLevel() {
        // Arrange
        ArrayList<Review> reviews = new ArrayList<>();
        reviews.add(new Review("JO","URL1","Nickel",5));
        reviews.add(new Review("JOJO","URL1","Nickel",5));
        reviews.add(new Review("GIGI","URL1","Nickel",5));
        reviews.add(new Review("LOLO","URL2","Bien",4));
        reviews.add(new Review("DODO","URL3","Moyen",3));

        // Mock liveReviews value
        when(liveReviews.getValue()).thenReturn(reviews);

        // Act
        int rateTotal = detailsViewModel.getTajMahalRateTotalByLevel(5);

        // Assert
        assert(rateTotal == 50); // 2 out of 4 reviews have a rating of 5, so 50%
    }
}
