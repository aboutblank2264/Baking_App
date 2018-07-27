package com.aboutblank.baking_app.usecases;

import com.aboutblank.baking_app.BaseActivity;
import com.aboutblank.baking_app.states.RecipeViewState;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ChangeRecipeStepViewUseCase {

    @Inject
    public ChangeRecipeStepViewUseCase() {
    }

    public void onGoPrevious(BaseActivity activity, RecipeViewState state) {
        if (checkIfHasNextStep(false, state)) {
            activity.setState(getNextViewState(state, false));
        }
    }

    public void onGoNext(BaseActivity activity, RecipeViewState state) {
        if (checkIfHasNextStep(true, state)) {
            activity.setState(getNextViewState(state, true));
        }
    }

    private boolean checkIfHasNextStep(boolean next, RecipeViewState state) {
        int currentPosition = state.getCurrentPosition();
        if (next) {
            return currentPosition < state.getNumberOfSteps() - 1; // subtract 1 for the ingredients tab.
        } else {
            return currentPosition > 0;
        }
    }

    private RecipeViewState getNextViewState(RecipeViewState state, boolean later) {
        RecipeViewState.Builder builder = new RecipeViewState.Builder(state.getRecipe())
                .setIndexedIngredients(state.getIndexedIngredients());
        if (later) {
            builder.setCurrentPosition(state.getCurrentPosition() + 1);
        } else {
            builder.setCurrentPosition(state.getCurrentPosition() - 1);
        }
        return builder.build();
    }
}
