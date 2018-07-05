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

import com.aboutblank.baking_app.BakingApplication;
import com.aboutblank.baking_app.MainViewModel;
import com.aboutblank.baking_app.R;
import com.aboutblank.baking_app.data.model.Recipe;
import com.aboutblank.baking_app.data.model.Step;
import com.aboutblank.baking_app.player.MediaPlayer;
import com.aboutblank.baking_app.view.adapters.StepsRecyclerViewAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class RecipeFragment extends BaseFragment implements ItemClickedListener {
    private final String LOG_TAG = getClass().getSimpleName();
    private final static String ID = "id";

    private LiveData<Recipe> recipe;

    @BindView(R.id.steps_recycler)
    RecyclerView stepsRecyclerView;
    StepsRecyclerViewAdapter stepsRecyclerViewAdapter;

    private MainViewModel mainViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        mainViewModel = ((BakingApplication) requireActivity().getApplication()).getMainViewModel();
        initializeRecyclerView(mainViewModel);

        return view;
    }

    private void initializeRecyclerView(MainViewModel mainViewModel) {
        stepsRecyclerViewAdapter = new StepsRecyclerViewAdapter(mainViewModel, new ArrayList<Step>(),
                stepsRecyclerView, getCompositeDisposable(), this);

        stepsRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));
        stepsRecyclerView.setAdapter(stepsRecyclerViewAdapter);
    }

    public void setRecipe(LiveData<Recipe> newRecipe) {
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
    public void onDetach() {
        super.onDetach();
        Disposable disposable = mainViewModel.getPlayer().subscribe(new Consumer<MediaPlayer>() {
            @Override
            public void accept(MediaPlayer mediaPlayer) throws Exception {
                mediaPlayer.release();
            }
        });

        getCompositeDisposable().add(disposable);
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_recipe;
    }

    @Override
    public void onItemClick(View view, int position) {
        //Launch the detailed step fragment
        Log.d(LOG_TAG, String.format("Step %d clicked", position));
    }
}
