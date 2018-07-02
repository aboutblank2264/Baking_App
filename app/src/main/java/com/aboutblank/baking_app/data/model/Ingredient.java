package com.aboutblank.baking_app.data.model;

import android.support.annotation.StringDef;

import com.google.gson.annotations.SerializedName;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class Ingredient {
    private double quantity;

    @SerializedName("measure")
    private @Measurement
    String measurement;
    private String ingredient;

    public Ingredient(double quantity, @Measurement String measurement, String ingredient) {
        this.quantity = quantity;
        this.measurement = measurement;
        this.ingredient = ingredient;
    }

    public double getQuantity() {
        return quantity;
    }

    public @Measurement
    String getMeasurement() {
        return measurement;
    }

    public String getIngredient() {
        return ingredient;
    }

    public String toPrint() {
        return String.format("%s %s of %s", String.valueOf(quantity),
                measurement.toLowerCase(), ingredient);
    }

    @Override
    public String toString() {
        return "Ingredient{" +
                "quantity=" + quantity +
                ", measurement=" + measurement +
                ", ingredient='" + ingredient + '\'' +
                '}';
    }

    public static final String CUP = "CUP";
    public static final String TBLSP = "TBLSP";
    public static final String TSP = "TSP";
    public static final String G = "G";
    public static final String OZ = "OZ";
    public static final String K = "K";
    public static final String UNIT = "UNIT";

    @StringDef({CUP, TBLSP, TSP, G, OZ, K, UNIT})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Measurement {
    }
}
