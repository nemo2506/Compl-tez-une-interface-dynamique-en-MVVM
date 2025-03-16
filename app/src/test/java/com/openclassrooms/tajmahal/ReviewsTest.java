package com.openclassrooms.tajmahal;

import android.content.Context;
import com.openclassrooms.tajmahal.ui.reviews.ReviewsFragment;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

public class ReviewsTest {

    private ReviewsFragment reviewsFragment;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        reviewsFragment = Mockito.spy(new ReviewsFragment());
        Context mockContext = mock(Context.class);
        doReturn(mockContext).when(reviewsFragment).getContext();
        when(mockContext.getString(R.string.issue_review_empty)).thenReturn("Review cannot be empty");
        when(mockContext.getString(R.string.issue_review_lenght)).thenReturn("Review must be longer than 3 characters");
        when(mockContext.getString(R.string.issue_rate_empty)).thenReturn("Rating cannot be zero");

        // Stub userAlert() to prevent execution
        doNothing().when(reviewsFragment).userAlert(anyString());
    }

    @Test
    public void testEmptyReviewTriggersAlert() {
        String emptyReview = "";
        float validRating = 4f;
        boolean isValid = reviewsFragment.isReviewVerified(emptyReview, validRating);

        assert (!isValid);
        verify(reviewsFragment).userAlert("Review cannot be empty");
    }

    @Test
    public void testShortReviewTriggersAlert() {
        String shortReview = "Hi";
        float validRating = 4f;
        boolean isValid = reviewsFragment.isReviewVerified(shortReview, validRating);

        assert (!isValid);
        verify(reviewsFragment).userAlert("Review must be longer than 3 characters");
    }

    @Test
    public void testZeroRatingTriggersAlert() {
        String validReview = "Amazing place!";
        float zeroRating = 0f;
        boolean isValid = reviewsFragment.isReviewVerified(validReview, zeroRating);

        assert (!isValid);
        verify(reviewsFragment).userAlert("Rating cannot be zero");
    }

    @Test
    public void testValidReviewPasses() {
        String validReview = "Great experience!";
        float validRating = 5f;
        boolean isValid = reviewsFragment.isReviewVerified(validReview, validRating);

        assert (isValid);
        verify(reviewsFragment, never()).userAlert(anyString()); // Ensure no alerts were shown
    }
}
