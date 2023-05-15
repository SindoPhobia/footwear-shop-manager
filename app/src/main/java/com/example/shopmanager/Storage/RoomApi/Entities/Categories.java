package com.example.shopmanager.Storage.RoomApi.Entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity (tableName = "Categories")
public class Categories {
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo (name = "name")
    private String name;

    public Categories(String name, int id){
        this.name = name;
        this.id = id;
    }

    public Categories(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
