package com.openclassrooms.tajmahal.ui.reviews;

import static org.junit.Assert.*;

import android.content.Context;
import android.view.LayoutInflater;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.test.core.app.ApplicationProvider;


import com.openclassrooms.tajmahal.R;
import com.openclassrooms.tajmahal.databinding.FragmentReviewsBinding;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = "./AndroidManifest.xml")
public class ReviewsTest {
    private ReviewsFragment fragment;
    private FragmentReviewsBinding binding;
    @Mock
    private ReviewsViewModel reviewsViewModel;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        fragment = new ReviewsFragment();
        binding = FragmentReviewsBinding.inflate(LayoutInflater.from(ApplicationProvider.getApplicationContext()));
        fragment.binding = binding;
        fragment.reviewsViewModel = reviewsViewModel;
    }

    @Test
    public void testCoordinatorLayoutInflation() {
        Context context = org.robolectric.RuntimeEnvironment.application;

        CoordinatorLayout coordinatorLayout = (CoordinatorLayout) LayoutInflater.from(context)
                .inflate(R.layout.fragment_reviews, null);

        assertNotNull(coordinatorLayout);
    }
}
