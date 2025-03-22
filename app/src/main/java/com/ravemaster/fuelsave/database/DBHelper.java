package com.ravemaster.fuelsave.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(@Nullable Context context) {
        super(context,"Eight.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table Pumps(id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, prevSale TEXT, prevLitre TEXT, totalSale TEXT, totalLitre TEXT, image INTEGER)");
        db.execSQL("create table CashPayments(id INTEGER PRIMARY KEY AUTOINCREMENT, cashName TEXT, lastAmount TEXT, accumulated TEXT)");
        db.execSQL("create table FuelStock(id INTEGER PRIMARY KEY AUTOINCREMENT, fuelName TEXT, initialFuel TEXT, currentFuel TEXT, addedFuel TEXT, fuelImage INTEGER)");
        db.execSQL("create table Stock(id INTEGER PRIMARY KEY AUTOINCREMENT, stockName TEXT, initialStock TEXT, currentStock TEXT, addedStock TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists Pumps");
        db.execSQL("drop table if exists CashPayments");
        db.execSQL("drop table if exists CurrentStock");
        db.execSQL("drop table if exists Stock");
    }

    public boolean insertPumps( String name, String sale, String amount, String totalAmounts, String totalSales, int image){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name",name);
        contentValues.put("prevSale",sale);
        contentValues.put("prevLitre",amount);
        contentValues.put("totalSale",totalSales);
        contentValues.put("totalLitre",totalAmounts);
        contentValues.put("image",image);
        long result = database.insert("Pumps",null,contentValues);
        return result != -1;
    }

    public boolean updatePumps(String name, String sale, String litre, String saleTotal, String litreTotal){
        SQLiteDatabase db = this.getWritableDatabase();

        double sale1 = Double.parseDouble(sale);
        double litre1 = Double.parseDouble(litre);
        double saleTotal1 = Double.parseDouble(saleTotal);
        double litreTotal1 = Double.parseDouble(litreTotal);
        double totalSale = saleTotal1 + sale1;
        double totalLitre = litreTotal1 + litre1;
        String finalSale = String.format("%.2f",totalSale);
        String finalLitre = String.format("%.2f",totalLitre);

        ContentValues contentValues = new ContentValues();
        contentValues.put("prevSale",sale);
        contentValues.put("prevLitre",litre);
        contentValues.put("totalSale",finalSale);
        contentValues.put("totalLitre",finalLitre);

        String whereClause = "name" + " = ?";
        String[] whereArgs = {name};

        long result = db.update("Pumps",contentValues,whereClause,whereArgs);
        return result != -1;
    }

    public Cursor getPump(String name){
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("Select * from Pumps where name = ?", new String[]{name});
    }

    public Cursor getPumps(){
        SQLiteDatabase database = this.getReadableDatabase();
        return database.rawQuery("Select * from Pumps ORDER BY id ASC",null);
    }

    public boolean insertCash( String name, String amount, String accumulated){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("cashName",name);
        contentValues.put("lastAmount",amount);
        contentValues.put("accumulated",accumulated);
        long result = database.insert("CashPayments",null,contentValues);
        return result != -1;
    }

    public boolean updatePayments(String name, String amount, String accumulated){
        SQLiteDatabase db = this.getWritableDatabase();

        double amount1 = Double.parseDouble(amount);
        double accumulated1 = Double.parseDouble(accumulated);
        double newTotal = amount1 + accumulated1;
        String finalTotal = String.format("%.2f",newTotal);

        ContentValues contentValues = new ContentValues();
        contentValues.put("lastAmount",amount);
        contentValues.put("accumulated",finalTotal);

        String whereClause = "cashName" + " = ?";
        String[] whereArgs = {name};

        long result = db.update("CashPayments",contentValues,whereClause,whereArgs);
        return result != -1;
    }

    public Cursor getPayment(String name){
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("Select * from CashPayments where cashName = ?", new String[]{name});
    }

    public Cursor getPayments(){
        SQLiteDatabase database = this.getReadableDatabase();
        return database.rawQuery("Select * from CashPayments ORDER BY id ASC",null);
    }

    public boolean insertFuelStock(String fuelName, String initial, String current, String added, int image){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("fuelName",fuelName);
        contentValues.put("initialFuel",initial);
        contentValues.put("currentFuel",current);
        contentValues.put("addedFuel",added);
        contentValues.put("fuelImage",image);
        long result = database.insert("FuelStock",null,contentValues);
        return result != -1;
    }

    public boolean updateFuelStock(String fuelName, String current, String added, String sold){
        SQLiteDatabase db = this.getWritableDatabase();

        double added1 = Double.parseDouble(added);
        double current1 = Double.parseDouble(current);
        double sold1 = Double.parseDouble(sold);

        double remaining = current1 - sold1 + added1;
        String finalCurrent = String.format("%.2f",remaining);


        ContentValues contentValues = new ContentValues();
        contentValues.put("currentFuel",finalCurrent);
        contentValues.put("addedFuel",added);

        String whereClause = "fuelName" + " = ?";
        String[] whereArgs = {fuelName};

        long result = db.update("FuelStock",contentValues,whereClause,whereArgs);
        return result != -1;
    }

    public Cursor getFuel(String name){
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("Select * from FuelStock where fuelName = ?", new String[]{name});
    }

    public Cursor getFuelStocks(){
        SQLiteDatabase database = this.getReadableDatabase();
        return database.rawQuery("Select * from FuelStock ORDER BY id ASC",null);
    }

    public boolean insertStock(String stockName, String initial, String current, String added){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("stockName",stockName);
        contentValues.put("initialStock",initial);
        contentValues.put("currentStock",current);
        contentValues.put("addedStock",added);
        long result = database.insert("Stock",null,contentValues);
        return result != -1;
    }

    public boolean updateStock(String name, String current, String added, String sold){
        SQLiteDatabase db = this.getWritableDatabase();

        double added1 = Double.parseDouble(added);
        double current1 = Double.parseDouble(current);
        double sold1 = Double.parseDouble(sold);

        double remaining = current1 - sold1 + added1;
        String finalCurrent = String.format("%.2f",remaining);


        ContentValues contentValues = new ContentValues();
        contentValues.put("currentStock",finalCurrent);
        contentValues.put("addedStock",added);

        String whereClause = "stockName" + " = ?";
        String[] whereArgs = {name};

        long result = db.update("Stock",contentValues,whereClause,whereArgs);
        return result != -1;
    }

    public Cursor getStock(String name){
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("Select * from Stock where stockName = ?", new String[]{name});
    }

    public Cursor getStocks(){
        SQLiteDatabase database = this.getReadableDatabase();
        return database.rawQuery("Select * from Stock ORDER BY id ASC",null);
    }

    public Boolean deletePump(String name) {
        SQLiteDatabase DB = this.getWritableDatabase();
        long result = DB.delete("Pumps", "name=?", new String[]{name});
        return result != -1;
    }

    public Boolean deleteCash(String name) {
        SQLiteDatabase DB = this.getWritableDatabase();
        long result = DB.delete("CashPayments", "cashName=?", new String[]{name});
        return result != -1;
    }

    public Boolean deleteTank(String name) {
        SQLiteDatabase DB = this.getWritableDatabase();
        long result = DB.delete("FuelStock", "fuelName=?", new String[]{name});
        return result != -1;
    }

    public Boolean deleteStock(String name) {
        SQLiteDatabase DB = this.getWritableDatabase();
        long result = DB.delete("Stock", "stockName=?", new String[]{name});
        return result != -1;
    }

}
