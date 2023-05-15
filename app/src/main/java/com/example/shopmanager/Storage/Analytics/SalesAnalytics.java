package com.example.shopmanager.Storage.Analytics;

import android.util.Log;

import com.example.shopmanager.Storage.Firestore.Collections.Sale;
import com.example.shopmanager.Storage.Firestore.Collections.SoldStock;
import com.example.shopmanager.Storage.Firestore.FirestoreDB;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class SalesAnalytics {
    private float today;
    private float total;
    private float monthly;
    private float weekly;

    private FirestoreDB.CallbackAggregate cl;

    private long getStartToday(){
        String date = getFormattedDate();
        int hours = Integer.parseInt(date.substring(13, 15));
        int minutes = Integer.parseInt(date.substring(16, 18));
        int seconds = Integer.parseInt(date.substring(19, 21));
        long now = System.currentTimeMillis();
        return now - ((long)hours*3600000 + (long)minutes*60000 + (long)seconds*1000);
    }

    private long getStartMonth(){
        String date = getFormattedDate();
        int days = Integer.parseInt(date.substring(3,5));
        return getStartToday() - (long)days*86400000;
    }

    private long getStartWeek(){
        return getStartToday() - (long)getDay()*86400000;
    }

    public void init(FirestoreDB.CallbackAggregate cl){
        this.cl = cl;
        updateToday();
    }

    private float calculatePrice(Sale[] sales){
        float tmp = 0;
        for(Sale sale : sales){
            tmp = tmp + Arrays.stream(sale.getSoldStock()).map(SoldStock::getPrice).reduce(0f, (a,b) -> a+b);
        }
        return tmp;
    }
    public void updateToday(){
        FirestoreDB.getSalesDateRange(getStartToday(), System.currentTimeMillis(), new FirestoreDB.Callback() {
            @Override
            public void onComplete(Sale[] sales) {
                today = calculatePrice(sales);
                Log.d("sales", total+"-"+monthly+"-"+weekly+"-"+today);
                updateWeekly();
            }

            @Override
            public void onError() {

            }
        });
    }

    public void updateWeekly(){
        FirestoreDB.getSalesDateRange(getStartWeek(), getStartToday(), new FirestoreDB.Callback() {
            @Override
            public void onComplete(Sale[] sales) {
                weekly = calculatePrice(sales) + today;
                Log.d("sales", total+"-"+monthly+"-"+weekly+"-"+today);
                updateMonthly();
            }

            @Override
            public void onError() {

            }
        });
    }

    public void updateMonthly(){
        FirestoreDB.getSalesDateRange(getStartMonth(), getStartWeek(), new FirestoreDB.Callback() {
            @Override
            public void onComplete(Sale[] sales) {
                monthly = calculatePrice(sales) + weekly;
                Log.d("sales", total+"-"+monthly+"-"+weekly+"-"+today);
                updateTotal();
            }

            @Override
            public void onError() {

            }
        });
    }

    public void updateTotal(){
        FirestoreDB.getSalesDateRange(0, getStartMonth(), new FirestoreDB.Callback() {
            @Override
            public void onComplete(Sale[] sales) {
                total = calculatePrice(sales) + monthly;
                Log.d("sales", total+"-"+monthly+"-"+weekly+"-"+today);
                cl.onComplete(0);
            }

            @Override
            public void onError() {
                cl.onError();
            }
        });
    }

    private String getFormattedDate(){
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/YYYY - HH:mm:ss");
        formatter.setTimeZone(TimeZone.getTimeZone("Europe/Athens"));
        String time = formatter.format(date);
        return time;
    }

    private int getDay(){
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("EEEE");
        formatter.setTimeZone(TimeZone.getTimeZone("Europe/Athens"));
        String day = formatter.format(date);
        if(day.equalsIgnoreCase("Sunday")) return 6;
        if(day.equalsIgnoreCase("Monday")) return 0;
        if(day.equalsIgnoreCase("Tuesday")) return 1;
        if(day.equalsIgnoreCase("Wednesday")) return 2;
        if(day.equalsIgnoreCase("Thursday")) return 3;
        if(day.equalsIgnoreCase("Friday")) return 4;
        if(day.equalsIgnoreCase("Saturday")) return 5;
        return 0;
    }

    public void onNewSale(Sale sale){
        Sale[] sales = new Sale[1];
        sales[0] = sale;
        float price = calculatePrice(sales);
        total += price;
        if(sale.getDate() > getStartToday()) today += price;
        if(sale.getDate() > getStartWeek()) weekly += price;
        if(sale.getDate() > getStartMonth()) monthly += price;
    }

    public void onDeleteSale(Sale sale){
        Sale[] sales = new Sale[1];
        sales[0] = sale;
        float price = calculatePrice(sales);
        total -= price;
        if(sale.getDate() > getStartToday()) today -= price;
        if(sale.getDate() > getStartWeek()) weekly -= price;
        if(sale.getDate() > getStartMonth()) monthly -= price;
    }

    public float getToday() {
        return today;
    }

    public void setToday(float today) {
        this.today = today;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public float getMonthly() {
        return monthly;
    }

    public void setMonthly(float monthly) {
        this.monthly = monthly;
    }

    public float getWeekly() {
        return weekly;
    }

    public void setWeekly(float weekly) {
        this.weekly = weekly;
    }
}
