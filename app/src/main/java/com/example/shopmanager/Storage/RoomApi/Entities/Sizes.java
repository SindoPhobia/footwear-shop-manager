package com.example.shopmanager.Storage.RoomApi.Entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Sizes")
public class Sizes {

    @PrimaryKey(autoGenerate = true) @ColumnInfo(name="id")
    private int id;

    @ColumnInfo(name="number")
    private int number;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
