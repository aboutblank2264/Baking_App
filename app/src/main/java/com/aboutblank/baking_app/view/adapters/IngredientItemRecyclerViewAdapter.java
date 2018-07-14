package com.aboutblank.baking_app.view.adapters;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aboutblank.baking_app.R;
import com.aboutblank.baking_app.data.model.Ingredient;
import com.aboutblank.baking_app.view.ItemClickedListener;

import java.util.List;
import java.util.Set;

import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;

public class IngredientItemRecyclerViewAdapter extends RecyclerView.Adapter<IngredientItemRecyclerViewAdapter.IngredientItemViewHolder> {
    private final static float GREYED = .3f;
    private final static float NORMAL = 1.0f;

    private List<Ingredient> ingredientList;
    private ItemClickedListener itemClickedListener;

    private Set<Integer> indexedIngredients;

    public IngredientItemRecyclerViewAdapter(List<Ingredient> ingredientList, ItemClickedListener itemClickedListener) {
        this.ingredientList = ingredientList;
        this.itemClickedListener = itemClickedListener;
    }

    @NonNull
    @Override
    public IngredientItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ingredient, parent, false);

        return new IngredientItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientItemViewHolder holder, int position) {
        holder.setIngredient(ingredientList.get(position));

        if(indexedIngredients != null) {
            holder.toggleActive(indexedIngredients.contains(position));
        }
    }

    public void update(List<Ingredient> list, Set<Integer> indexedIngredients) {
        ingredientList.clear();
        ingredientList.addAll(list);

        this.indexedIngredients = indexedIngredients;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return ingredientList.size();
    }

    public class IngredientItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.ingredient_icon)
        ImageView iconView;

        @BindView(R.id.ingredient_text)
        TextView ingredient;

        @BindDrawable(R.drawable.ic_done_black_24dp)
        Drawable doneIcon;

        @BindDrawable(R.drawable.ic_add_black_24dp)
        Drawable addIcon;

        IngredientItemViewHolder(View view) {
            super(view);

            ButterKnife.bind(this, view);

            view.setOnClickListener(this);
        }

        void setIngredient(Ingredient ingredient) {
            this.ingredient.setText(ingredient.toPrint());
        }

        void toggleActive(boolean isIndexed) {
            checkIfActive(itemView, isIndexed);
        }

        @Override
        public void onClick(View item) {
            checkIfActive(item, item.getAlpha() == NORMAL);
            itemClickedListener.onItemClick(item, getAdapterPosition());
        }

        private void checkIfActive(View item, boolean check) {
            if (check) {
                item.setAlpha(GREYED);
                iconView.setBackground(doneIcon);
            } else {
                item.setAlpha(NORMAL);
                iconView.setBackground(addIcon);
            }
        }
    }
}
