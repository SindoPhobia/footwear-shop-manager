package com.example.shopmanager.Storage.RoomApi.Entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity (tableName = "Stock",
        primaryKeys = {"shoe_id", "color_id", "brand_id","category_id", "size_id","id"},
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
                @ForeignKey(entity = Sizes.class,
                        parentColumns = "id",
                        childColumns = "size_id",
                        onDelete = ForeignKey.CASCADE,
                        onUpdate = ForeignKey.CASCADE)
        }
)
public class Stock {
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

    @ColumnInfo(name="size_id") @NonNull
    private int sizeId;

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

    public int getSizeId() {
        return sizeId;
    }

    public void setSizeId(int sizeId) {
        this.sizeId = sizeId;
    }
}
