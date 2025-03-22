package com.ravemaster.fuelsave.activities;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.ravemaster.fuelsave.database.DBHelper;
import com.ravemaster.fuelsave.databinding.ActivityInventoryBinding;
import com.ravemaster.fuelsave.models.Fuel;
import com.ravemaster.fuelsave.models.Stock;

public class InventoryActivity extends AppCompatActivity {

    ActivityInventoryBinding binding;
    String amount = "";
    DBHelper helper;
    EditText one;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityInventoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Stock stock = (Stock) getIntent().getSerializableExtra("Stock");
        helper = new DBHelper(this);
        showData(getStock(stock.stockName));
        one = binding.enterAddedStock.getEditText();
        binding.btnLogStock.setOnClickListener(v->{
            makeEntry(stock.stockName, stock.currentStock,"1000");
        });
    }
    private void showData(Stock stock) {
        binding.nameStock.setText(stock.stockName);
        binding.currentStock.setText(stock.currentStock);
        binding.initialStock.setText(stock.initialStock);
        binding.addedStock.setText(stock.addedStock);
    }

    private void makeEntry(String name,String current, String sold) {
        amount = one.getText().toString();
        boolean success = helper.updateStock(name,current,amount,sold);
        if (success){
            showToasts("Entry made successfully!");
            showData(getStock(binding.nameStock.getText().toString()));
        } else {
            showToasts("Failed");
        }
    }

    private Stock getStock(String stockName) {
        Cursor cursor = helper.getStock(stockName);
        Stock stock = null;
        if (cursor.getCount() == 0){
        } else {
            while (cursor.moveToNext()){
                String name = cursor.getString(1);
                String initial = cursor.getString(2);
                String current = cursor.getString(3);
                String added = cursor.getString(4);
                stock = new Stock(name,initial,current,added);
            }
        }
        return stock;
    }
    private void showToasts(String message){
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}