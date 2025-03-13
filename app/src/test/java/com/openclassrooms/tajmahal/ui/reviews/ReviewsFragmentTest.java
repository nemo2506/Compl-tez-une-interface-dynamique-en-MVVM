package com.openclassrooms.tajmahal.ui.reviews;

import static org.junit.Assert.*;

import android.view.View;

import androidx.fragment.app.testing.FragmentScenario;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.openclassrooms.tajmahal.domain.model.Review;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

@RunWith(AndroidJUnit4.class)
public class ReviewsFragmentTest {

    private FragmentScenario<ReviewsFragment> fragmentScenario;

    @Before
    public void setUp() {
        fragmentScenario = FragmentScenario.launchInContainer(ReviewsFragment.class);
    }

    @Test
    public void testUpdateUIWithReviews_ShouldUpdateUI() {
        fragmentScenario.onFragment(fragment -> {
            // Arrange
            ArrayList<Review> reviews = new ArrayList<>();
            reviews.add(new Review("John Doe", "url", "Great place!", 5));
            MutableLiveData<ArrayList<Review>> liveData = new MutableLiveData<>(reviews);
            
            fragment.reviewsViewModel.getTajMahalLiveReviews().setValue(reviews);
            
            // Act
            fragment.updateUIWithReviews(reviews);

            // Assert
            assertNotNull(fragment.binding);
            assertNotNull(fragment.binding.userValidate);
        });
    }
}
