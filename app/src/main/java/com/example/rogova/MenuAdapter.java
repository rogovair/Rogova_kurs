package com.example.rogova;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import java.util.Locale;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder> {

    private Context context;
    private List<Dish> dishes;
    private OnAddToCartClickListener addToCartClickListener;

    public MenuAdapter(Context context, List<Dish> dishes, OnAddToCartClickListener addToCartClickListener) {
        this.context = context;
        this.dishes = dishes;
        this.addToCartClickListener = addToCartClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_menu, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Dish dish = dishes.get(position);

        holder.textDishName.setText(dish.getName());
        holder.textDishDescription.setText(dish.getDescription());
        holder.textDishPrice.setText(String.format(Locale.getDefault(), "%.2f", dish.getPrice()));

        holder.btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (addToCartClickListener != null) {
                    addToCartClickListener.onAddToCartClick(dish);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return dishes.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textDishName;
        TextView textDishDescription;
        TextView textDishPrice;
        Button btnAddToCart;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textDishName = itemView.findViewById(R.id.textDishName);
            textDishDescription = itemView.findViewById(R.id.textDishDescription);
            textDishPrice = itemView.findViewById(R.id.textDishPrice);
            btnAddToCart = itemView.findViewById(R.id.btnAddToCart);
        }
    }

    public interface OnAddToCartClickListener {
        void onAddToCartClick(Dish dish);
    }
}