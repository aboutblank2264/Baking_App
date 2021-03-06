package com.aboutblank.baking_app.widget;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aboutblank.baking_app.BakingApplication;
import com.aboutblank.baking_app.R;
import com.aboutblank.baking_app.data.IDataModel;
import com.aboutblank.baking_app.data.model.MinimalRecipe;
import com.aboutblank.baking_app.view.ItemClickedListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

//Reference:
// https://android.googlesource.com/platform/development/+/master/samples/ApiDemos/src/com/example/android/apis/appwidget/ExampleAppWidgetConfigure.java
public class ConfigureWidgetActivity extends AppCompatActivity implements ItemClickedListener {
    @BindView(R.id.chooser)
    RecyclerView listView;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private int appWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
    private List<MinimalRecipe> minimalRecipes;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("Widget", "getting recipes");
        setResult(RESULT_CANCELED);

        setContentView(R.layout.widget_configure);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        IDataModel dataModel = ((BakingApplication) getApplicationContext()).getDataModel();

        dataModel.getMinimalRecipes().observe(this, minimalRecipes -> {
            if (minimalRecipes != null) {
                Log.d("Widget", minimalRecipes.toString());
                this.minimalRecipes = minimalRecipes;
                setupListView();
            }
        });

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            appWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);

            if (appWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
                finish();
            }
        }
    }

    private void setupListView() {
        ChooserAdapter chooserAdapter = new ChooserAdapter(minimalRecipes, this);
        listView.setLayoutManager(new LinearLayoutManager(this));
        listView.setAdapter(chooserAdapter);
    }

    @Override
    public void onItemClick(View view, int position) {
        Log.d("Widget", String.format("Position: %s, Id: %s, Name: %s",
                String.valueOf(position), appWidgetId, minimalRecipes.get(position).getName()));

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        ComponentName thisAppWidget = new ComponentName(
                ConfigureWidgetActivity.this, IngredientWidgetProvider.class);

        Intent result = new Intent();
        result.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        setResult(RESULT_OK, result);

        //Notes:
        //https://stackoverflow.com/questions/14582395/handling-multiple-instances-of-appwidget
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(thisAppWidget);
        Intent startBroadcast = new Intent(ConfigureWidgetActivity.this,
                IngredientWidgetProvider.class);
        startBroadcast.putExtra(getString(R.string.widget_app_id) + appWidgetId, minimalRecipes.get(position).getId());
        startBroadcast.putExtra(getString(R.string.widget_name) + appWidgetId, minimalRecipes.get(position).getName());
        startBroadcast.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
        startBroadcast.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        sendBroadcast(startBroadcast);

        finish();
    }

    private class ChooserAdapter extends RecyclerView.Adapter<ChooserViewHolder> {
        private List<MinimalRecipe> titles;
        private ItemClickedListener itemClickedListener;

        ChooserAdapter(List<MinimalRecipe> titles, ItemClickedListener itemClickedListener) {
            this.titles = titles;
            this.itemClickedListener = itemClickedListener;
        }

        @NonNull
        @Override
        public ChooserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.widget_configure_item, parent, false);
            return new ChooserViewHolder(view, itemClickedListener);
        }

        @Override
        public void onBindViewHolder(@NonNull ChooserViewHolder holder, int position) {
            holder.setTitle(titles.get(position).getName());
        }

        @Override
        public int getItemCount() {
            return titles.size();
        }
    }

    class ChooserViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.chooser_item_title)
        TextView title;

        ItemClickedListener itemClickedListener;

        ChooserViewHolder(View itemView, ItemClickedListener itemClickedListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            this.itemClickedListener = itemClickedListener;
            itemView.setOnClickListener(this);
        }

        public void setTitle(String title) {
            this.title.setText(title);
        }

        @Override
        public void onClick(View v) {
            itemClickedListener.onItemClick(v, getAdapterPosition());
        }
    }
}
