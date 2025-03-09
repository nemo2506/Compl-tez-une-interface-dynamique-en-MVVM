package com.openclassrooms.tajmahal.ui.reviews;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.openclassrooms.tajmahal.R;
import com.openclassrooms.tajmahal.databinding.FragmentReviewsBinding;
import com.openclassrooms.tajmahal.domain.model.Review;

import android.view.View;

import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

/**
 * ReviewsFragment is the entry point of the application and serves as the primary UI.
 * It displays reviews about a reviews and provides functionality TODO
 * This class uses {@link FragmentReviewsBinding} for data binding to its layout and
 * {@link ReviewsViewModel} to interact with data sources and manage UI-related data.
 */
@AndroidEntryPoint
public class ReviewsFragment extends Fragment {
    private FragmentReviewsBinding binding;
    private ReviewsViewModel reviewsViewModel;

    public static ReviewsFragment newInstance() {
        return new ReviewsFragment();
    }

    /**
     * This method is called when the fragment is first created.
     * It's used to perform one-time initialization.
     *
     * @param savedInstanceState A bundle containing previously saved instance state.
     *                           If the fragment is being re-created from a previous saved state, this is the state.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentReviewsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    /**
     * This method is called immediately after `onCreateView()`.
     * Use this method to perform final initialization once the fragment views have been inflated.
     *
     * @param view               The View returned by `onCreateView()`.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     *                           from a previous saved state as given here.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupViewModel();
//        String test = reviewsViewModel.getTajMahalReviews().get(0).getUsername();
        binding.userName.setText("Manon Garcia");
    }

    /**
     * Initializes the ViewModel for this activity.
     */
    private void setupViewModel() {
        reviewsViewModel = new ViewModelProvider(this).get(ReviewsViewModel.class);
    }
}