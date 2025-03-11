package com.openclassrooms.tajmahal.ui.reviews;

import static java.sql.DriverManager.println;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.openclassrooms.tajmahal.R;
import com.openclassrooms.tajmahal.databinding.FragmentReviewsBinding;
import com.openclassrooms.tajmahal.domain.model.Review;

import java.util.ArrayList;
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
//    public List<Review> reviews;

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
        manageReview(view);
    }


    /**
     * Initializes the ViewModel for this activity.
     */
    private void setupViewModel() {
        reviewsViewModel = new ViewModelProvider(this).get(ReviewsViewModel.class);
    }

    /**
     * function to manage review service
     * use in onViewCreated
     */
    private void manageReview(View view) {
        List<Review> reviews = reviewsViewModel.getTajMahalReviews();
        RecyclerView recyclerView = view.findViewById(R.id.reviewsList);
        setActualReviews(view, reviews, recyclerView);
        setUserReview();
        binding.userValidate.setOnClickListener(this::userReviewValidate);
    }

    /**
     * function to manage user avis
     * verify with isReviewVerified function
     */
    private void userReviewValidate(View view) {
        if (!isReviewVerified()) return;
        String successMessage = requireContext().getString(R.string.success_review_message);
        String errorMessage = requireContext().getString(R.string.error_review_message);
        try {
            userEditHide();
            userReviewLoad(view);
            newReviewShow();
            userAlert(successMessage);
        } catch (Exception e) {
            userAlert(errorMessage);
            // for developer
            try {
                Thread.sleep(2000); // Sleep for 2000 milliseconds (2 seconds)
            } catch (InterruptedException f) {
                System.out.println("Sleep was interrupted!");
            }
            userAlert(e.toString());
            throw new RuntimeException(e);
        }
    }

    /**
     * function to set user in review in display user
     * use in manageReview
     */
    private void setUserReview() {
        String toolBarTitle = reviewsViewModel.getTajMahalRestaurant().getName();
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
     * after click return to back screen
     */
    private void onBackClick() {
        requireActivity().getSupportFragmentManager().popBackStack();
    }

    /**
     * display actual reviews { @link ReviewsViewModel }
     */
    private void setActualReviews(View view, List<Review> reviews, RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setAdapter(new ReviewAdapter(reviews));
    }

    /**
     * function to save user review
     * use in userReviewValidate
     */
    private void userReviewLoad(View view) {
        String userName = binding.userName.getText().toString().trim();
        String userUrl = binding.userPicture.getTag().toString();
        String userReviewText = binding.userReviewText.getText().toString().trim();
        float userRate = binding.userRatingBar.getRating();

        binding.newReviewName.setText(userName);
        Glide.with(binding.getRoot().getContext())
                .load(userUrl)
                .into(binding.newReviewPicture);
        binding.newReviewBar.setRating(userRate);
        binding.newReviewText.setText(userReviewText);
        binding.newReviewName.setText(userName);
    }


    private void userEditHide() {
        binding.userEdit.setVisibility(View.GONE);
    }

    private void newReviewShow() {
        binding.newReview.setVisibility(View.VISIBLE);
    }

    /**
     * function to verify user review
     * use in validateReview
     */
    private boolean isReviewVerified() {
        String errorReviewMessage = requireContext().getString(R.string.issue_review_empty);
        String errorReviewMessageLength = requireContext().getString(R.string.issue_review_lenght);
        String errorRateMessage = requireContext().getString(R.string.issue_rate_empty);
        float rating = binding.userRatingBar.getRating();
        String userReview = binding.userReviewText.getText().toString().trim();
        if (userReview.isEmpty()) {
            userAlert(errorReviewMessage);
            return false;
        }
        if (userReview.length() <= 3) {
            userAlert(errorReviewMessageLength);
            return false;
        }
        if (rating == 0) {
            userAlert(errorRateMessage);
            return false;
        }
        return true;
    }

    /**
     * function to communicate on user interface
     * use in isReviewVerified and isReviewVerified
     */
    private void userAlert(String message) {
        Toast.makeText(binding.getRoot().getContext(), message, Toast.LENGTH_SHORT).show();
    }
}