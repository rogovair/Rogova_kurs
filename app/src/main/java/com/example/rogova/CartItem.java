package com.example.rogova;

import android.os.Parcel;
import android.os.Parcelable;

public class CartItem implements Parcelable {
    private long dishId;
    private String name;
    private double price;
    private int quantity;

    public CartItem(long dishId, String name, double price) {
        this.dishId = dishId;
        this.name = name;
        this.price = price;
        this.quantity = 1;
    }

    protected CartItem(Parcel in) {
        dishId = in.readLong();
        name = in.readString();
        price = in.readDouble();
        quantity = in.readInt();
    }

    public static final Creator<CartItem> CREATOR = new Creator<CartItem>() {
        @Override
        public CartItem createFromParcel(Parcel in) {
            return new CartItem(in);
        }

        @Override
        public CartItem[] newArray(int size) {
            return new CartItem[size];
        }
    };

    public long getDishId() {
        return dishId;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void incrementQuantity() {
        quantity++;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(dishId);
        dest.writeString(name);
        dest.writeDouble(price);
        dest.writeInt(quantity);
    }
}