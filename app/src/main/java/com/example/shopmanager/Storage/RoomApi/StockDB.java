package com.example.shopmanager.Storage.RoomApi;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.shopmanager.Storage.RoomApi.Entities.Brands;
import com.example.shopmanager.Storage.RoomApi.Entities.Categories;
import com.example.shopmanager.Storage.RoomApi.Entities.Colors;
import com.example.shopmanager.Storage.RoomApi.Entities.Shoes;
import com.example.shopmanager.Storage.RoomApi.Entities.Sizes;
import com.example.shopmanager.Storage.RoomApi.Entities.Stock;

@Database(entities = {Shoes.class, Colors.class, Categories.class, Brands.class, Sizes.class, Stock.class}, version = 1)
public abstract class StockDB extends RoomDatabase{
    public abstract StockDao stockDao();
}
