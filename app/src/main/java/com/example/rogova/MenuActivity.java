package com.example.rogova;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.content.Intent;
import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Button btnViewMenu = findViewById(R.id.btnViewMenu);
        btnViewMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, RestaurantMenuActivity.class);
                startActivity(intent);
            }
        });

        Button btnOrderHistory = findViewById(R.id.btnOrderHistory);
        btnOrderHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Order> orderHistoryList = OrderHistoryManager.getInstance().getOrderHistory();

                Intent intent = new Intent(MenuActivity.this, OrderHistoryActivity.class);
                intent.putExtra("orderHistory", new ArrayList<>(orderHistoryList));
                startActivity(intent);
            }
        });
    }
}