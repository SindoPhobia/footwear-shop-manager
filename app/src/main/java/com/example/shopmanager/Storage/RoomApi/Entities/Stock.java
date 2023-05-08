package com.example.shopmanager.Storage.RoomApi.Entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity (tableName = "Stock",
        indices = {
                @Index(value={"shoe_id", "brand_id", "category_id", "color_id"}, unique = true),
        },
        foreignKeys = {
                @ForeignKey(entity = Shoes.class,
                        parentColumns = "id",
                        childColumns = "shoe_id",
                        onDelete = ForeignKey.CASCADE,
                        onUpdate = ForeignKey.CASCADE),
                @ForeignKey(entity = Colors.class,
                        parentColumns = "id",
                        childColumns = "color_id",
                        onDelete = ForeignKey.CASCADE,
                        onUpdate = ForeignKey.CASCADE),
                @ForeignKey(entity = Brands.class,
                        parentColumns = "id",
                        childColumns = "brand_id",
                        onDelete = ForeignKey.CASCADE,
                        onUpdate = ForeignKey.CASCADE),
                @ForeignKey(entity = Categories.class,
                        parentColumns = "id",
                        childColumns = "category_id",
                        onDelete = ForeignKey.CASCADE,
                        onUpdate = ForeignKey.CASCADE),
        }
)
public class Stock {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") @NonNull
    private int id;

    @ColumnInfo(name="shoe_id") @NonNull
    private int shoeId;

    @ColumnInfo(name="category_id") @NonNull
    private int categoryId;

    @ColumnInfo(name="color_id") @NonNull
    private int colorId;

    @ColumnInfo(name="brand_id") @NonNull
    private int brandId;

    public Stock(int shoeId, int categoryId, int colorId, int brandId) {
        this.shoeId = shoeId;
        this.categoryId = categoryId;
        this.colorId = colorId;
        this.brandId = brandId;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getShoeId() {
        return shoeId;
    }

    public void setShoeId(int shoeId) {
        this.shoeId = shoeId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getColorId() {
        return colorId;
    }

    public void setColorId(int colorId) {
        this.colorId = colorId;
    }

    public int getBrandId() {
        return brandId;
    }

    public void setBrandId(int brandId) {
        this.brandId = brandId;
    }

}
