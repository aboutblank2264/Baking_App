package com.aboutblank.baking_app.usecases;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

public class LoadFragmentUseCase {

    public void loadFragment(Context context, int viewId, int position) {
        FragmentTransaction fragmentTransaction =
                ((FragmentActivity)context).getSupportFragmentManager().beginTransaction();

        

        fragmentTransaction.commit();
    }
}
