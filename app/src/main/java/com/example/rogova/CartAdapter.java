package com.example.rogova;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class CartAdapter extends ArrayAdapter<CartItem> {

    private final Context context;
    private final int resource;
    private final List<CartItem> cartItems;

    public CartAdapter(Context context, int resource, List<CartItem> cartItems) {
        super(context, resource, cartItems);
        this.context = context;
        this.resource = resource;
        this.cartItems = cartItems;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View row = inflater.inflate(resource, parent, false);

        CartItem cartItem = cartItems.get(position);

        TextView tvDishName = row.findViewById(R.id.cartItemName);
        tvDishName.setText(cartItem.getName());

        TextView tvDishPrice = row.findViewById(R.id.cartItemPrice);
        tvDishPrice.setText(String.valueOf(cartItem.getPrice()));

        TextView tvQuantity = row.findViewById(R.id.cartItemQuantity);
        tvQuantity.setText(String.valueOf(cartItem.getQuantity()));

        Button btnRemoveFromCart = row.findViewById(R.id.btnRemoveFromCart);
        btnRemoveFromCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cartItems.remove(position);
                notifyDataSetChanged();
            }
        });

        return row;
    }
}