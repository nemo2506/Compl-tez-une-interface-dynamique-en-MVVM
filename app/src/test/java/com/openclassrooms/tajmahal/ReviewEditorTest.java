package com.openclassrooms.tajmahal;

import android.content.Context;

import com.openclassrooms.tajmahal.ui.reviews.ReviewsFragment;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Observer;

import com.openclassrooms.tajmahal.data.repository.RestaurantRepository;
import com.openclassrooms.tajmahal.data.service.RestaurantApi;
import com.openclassrooms.tajmahal.domain.model.Review;

import org.junit.Rule;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;


public class ReviewEditorTest {

    private ReviewsFragment reviewsFragment;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Mock
    private RestaurantApi restaurantApi;

    @Mock
    private Observer<List<Review>> observer;

    private RestaurantRepository restaurantRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        when(restaurantApi.getReviews()).thenReturn(new ArrayList<>());
        restaurantRepository = new RestaurantRepository(restaurantApi);
        restaurantRepository.getLiveReviews().observeForever(observer);

        reviewsFragment = Mockito.spy(new ReviewsFragment());
        Context mockContext = mock(Context.class);
        doReturn(mockContext).when(reviewsFragment).getContext();
        when(mockContext.getString(R.string.issue_review_empty)).thenReturn("L'avis doit être rempli");
        when(mockContext.getString(R.string.issue_review_lenght)).thenReturn("la critique  doit être comprendre plus de trois lettres");
        when(mockContext.getString(R.string.issue_rate_empty)).thenReturn("la note doit être choisi");

        // Stub userAlert() to prevent execution
        doNothing().when(reviewsFragment).userAlert(anyString());
    }

    @Test
    public void testEmptyReview() {
        String emptyReview = "";
        float validRating = 4f;
        boolean isValid = reviewsFragment.isReviewVerified(emptyReview, validRating);

        assert (!isValid);
        verify(reviewsFragment).userAlert("L'avis doit être rempli");
    }

    @Test
    public void testShortReview() {
        String shortReview = "Hi";
        float validRating = 4f;
        boolean isValid = reviewsFragment.isReviewVerified(shortReview, validRating);

        assert (!isValid);
        verify(reviewsFragment).userAlert("la critique  doit être comprendre plus de trois lettres");
    }

    @Test
    public void testEmptyRate() {
        String validReview = "Restaurant magnifique !";
        float zeroRating = 0f;
        boolean isValid = reviewsFragment.isReviewVerified(validReview, zeroRating);

        assert (!isValid);
        verify(reviewsFragment).userAlert("la note doit être choisi");
    }

    @Test
    public void testValidReview() {
        String validReview = "Moment exceptionnel!";
        float validRating = 5f;
        boolean isValid = reviewsFragment.isReviewVerified(validReview, validRating);

        assert (isValid);
        verify(reviewsFragment, never()).userAlert(anyString()); // Ensure no alerts were shown
    }

    @Test
    public void testAddingOrderdReviewObservable() {
        // Given a new review
        Review newReview1 = new Review("John Doe", "URL1", "très bon",5);
        Review newReview2 = new Review("Johny", "URL2", "pas bon",1);
        List<Review> fakeReviews = new ArrayList<>();
        fakeReviews.add(newReview1);
        fakeReviews.add(newReview2);

        restaurantRepository.addReview(newReview1);
        restaurantRepository.addReview(newReview2);

        List<Review> reviews = restaurantRepository.getLiveReviews().getValue();
        assertNotNull(reviews);
        assertFalse(reviews.isEmpty());
        assertEquals(fakeReviews.size(), reviews.size());
        assertEquals(newReview2, reviews.get(0));
        assertEquals(newReview1, reviews.get(1));
        verify(observer, times(fakeReviews.size()+1)).onChanged(anyList());
    }
}
