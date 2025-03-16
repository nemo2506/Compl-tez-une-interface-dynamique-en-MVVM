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
        when(mockContext.getString(R.string.issue_review_empty)).thenReturn("L'avis doit être rempli");
        when(mockContext.getString(R.string.issue_review_lenght)).thenReturn("la critique  doit être comprendre plus de trois lettres");
        when(mockContext.getString(R.string.issue_rate_empty)).thenReturn("la note doit être choisi");

        // Stub userAlert() to prevent execution
        doNothing().when(reviewsFragment).userAlert(anyString());
    }

    @Test
    public void testCommentaireVide() {
        String emptyReview = "";
        float validRating = 4f;
        boolean isValid = reviewsFragment.isReviewVerified(emptyReview, validRating);

        assert (!isValid);
        verify(reviewsFragment).userAlert("L'avis doit être rempli");
    }

    @Test
    public void testCommentaireCourt() {
        String shortReview = "Hi";
        float validRating = 4f;
        boolean isValid = reviewsFragment.isReviewVerified(shortReview, validRating);

        assert (!isValid);
        verify(reviewsFragment).userAlert("la critique  doit être comprendre plus de trois lettres");
    }

    @Test
    public void testSansNote() {
        String validReview = "Restaurant magnifique !";
        float zeroRating = 0f;
        boolean isValid = reviewsFragment.isReviewVerified(validReview, zeroRating);

        assert (!isValid);
        verify(reviewsFragment).userAlert("la note doit être choisi");
    }

    @Test
    public void testAvisValable() {
        String validReview = "Moment exceptionnel!";
        float validRating = 5f;
        boolean isValid = reviewsFragment.isReviewVerified(validReview, validRating);

        assert (isValid);
        verify(reviewsFragment, never()).userAlert(anyString()); // Ensure no alerts were shown
    }
}
