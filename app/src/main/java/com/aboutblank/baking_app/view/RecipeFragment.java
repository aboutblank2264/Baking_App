package com.aboutblank.baking_app.view;

import android.arch.lifecycle.LiveData;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aboutblank.baking_app.BakingApplication;
import com.aboutblank.baking_app.MainViewModel;
import com.aboutblank.baking_app.R;
import com.aboutblank.baking_app.data.model.Recipe;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeFragment extends BaseFragment {
    private final String LOG_TAG = getClass().getSimpleName();
    private final static String ID = "id";

    private MainViewModel mainViewModel;
    private LiveData<Recipe> recipe;
    private int id = -1;

    @BindView(R.id.card_view)
    CardView cardView;

    public static RecipeFragment newInstance(int id) {
        RecipeFragment recipeFragment = new RecipeFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ID, id);

        recipeFragment.setArguments(bundle);

        return recipeFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();

        if(bundle != null) {
            id = bundle.getInt(ID);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recipe_fragment, container, false);

        ButterKnife.bind(this, view);

        mainViewModel = ((BakingApplication) requireActivity().getApplication()).getMainViewModel();

        recipe = mainViewModel.getRecipe(id);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public int getLayout() {
        return 0;
    }
}
