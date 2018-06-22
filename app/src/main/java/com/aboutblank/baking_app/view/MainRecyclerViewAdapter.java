package com.aboutblank.baking_app.view;

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

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainRecyclerViewAdapter extends RecyclerView.Adapter<MainRecyclerViewAdapter.MainRecyclerViewHolder> {
    private List<MinimalRecipe> recipeList;
    private ItemClickedListener itemClickedListener;

    public MainRecyclerViewAdapter(List<MinimalRecipe> recipeList) {
        this.recipeList = recipeList;
    }

    @NonNull
    @Override
    public MainRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.main_recycler_item, parent, false);

        return new MainRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainRecyclerViewHolder holder, int position) {
        holder.setTitle(recipeList.get(position).getName());
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

    public void setItemClickedListener(ItemClickedListener itemClickedListener) {
        this.itemClickedListener = itemClickedListener;
    }

    public class MainRecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.image)
        ImageView imageView;
        @BindView(R.id.title)
        TextView titleTextView;

        public MainRecyclerViewHolder(View view) {
            super(view);
            ButterKnife.bind(view);
        }

        public void setTitle(@NonNull String title) {
            titleTextView.setText(title);
        }

        public void setImage( String imageUrl) {
            if(imageUrl != null && !imageUrl.isEmpty()) {
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
