package com.aboutblank.baking_app.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.StringDef;

import com.google.gson.annotations.SerializedName;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class Ingredient implements Parcelable {

    public static final Creator<Ingredient> CREATOR = new Creator<Ingredient>() {
        @Override
        public Ingredient createFromParcel(Parcel in) {
            return new Ingredient(in);
        }

        @Override
        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }
    };

    private double quantity;

    @SerializedName("measure")
    private @Measurement
    String measurement;
    @SerializedName("ingredient")
    private String name;

    public Ingredient(double quantity, @Measurement String measurement, String name) {
        this.quantity = quantity;
        this.measurement = measurement;
        this.name = name;
    }

    public Ingredient(Parcel in) {
        quantity = in.readDouble();
        measurement = in.readString();
        name = in.readString();
    }

    public double getQuantity() {
        return quantity;
    }

    public @Measurement
    String getMeasurement() {
        return measurement;
    }

    public String getName() {
        return name;
    }

    public String toPrint() {
        return String.format("%s %s of %s", String.valueOf(quantity),
                measurement.toLowerCase(), name);
    }

    @Override
    public String toString() {
        return "Ingredient{" +
                "quantity=" + quantity +
                ", measurement=" + measurement +
                ", name='" + name + '\'' +
                '}';
    }

    public static final String CUP = "CUP";
    public static final String TBLSP = "TBLSP";
    public static final String TSP = "TSP";
    public static final String G = "G";
    public static final String OZ = "OZ";
    public static final String K = "K";
    public static final String UNIT = "UNIT";

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(quantity);
        dest.writeString(measurement);
        dest.writeString(name);
    }

    @StringDef({CUP, TBLSP, TSP, G, OZ, K, UNIT})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Measurement {
    }
}
