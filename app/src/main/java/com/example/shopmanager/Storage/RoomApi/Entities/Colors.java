package com.example.shopmanager.Storage.RoomApi.Entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity (tableName = "Colors")
public class Colors {
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo (name = "name")
    private String name;

    public Colors(){}

    public Colors(String name, int id){
        this.name = name;
        this.id = id;
    }

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
