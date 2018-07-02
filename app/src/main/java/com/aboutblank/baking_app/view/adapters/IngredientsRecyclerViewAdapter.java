package com.aboutblank.baking_app.view.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aboutblank.baking_app.R;
import com.aboutblank.baking_app.data.model.Ingredient;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IngredientsRecyclerViewAdapter extends RecyclerView.Adapter<IngredientsRecyclerViewAdapter.IngredientsViewHolder> {
    private List<Ingredient> ingredientList;

    public IngredientsRecyclerViewAdapter(List<Ingredient> ingredientList) {
        this.ingredientList = ingredientList;
    }

    @NonNull
    @Override
    public IngredientsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ingredients, parent, false);

        return new IngredientsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientsViewHolder holder, int position) {
        holder.setIngredient(ingredientList.get(position));
    }

    public void update(List<Ingredient> list) {
        ingredientList.clear();
        ingredientList.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return ingredientList.size();
    }

    public class IngredientsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
//        @BindView(R.id.ingredient_item)
//        View itemView;

        @BindView(R.id.ingredient_text)
        TextView ingredient;

        IngredientsViewHolder(View view) {
            super(view);

            ButterKnife.bind(this, view);
        }

        public void setIngredient(Ingredient ingredient) {
            this.ingredient.setText(ingredient.toPrint());
        }

        @Override
        public void onClick(View item) {
            if(item.getAlpha() == 1.0f) {
                item.setAlpha(.5f);
            } else {
                item.setAlpha(1.0f);
            }
        }
    }
}
