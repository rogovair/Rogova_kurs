package com.example.rogova;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {

    private List<CartItem> cartItems;
    private ArrayAdapter<CartItem> cartAdapter;

    private static final int REQUEST_CODE_MENU = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        SharedPreferences preferences = getSharedPreferences("cart_prefs", MODE_PRIVATE);
        String cartJson = preferences.getString("cart_items", "");
        Type type = new TypeToken<List<CartItem>>() {}.getType();
        cartItems = new Gson().fromJson(cartJson, type);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("cartItems")) {
            ArrayList<CartItem> cartItemsList = (ArrayList<CartItem>) intent.getSerializableExtra("cartItems");

            cartItems = new ArrayList<>(cartItemsList);
        } else {
            if (cartItems == null) {
                cartItems = new ArrayList<>();
            }
        }

        cartAdapter = new CartAdapter(this, R.layout.cart_item, cartItems);
        ListView cartListView = findViewById(R.id.cartListView);
        cartListView.setAdapter(cartAdapter);

        Button btnPlaceOrder = findViewById(R.id.btnPlaceOrder);
        btnPlaceOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showOrderDialog();
            }
        });

        cartListView.setOnItemClickListener((parent, view, position, id) -> {
            CartItem selectedItem = cartItems.get(position);
            cartItems.remove(selectedItem);
            cartAdapter.notifyDataSetChanged();
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharedPreferences preferences = getSharedPreferences("cart_prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("cart_items", new Gson().toJson(cartItems));
        editor.apply();
    }

    private CartItem findItemInCart(long dishId) {
        for (CartItem item : cartItems) {
            if (item.getDishId() == dishId) {
                return item;
            }
        }
        return null;
    }

    private void showOrderDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Оформление заказа");

        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_place_order, null);
        builder.setView(dialogView);

        EditText etPhoneNumber = dialogView.findViewById(R.id.etPhoneNumber);
        EditText etAddress = dialogView.findViewById(R.id.etAddress);

        builder.setPositiveButton("Оформить", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String phoneNumber = etPhoneNumber.getText().toString().trim();
                String address = etAddress.getText().toString().trim();

                if (!TextUtils.isEmpty(phoneNumber) && !TextUtils.isEmpty(address)) {
                    Order order = new Order(phoneNumber, address, new ArrayList<>(cartItems));

                    OrderHistoryManager.getInstance().addOrder(order);

                    cartItems.clear();
                    cartAdapter.notifyDataSetChanged();

                    updateCart();

                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("cartItems", new ArrayList<>(cartItems));
                    setResult(RESULT_OK, resultIntent);
                    finish();

                    Toast.makeText(CartActivity.this, "Заказ оформлен", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(CartActivity.this, "Заполните все поля", Toast.LENGTH_SHORT).show();
                }
            }
        });

        builder.setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        builder.create().show();
    }

    private void updateCart() {
        SharedPreferences preferences = getSharedPreferences("cart_prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("cart_items", new Gson().toJson(cartItems));
        editor.apply();
    }
}