package com.aboutblank.baking_app.view.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aboutblank.baking_app.R;
import com.aboutblank.baking_app.data.model.MinimalRecipe;
import com.aboutblank.baking_app.utils.ImageUtils;
import com.aboutblank.baking_app.view.ItemClickedListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainRecyclerViewAdapter extends RecyclerView.Adapter<MainRecyclerViewAdapter.MainRecyclerViewHolder> {
    private final String LOG_TAG = getClass().getSimpleName();

    private List<MinimalRecipe> recipeList;
    private ItemClickedListener itemClickedListener;

    public MainRecyclerViewAdapter(List<MinimalRecipe> recipeList, ItemClickedListener itemClickedListener) {
        this.recipeList = recipeList;
        this.itemClickedListener = itemClickedListener;
    }

    @NonNull
    @Override
    public MainRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_main_recycler, parent, false);

        return new MainRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainRecyclerViewHolder holder, int position) {
        holder.setTitle(recipeList.get(position).getName());stat
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    public void updateRecipeList(List<MinimalRecipe> list) {
        recipeList.clear();
        recipeList.addAll(list);

        notifyDataSetChanged();
    }

    public class MainRecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.image)
        ImageView imageView;
        @BindView(R.id.title)
        TextView titleTextView;

        MainRecyclerViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

            view.setOnClickListener(this);
        }

        public void setTitle(String title) {
            titleTextView.setText(title);
        }

        public void setImage(String imageUrl) {
            if (imageUrl != null && !imageUrl.isEmpty()) {
                ImageUtils.loadImage(imageView, imageUrl);
            }
        }

        @Override
        public void onClick(View v) {
            if (itemClickedListener != null) {
                itemClickedListener.onItemClick(v, getAdapterPosition());
            }
        }
    }
}
