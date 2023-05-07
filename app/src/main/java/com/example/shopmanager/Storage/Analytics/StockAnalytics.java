package com.example.shopmanager.Storage.Analytics;

public class StockAnalytics {
    private int stockTotal;
    private int categoriesTotal;

    public int getStockTotal() {
        return stockTotal;
    }

    public void setStockTotal(int stockTotal) {
        this.stockTotal = stockTotal;
    }

    public int getCategoriesTotal() {
        return categoriesTotal;
    }

    public void setCategoriesTotal(int categoriesTotal) {
        this.categoriesTotal = categoriesTotal;
    }

    @Override
    public String toString() {
        return "StockAnalytics{" +
                "stockTotal=" + stockTotal +
                ", categoriesTotal=" + categoriesTotal +
                '}';
    }
}
