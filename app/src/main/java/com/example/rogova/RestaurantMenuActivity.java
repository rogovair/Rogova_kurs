package com.example.rogova;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import android.content.Intent;
import java.io.Serializable;

public class RestaurantMenuActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MenuAdapter menuAdapter;
    private DatabaseHelper databaseHelper;
    private List<CartItem> cartItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_menu);

        recyclerView = findViewById(R.id.recyclerViewMenu);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        databaseHelper = new DatabaseHelper(this);
        menuAdapter = new MenuAdapter(this, getDishesFromDatabase(), new MenuAdapter.OnAddToCartClickListener() {
            @Override
            public void onAddToCartClick(Dish dish) {
                addToCart(dish);
            }
        });
        recyclerView.setAdapter(menuAdapter);

        cartItems = new ArrayList<>();

        Button btnAddToCart = findViewById(R.id.btnAddToCart);
        btnAddToCart.setOnClickListener(v -> openCartActivity());
    }

    private List<Dish> getDishesFromDatabase() {
        if (databaseHelper != null) {
            return databaseHelper.getAllDishes();
        } else {
            return new ArrayList<>();
        }
    }

    private void addToCart(Dish dish) {
        CartItem cartItem = new CartItem(dish.getId(), dish.getName(), dish.getPrice());

        if (cartItems.contains(cartItem)) {
            CartItem existingItem = cartItems.get(cartItems.indexOf(cartItem));
            existingItem.incrementQuantity();
        } else {
            cartItems.add(cartItem);
        }

        String toastMessage = String.format("%s добавлен в корзину", dish.getName());
        Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT).show();
    }


    private CartActivity getCartActivity() {
        if (getParent() != null && getParent() instanceof CartActivity) {
            return (CartActivity) getParent();
        } else {
            return null;
        }
    }

    private void openCartActivity() {
        Intent intent = new Intent(RestaurantMenuActivity.this, CartActivity.class);

        intent.putExtra("cartItems", (Serializable) cartItems);

        startActivity(intent);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putSerializable("cartItems", new ArrayList<>(cartItems));
        super.onSaveInstanceState(outState);
    }

}