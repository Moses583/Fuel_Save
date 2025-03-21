package com.ravemaster.fuelsave.activities;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.ravemaster.fuelsave.R;
import com.ravemaster.fuelsave.database.DBHelper;
import com.ravemaster.fuelsave.models.Pump;

public class MakeEntryActivity extends AppCompatActivity {

    ImageView imageView;
    TextView totalAmount, totalSales, lastAmount, lastSale, name;
    Button button;
    String amount = "";
    String sale = "";
    TextInputLayout enterAmount, enterSales;
    EditText one, two;
    DBHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_make_entry);
        helper = new DBHelper(this);
        Pump pump = (Pump) getIntent().getSerializableExtra("pump");
        initViews();
        showData(getData(pump.getName()));
        one = enterAmount.getEditText();
        two = enterSales.getEditText();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeEntry(getData(pump.getName()).getTotalSale(), getData(pump.getName()).getTotalLitre());
            }
        });
    }

    private Pump getData(String pumpName) {
        Cursor cursor = helper.getPump(pumpName);
        Pump pump = null;
        if (cursor.getCount() == 0){
            Toast.makeText(this, "There is no such pump!", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()){
                String name = cursor.getString(1);
                String prevSale = cursor.getString(2);
                String prevLitre = cursor.getString(3);
                String totalSale = cursor.getString(4);
                String totalLitre = cursor.getString(5);
                int image = cursor.getInt(6);
                pump = new Pump(name,prevLitre,prevSale, totalLitre, totalSale, image);
            }
        }
        return pump;
    }

    private void makeEntry(String totalSale, String totalLitre) {
       amount = one.getText().toString();
       sale = two.getText().toString();
       boolean success = helper.updatePumps(name.getText().toString(),sale,amount,totalSale,totalLitre);
       if (success){
           Toast.makeText(this, "Entry made successfully!", Toast.LENGTH_SHORT).show();
           showData(getData(name.getText().toString()));
           one.setText("");
           two.setText("");
       } else {
           Toast.makeText(this, "Operation failed!", Toast.LENGTH_SHORT).show();
       }
    }

    private void showData(Pump pump) {
        totalAmount.setText(pump.getTotalLitre());
        totalSales.setText(pump.getTotalSale());
        lastAmount.setText(pump.getPrevLitre());
        lastSale.setText(pump.getPrevSale());
        name.setText(pump.getName());
        imageView.setBackgroundResource(pump.getImage());
    }

    private void initViews() {
        imageView = findViewById(R.id.imagePump);
        name = findViewById(R.id.namePump);
        totalAmount = findViewById(R.id.txtLitresTotal);
        totalSales = findViewById(R.id.txtSalesTotal);
        lastAmount = findViewById(R.id.txtLitresToday);
        lastSale = findViewById(R.id.txtSalesToday);
        button = findViewById(R.id.btnEnter);
        enterAmount = findViewById(R.id.enterLitres);
        enterSales = findViewById(R.id.enterSales);
    }
}