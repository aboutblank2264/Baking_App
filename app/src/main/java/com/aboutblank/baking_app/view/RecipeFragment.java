package com.aboutblank.baking_app.view;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aboutblank.baking_app.R;
import com.aboutblank.baking_app.data.model.Recipe;
import com.aboutblank.baking_app.data.model.Step;
import com.aboutblank.baking_app.view.adapters.StepsRecyclerViewAdapter;

import java.util.ArrayList;

import butterknife.BindView;

public class RecipeFragment extends BaseFragment implements ItemClickedListener {
    private final String LOG_TAG = getClass().getSimpleName();
    private final static String ID = "id";

    private ItemClickedListener itemClickedListener;
    private LiveData<Recipe> recipe;
    private int id = -1;

    @BindView(R.id.steps_recycler)
    RecyclerView stepsRecyclerView;
    StepsRecyclerViewAdapter stepsRecyclerViewAdapter;

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

        if (bundle != null) {
            id = bundle.getInt(ID);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        initializeRecyclerView();

        return view;
    }

    private void initializeRecyclerView() {
        stepsRecyclerViewAdapter = new StepsRecyclerViewAdapter(new ArrayList<Step>(), this);

        stepsRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));
        stepsRecyclerView.setAdapter(stepsRecyclerViewAdapter);
    }

    public void setItemClickedListener(ItemClickedListener itemClickedListener) {
        this.itemClickedListener = itemClickedListener;
    }

    public void setRecipe(int id, LiveData<Recipe> newRecipe) {
        this.id = id;
        this.recipe = newRecipe;

        if (this.recipe != null) {
            this.recipe.observe(this, new Observer<Recipe>() {
                @Override
                public void onChanged(@Nullable Recipe recipe) {
                    if (recipe != null && recipe.getSteps() != null) {
                        stepsRecyclerViewAdapter.update(recipe.getSteps());
                    }
                }
            });
        }
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_recipe;
    }

    @Override
    public void onItemClick(View view, int position) {
        //Launch the detailed step fragment
        Log.d(LOG_TAG, String.format("Step %d clicked", position));

        if(itemClickedListener != null) {
            itemClickedListener.onItemClick(view, position);
        }
    }
}
