package com.example.shopmanager.Storage.RoomApi;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.shopmanager.MainActivity;
import com.example.shopmanager.Storage.RoomApi.Entities.Brands;
import com.example.shopmanager.Storage.RoomApi.Entities.Categories;
import com.example.shopmanager.Storage.RoomApi.Entities.Colors;
import com.example.shopmanager.Storage.RoomApi.Entities.Shoes;
import com.example.shopmanager.Storage.RoomApi.Entities.Stock;

@Database(entities = {Shoes.class, Colors.class, Categories.class, Brands.class, Stock.class}, version = 1)
public abstract class StockDB extends RoomDatabase{
    public abstract StockDao stockDao();
    public void createShoe(Shoe data){
        int stock_id = data.getId();
        Shoes s = getShoe(data);
        Colors c = getColor(data);
        Categories cat = getCategory(data);
        Brands b = getBrand(data);
        StockDao dao = MainActivity.stockDatabase.stockDao();
        if(stock_id==0){
            this.stockDao().insertShoe(s);
            Stock st = new Stock(
                    dao.getShoeRaw(s.getCode(),
                            s.getDate()).getId(), cat.getId(), c.getId(), b.getId());
            this.stockDao().insertStock(st);
            return;
        }
        Stock st = dao.getStockRaw(stock_id);
        st.setBrandId(b.getId());
        st.setCategoryId(cat.getId());
        st.setColorId(c.getId());
        s.setId(st.getShoeId());
        dao.updateShoe(s);
        dao.updateStock(st);
    }

    private Shoes getShoe(Shoe data){
        return new Shoes(
                data.getName(), data.getPrice(), data.isSale_enabled(),
                data.getSale_price(), data.getCode(),
                data.getDate(), data.getGender()
        );
    }

    private Colors getColor(Shoe data){
        return MainActivity.stockDatabase.stockDao().getColorRaw(data.getColor());
    }

    private Brands getBrand(Shoe data){
        return MainActivity.stockDatabase.stockDao().getBrandsRaw(data.getBrand());
    }

    private Categories getCategory(Shoe data){
        return MainActivity.stockDatabase.stockDao().getCategoriesRaw(data.getCategory());
    }
}
