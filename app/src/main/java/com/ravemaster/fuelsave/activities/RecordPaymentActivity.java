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
import com.ravemaster.fuelsave.databinding.ActivityRecordPaymentBinding;
import com.ravemaster.fuelsave.models.Cash;
import com.ravemaster.fuelsave.models.Pump;

public class RecordPaymentActivity extends AppCompatActivity {

    ActivityRecordPaymentBinding binding;
    String amount = "";
    String party = "";
    DBHelper helper;
    EditText one,two;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityRecordPaymentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Cash cash = (Cash) getIntent().getSerializableExtra("cash");
        helper = new DBHelper(this);
        showData(getPayment(cash.name));
        one = binding.layoutEnterAmount.getEditText();
        two = binding.layoutEnterParty.getEditText();
        binding.btnLogPayment.setOnClickListener(v->{
            makeEntry(cash.name,cash.accumulated);
        });
    }

    private void showData(Cash payment) {
        binding.namePayment.setText(payment.name);
        binding.amountPayment.setText(payment.amount);
        binding.partyPayment.setText(payment.party);
        binding.amountPaymentTotal.setText(payment.accumulated);
    }

    private void makeEntry(String name, String accumulated) {
        amount = one.getText().toString();
        party = two.getText().toString();
        boolean success = helper.updatePayments(name,amount,party,accumulated);
        if (success){
            showToasts("Successful");
            showData(getPayment(binding.namePayment.getText().toString()));
        } else {
            showToasts("Failed");
        }
    }

    private Cash getPayment(String paymentName) {
        Cursor cursor = helper.getPayment(paymentName);
        Cash cash = null;
        if (cursor.getCount() == 0){
            Toast.makeText(this, "There is no such pump!", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()){
                String name = cursor.getString(1);
                String lastAmount = cursor.getString(2);
                String party = cursor.getString(3);
                String accumulated = cursor.getString(4);
                cash = new Cash(name,lastAmount,party,accumulated);
            }
        }
        return cash;
    }
    private void showToasts(String message){
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}