package com.aboutblank.baking_app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.aboutblank.baking_app.data.model.Recipe;
import com.aboutblank.baking_app.view.ItemClickedListener;
import com.aboutblank.baking_app.view.MainRecyclerViewAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements ItemClickedListener {
    private final String LOG_TAG = getClass().getSimpleName();

    private MainViewModel mainViewModel;

    @BindView(R.id.main_recycler_view)
    RecyclerView mainRecyclerView;
    MainRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initializeRecyclerView();

        mainViewModel = getViewModel();
        mainViewModel.update();
    }

    /**
     * Initializes the Recipe list.
     * Initial list of recipes is set to empty until full list can be retrieved.
     */
    private void initializeRecyclerView() {
        adapter = new MainRecyclerViewAdapter(new ArrayList<Recipe>());
        adapter.setItemClickedListener(this);
        mainRecyclerView.setAdapter(adapter);
        mainRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    private MainViewModel getViewModel() {
        return ((BakingApplication) getApplication()).getMainViewModel();
    }

    private void update() {
    }

    @Override
    public void onItemClick(View view, int position) {
        // TODO launch recipe fragment
    }
}
