package com.aboutblank.baking_app.widget;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aboutblank.baking_app.R;
import com.aboutblank.baking_app.view.ItemClickedListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

//Reference:
// https://android.googlesource.com/platform/development/+/master/samples/ApiDemos/src/com/example/android/apis/appwidget/ExampleAppWidgetConfigure.java
public class ConfigureWidgetActivity extends Activity implements ItemClickedListener {
    @BindView(R.id.chooser)
    RecyclerView listView;

    private int appWidgetId;
    private List<String> titles;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setResult(RESULT_CANCELED);

        setContentView(R.layout.widget_configure);
        ButterKnife.bind(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            titles = extras.getStringArrayList("titles");
            appWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);

            if(appWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
                finish();
            }
            setupListView();
        }
    }

    private void setupListView() {
        ChooserAdapter chooserAdapter = new ChooserAdapter(titles, this);
        listView.setAdapter(chooserAdapter);
    }

    @Override
    public void onItemClick(View view, int position) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] widgetIds = appWidgetManager.getAppWidgetIds(getComponentName());

        IngredientWidgetProvider.updateAppWidgets(this, appWidgetManager,
                titles.get(position), widgetIds);

        Intent result = new Intent();
        result.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        setResult(RESULT_OK, result);

        finish();
    }

    private class ChooserAdapter extends RecyclerView.Adapter<ChooserViewHolder> implements ItemClickedListener {
        private List<String> titles;
        private ItemClickedListener itemClickedListener;

        public ChooserAdapter(List<String> titles, ItemClickedListener itemClickedListener) {
            this.titles = titles;
            this.itemClickedListener = itemClickedListener;
        }

        @NonNull
        @Override
        public ChooserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.widget_configure_item, parent, false);
            return new ChooserViewHolder(view, this);
        }

        @Override
        public void onBindViewHolder(@NonNull ChooserViewHolder holder, int position) {
            holder.setTitle(titles.get(position));
        }

        @Override
        public int getItemCount() {
            return titles.size();
        }

        @Override
        public void onItemClick(View view, int position) {
            itemClickedListener.onItemClick(view, position);
        }
    }

    private class ChooserViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

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
