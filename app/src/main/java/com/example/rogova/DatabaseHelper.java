package com.example.rogova;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "my_database";
    private static final int DATABASE_VERSION = 3;

    public static final String TABLE_USERS = "users";
    public static final String COLUMN_USER_ID = "_id";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_PASSWORD = "password";

    public static final String TABLE_DISHES = "dishes";
    public static final String COLUMN_DISH_ID = "_id";
    public static final String COLUMN_DISH_NAME = "name";
    public static final String COLUMN_DISH_DESCRIPTION = "description";
    public static final String COLUMN_DISH_PRICE = "price";

    private static final String CREATE_TABLE_USERS = "CREATE TABLE " + TABLE_USERS +
            "(" + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_USERNAME + " TEXT, " +
            COLUMN_PASSWORD + " TEXT)";

    private static final String CREATE_TABLE_DISHES = "CREATE TABLE " + TABLE_DISHES +
            "(" + COLUMN_DISH_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_DISH_NAME + " TEXT, " +
            COLUMN_DISH_DESCRIPTION + " TEXT, " +
            COLUMN_DISH_PRICE + " REAL)";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USERS);
        db.execSQL(CREATE_TABLE_DISHES);
        fillDishesTable(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DISHES);
        onCreate(db);
    }



    private void fillDishesTable(SQLiteDatabase db) {
        // Пример добавления блюд в таблицу
        addDish(db, "Паста", "Ароматная паста с соусом", 12.99);
        addDish(db, "Пицца", "Итальянская пицца с разнообразными начинками", 15.99);
        addDish(db, "Салат", "Свежий салат с овощами и соусом", 8.99);
        // Добавьте еще блюд по вашему усмотрению
    }

    public long addUser(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, username);
        values.put(COLUMN_PASSWORD, password);
        long id = db.insert(TABLE_USERS, null, values);
        db.close();
        return id;
    }

    public long addDish(SQLiteDatabase db, String name, String description, double price) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_DISH_NAME, name);
        values.put(COLUMN_DISH_DESCRIPTION, description);
        values.put(COLUMN_DISH_PRICE, price);
        return db.insert(TABLE_DISHES, null, values);
    }

    public List<Dish> getAllDishes() {
        List<Dish> dishes = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(
                TABLE_DISHES,
                null,
                null,
                null,
                null,
                null,
                null
        );

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(COLUMN_DISH_ID));
            String name = cursor.getString(cursor.getColumnIndex(COLUMN_DISH_NAME));
            String description = cursor.getString(cursor.getColumnIndex(COLUMN_DISH_DESCRIPTION));
            double price = cursor.getDouble(cursor.getColumnIndex(COLUMN_DISH_PRICE));

            Dish dish = new Dish(id, name, description, price);
            dishes.add(dish);
        }

        cursor.close();
        db.close();

        return dishes;
    }
}