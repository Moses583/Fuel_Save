package com.ravemaster.fuelsave.activities;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.ravemaster.fuelsave.R;
import com.ravemaster.fuelsave.database.DBHelper;
import com.ravemaster.fuelsave.databinding.ActivityFuelStockBinding;
import com.ravemaster.fuelsave.databinding.ActivityRecordPaymentBinding;
import com.ravemaster.fuelsave.models.Cash;
import com.ravemaster.fuelsave.models.Fuel;

public class FuelStockActivity extends AppCompatActivity {

    ActivityFuelStockBinding binding;
    String amount = "";
    DBHelper helper;
    EditText one;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityFuelStockBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Fuel fuel = (Fuel) getIntent().getSerializableExtra("fuel");
        helper = new DBHelper(this);
        showData(getFuel(fuel.fuelName));
        one = binding.enterAddedFuelStock.getEditText();
        binding.btnLogTank.setOnClickListener(v->{
            makeEntry(fuel.fuelName, fuel.currentStock,"1000");
        });
    }
    private void showData(Fuel fuel) {
        binding.nameTank.setText(fuel.fuelName);
        binding.imageTank.setBackgroundResource(fuel.image);
        binding.currentTank.setText(fuel.currentStock);
        binding.initialTank.setText(fuel.initialStock);
        binding.addedTank.setText(fuel.addedStock);
    }

    private void makeEntry(String name,String current, String sold) {
        amount = one.getText().toString();
        boolean success = helper.updateFuelStock(name,current,amount,sold);
        if (success){
            showToasts("Entry made successfully!");
            showData(getFuel(binding.nameTank.getText().toString()));
        } else {
            showToasts("Failed");
        }
    }

    private Fuel getFuel(String fuelName) {
        Cursor cursor = helper.getFuel(fuelName);
        Fuel fuel = null;
        if (cursor.getCount() == 0){
        } else {
            while (cursor.moveToNext()){
                String name = cursor.getString(1);
                String initial = cursor.getString(2);
                String current = cursor.getString(3);
                String added = cursor.getString(4);
                int image = cursor.getInt(5);
                fuel = new Fuel(name,initial,current,added,image);
            }
        }
        return fuel;
    }
    private void showToasts(String message){
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

}