package com.example.rogova;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class OrderHistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);

        List<Order> orderHistoryList = (List<Order>) getIntent().getSerializableExtra("orderHistory");

        ArrayAdapter<String> orderAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, convertOrdersToStrings(orderHistoryList));
        ListView orderListView = findViewById(R.id.orderListView);
        orderListView.setAdapter(orderAdapter);
    }

    private List<String> convertOrdersToStrings(List<Order> orders) {
        List<String> orderStrings = new ArrayList<>();

        for (Order order : orders) {
            StringBuilder orderDetails = new StringBuilder();
            orderDetails.append("Номер телефона: ").append(order.getPhoneNumber()).append("\n");
            orderDetails.append("Адрес: ").append(order.getAddress()).append("\n");
            orderDetails.append("Список напитков:\n");

            double totalAmount = 0;

            for (CartItem cartItem : order.getCartItems()) {
                orderDetails.append("- ").append(cartItem.getName()).append("\n");
                totalAmount += cartItem.getPrice() * cartItem.getQuantity();
            }

            orderDetails.append(String.format(Locale.getDefault(), "Общая сумма: %.2f", totalAmount));

            orderStrings.add(orderDetails.toString());
        }

        return orderStrings;
    }
}