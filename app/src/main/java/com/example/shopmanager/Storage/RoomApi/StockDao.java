package com.example.shopmanager.Storage.RoomApi;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.shopmanager.Storage.RoomApi.Entities.Brands;
import com.example.shopmanager.Storage.RoomApi.Entities.Categories;
import com.example.shopmanager.Storage.RoomApi.Entities.Colors;
import com.example.shopmanager.Storage.RoomApi.Entities.Shoes;
import com.example.shopmanager.Storage.RoomApi.Entities.Stock;

import java.util.List;

@Dao
public interface StockDao{

    @Insert
    public void insertShoe(Shoes shoe);

    @Insert
    public void insertColors(Colors color);

    @Insert
    public void insertBrands(Brands brand);

    @Insert
    public void insertCategories(Categories categories);

    @Insert
    public void insertStock(Stock stock);

    @Update
    public void updateShoe(Shoes shoe);

    @Update
    public void updateColors(Colors color);

    @Update
    public void updateBrands(Brands brand);

    @Update
    public void updateCategories(Categories categories);

    @Update
    public void updateStock(Stock stock);

    @Delete
    public void deleteShoe(Shoes shoe);


    @Delete
    public void deleteColors(Colors color);

    @Delete
    public void deleteBrands(Brands brand);

    @Delete
    public void deleteCategories(Categories categories);

    @Delete
    public void deleteStock(Stock stock);

    @Query("select * from Stock")
    public List<Stock> getStock();

    @Query("select s2.name as name, s2.code as code, s2.gender as gender, " +
            "s2.date as date, s2.sale_enabled as sale_enabled," +
            "s2.sale_price as sale_price, s2.price as price," +
            "c1.name as color, c2.name as category, " +
            "s1.id as id, s2.sizes as sizes, " +
            "b1.name as brand " +
            "from Stock s1 " +
            "inner join Shoes s2 on s1.shoe_id=s2.id " +
            "inner join Colors c1 on c1.id=s1.color_id " +
            "inner join Categories c2 on c2.id=s1.category_id " +
            "inner join Brands b1 on b1.id=s1.brand_id " +
            "where s1.id=:stockId")
    public List<Shoe> getStock(int stockId);

    @Query("select s2.name as name, s2.code as code, s2.gender as gender, " +
            "s2.date as date, s2.sale_enabled as sale_enabled," +
            "s2.sale_price as sale_price, s2.price as price," +
            "c1.name as color, c2.name as category," +
            "s1.id as id, s2.sizes as sizes, " +
            "b1.name as brand" +
            " from Stock s1 " +
            "inner join Shoes s2 on s1.shoe_id=s2.id " +
            "inner join Colors c1 on c1.id=s1.color_id " +
            "inner join Categories c2 on c2.id=s1.category_id " +
            "inner join Brands b1 on b1.id=s1.brand_id " +
            "where s2.name like '%' || :shoeName || '%'")
    public List<Shoe> getStock(String shoeName);

    @Query("select s2.name as name, s2.code as code, s2.gender as gender, " +
            "s2.date as date, s2.sale_enabled as sale_enabled," +
            "s2.sale_price as sale_price, s2.price as price," +
            "c1.name as color, c2.name as category," +
            "s1.id as id, s2.sizes as sizes, " +
            "b1.name as brand" +
            " from Stock s1 " +
            "inner join Shoes s2 on s1.shoe_id=s2.id " +
            "inner join Colors c1 on c1.id=s1.color_id " +
            "inner join Categories c2 on c2.id=s1.category_id " +
            "inner join Brands b1 on b1.id=s1.brand_id " +
            "where c2.name=:category")
    public List<Shoe> getStockCategory(String category);

    @Query("select s2.name as name, s2.code as code, s2.gender as gender, " +
            "s2.date as date, s2.sale_enabled as sale_enabled," +
            "s2.sale_price as sale_price, s2.price as price," +
            "c1.name as color, c2.name as category," +
            "s1.id as id, s2.sizes as sizes, " +
            "b1.name as brand" +
            " from Stock s1 " +
            "inner join Shoes s2 on s1.shoe_id=s2.id " +
            "inner join Colors c1 on c1.id=s1.color_id " +
            "inner join Categories c2 on c2.id=s1.category_id " +
            "inner join Brands b1 on b1.id=s1.brand_id " +
            "where s2.gender=:gender")
    public List<Shoe> getStockGender(String gender);

    @Query("select s2.name as name, s2.code as code, s2.gender as gender, " +
            "s2.date as date, s2.sale_enabled as sale_enabled," +
            "s2.sale_price as sale_price, s2.price as price," +
            "c1.name as color, c2.name as category," +
            "s1.id as id, s2.sizes as sizes, " +
            "b1.name as brand" +
            " from Stock s1 " +
            "inner join Shoes s2 on s1.shoe_id=s2.id " +
            "inner join Colors c1 on c1.id=s1.color_id " +
            "inner join Categories c2 on c2.id=s1.category_id " +
            "inner join Brands b1 on b1.id=s1.brand_id " +
            "where c1.name=:color")
    public List<Shoe> getStockColor(String color);

    @Query("select s2.name as name, s2.code as code, s2.gender as gender, " +
            "s2.date as date, s2.sale_enabled as sale_enabled," +
            "s2.sale_price as sale_price, s2.price as price," +
            "c1.name as color, c2.name as category," +
            "s1.id as id, s2.sizes as sizes, " +
            "b1.name as brand" +
            " from Stock s1 " +
            "inner join Shoes s2 on s1.shoe_id=s2.id " +
            "inner join Colors c1 on c1.id=s1.color_id " +
            "inner join Categories c2 on c2.id=s1.category_id " +
            "inner join Brands b1 on b1.id=s1.brand_id " +
            "where b1.name=:brand")
    public List<Shoe> getStockBrand(String brand);

    @Query("select s2.name as name, s2.code as code, s2.gender as gender, " +
            "s2.date as date, s2.sale_enabled as sale_enabled," +
            "s2.sale_price as sale_price, s2.price as price," +
            "c1.name as color, c2.name as category," +
            "s1.id as id, s2.sizes as sizes, " +
            "b1.name as brand" +
            " from Stock s1 " +
            "inner join Shoes s2 on s1.shoe_id=s2.id " +
            "inner join Colors c1 on c1.id=s1.color_id " +
            "inner join Categories c2 on c2.id=s1.category_id " +
            "inner join Brands b1 on b1.id=s1.brand_id " +
            "where s2.price<=:ending and s2.price>=:starting")
    public List<Shoe> getStockPrice(float starting, float ending);

    @Query("select s2.name as name, s2.code as code, s2.gender as gender, " +
            "s2.date as date, s2.sale_enabled as sale_enabled," +
            "s2.sale_price as sale_price, s2.price as price," +
            "c1.name as color, c2.name as category," +
            "s1.id as id, s2.sizes as sizes, " +
            "b1.name as brand" +
            " from Stock s1 " +
            "inner join Shoes s2 on s1.shoe_id=s2.id " +
            "inner join Colors c1 on c1.id=s1.color_id " +
            "inner join Categories c2 on c2.id=s1.category_id " +
            "inner join Brands b1 on b1.id=s1.brand_id " +
            "where s2.date<=:ending and s2.date>=:starting")
    public List<Shoe> getStockDate(long starting, long ending);

    @Query("select s2.name as name, s2.code as code, s2.gender as gender, " +
            "s2.date as date, s2.sale_enabled as sale_enabled," +
            "s2.sale_price as sale_price, s2.price as price," +
            "c1.name as color, c2.name as category," +
            "s1.id as id, s2.sizes as sizes, " +
            "b1.name as brand" +
            " from Stock s1 " +
            "inner join Shoes s2 on s1.shoe_id=s2.id " +
            "inner join Colors c1 on c1.id=s1.color_id " +
            "inner join Categories c2 on c2.id=s1.category_id " +
            "inner join Brands b1 on b1.id=s1.brand_id " +
            "order by s2.date desc")
    public List<Shoe> getStockDesc();

    @Query("select s2.name as name, s2.code as code, s2.gender as gender, " +
            "s2.date as date, s2.sale_enabled as sale_enabled," +
            "s2.sale_price as sale_price, s2.price as price," +
            "c1.name as color, c2.name as category," +
            "s1.id as id, s2.sizes as sizes, " +
            "b1.name as brand" +
            " from Stock s1 " +
            "inner join Shoes s2 on s1.shoe_id=s2.id " +
            "inner join Colors c1 on c1.id=s1.color_id " +
            "inner join Categories c2 on c2.id=s1.category_id " +
            "inner join Brands b1 on b1.id=s1.brand_id " +
            "order by s2.date asc")
    public List<Shoe> getStockAsc();

}
