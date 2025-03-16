package com.openclassrooms.tajmahal.ui.reviews;


import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Window;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.openclassrooms.tajmahal.R;
import com.openclassrooms.tajmahal.databinding.FragmentReviewsBinding;
import com.openclassrooms.tajmahal.domain.model.Review;
import com.openclassrooms.tajmahal.ui.restaurant.DetailsFragment;
import com.openclassrooms.tajmahal.ui.restaurant.DetailsViewModel;

import java.util.ArrayList;
import java.util.Objects;

import dagger.hilt.android.AndroidEntryPoint;

/**
 * ReviewsFragment is the entry point of the application and serves as the primary UI.
 * It displays reviews about a reviews and provides functionality TODO
 * This class uses {@link FragmentReviewsBinding} for data binding to its layout and
 * {@link ReviewsViewModel} to interact with data sources and manage UI-related data.
 */
@AndroidEntryPoint
public class ReviewsFragment extends Fragment {
    public FragmentReviewsBinding binding;
    public ReviewsViewModel reviewsViewModel;
    public DetailsViewModel detailsViewModel;
    private View view;

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
        this.view = view;
        setupUI();
        setupViewModel();
        reviewsViewModel.getTajMahalLiveReviews().observe(getViewLifecycleOwner(), this::updateUIWithReviews); // Observes changes in the restaurant data and updates the UI accordingly.
    }

    /**
     * Sets up the UI-specific properties, such as system UI flags and status bar color.
     */

    private void setupUI() {
        Window window = requireActivity().getWindow();
        window.getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        );
        window.setStatusBarColor(Color.TRANSPARENT);
    }

    /**
     * Initializes the ViewModel for this activity.
     * use in onViewCreated
     */
    private void setupViewModel() {
        reviewsViewModel = new ViewModelProvider(this).get(ReviewsViewModel.class);
        detailsViewModel = new ViewModelProvider(this).get(DetailsViewModel.class);
    }

    /**
     * function to manage reviews UI
     * use in onViewCreated
     */
    @SuppressLint("DefaultLocale")
    void updateUIWithReviews(ArrayList<Review> reviews) {
        if (reviews == null) return;
        setReviewUserUI();
        setActualReviewsUI(reviews);
        binding.userValidate.setOnClickListener(this::userReviewValidate);

    }

    /**
     * function to validate user review
     * verify with isUserReviewLoad function
     * use in updateUIWithReviews
     */
    private void userReviewValidate(View view) {
        String successMessage = requireContext().getString(R.string.success_review_message);
        String errorMessage = requireContext().getString(R.string.error_review_message);
        try {
            if(isUserReviewLoad()) userAlert(successMessage);
        } catch (Exception e) {
            userAlert(errorMessage);
        }
    }

    /**
     * function to set user data in screen
     * use in updateUIWithReviews
     */
    private void setReviewUserUI() {
        String toolBarTitle = (Objects.requireNonNull(detailsViewModel.getTajMahalRestaurant().getValue())).getName();
        String pictureUrl = reviewsViewModel.getTajMahalUser().getPictureUrl();
        String userName = reviewsViewModel.getTajMahalUser().getUser();
        binding.userToolBar.setTitle(toolBarTitle);
        binding.userName.setText(userName);
        binding.userPicture.setTag(pictureUrl);
        Glide.with(binding.getRoot().getContext())
                .load(pictureUrl)
                .into(binding.userPicture);
        binding.userToolBar.setNavigationOnClickListener(v -> onBackClick());
    }

    /**
     * display actual reviews in UI
     * use in updateUIWithReviews
     */
    private void setActualReviewsUI(ArrayList<Review> reviews) {
        RecyclerView recyclerView = view.findViewById(R.id.reviewsList);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setAdapter(new ReviewAdapter(reviews));
    }

    /**
     * after click return to back screen
     * use in setReviewUserUI
     */
    private void onBackClick() {
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        DetailsFragment detailsFragment = DetailsFragment.newInstance();
        fragmentTransaction.add(R.id.container, detailsFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    /**
     * display user review in screen
     * use in userReviewValidate
     *
     * @return
     */
    public boolean isUserReviewLoad() {
        String userName = binding.userName.getText().toString().trim();
        String userUrl = binding.userPicture.getTag().toString();
        String userReviewText = binding.userReviewText.getText().toString().trim();
        float userRate = binding.userRatingBar.getRating();

        if (!isReviewVerified(userReviewText,userRate)) return false;

        Review newReview = new Review(userName, userUrl, userReviewText, (int) userRate);
        reviewsViewModel.addTajMahalReview(newReview);
        return true;
    }


    /**
     * function to verify user review
     * use in validateReview
     */
    public boolean isReviewVerified(String review, float rate) {
        String errorReviewMessage = requireContext().getString(R.string.issue_review_empty);
        String errorReviewMessageLength = requireContext().getString(R.string.issue_review_lenght);
        String errorRateMessage = requireContext().getString(R.string.issue_rate_empty);
        if (review.isEmpty()) {
            userAlert(errorReviewMessage);
            return false;
        } else if (review.length() <= 3) {
            userAlert(errorReviewMessageLength);
            return false;
        }
        if (rate == 0) {
            userAlert(errorRateMessage);
            return false;
        }
        return true;
    }

    /**
     * function to communicate on user interface
     * use in isReviewVerified and isReviewVerified
     */
    public void userAlert(String message) {
        Toast.makeText(binding.getRoot().getContext(), message, Toast.LENGTH_SHORT).show();
    }
}