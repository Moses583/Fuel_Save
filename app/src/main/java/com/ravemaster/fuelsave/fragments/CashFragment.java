package com.ravemaster.fuelsave.fragments;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ravemaster.fuelsave.activities.RecordPaymentActivity;
import com.ravemaster.fuelsave.adapters.CashAdapter;
import com.ravemaster.fuelsave.database.DBHelper;
import com.ravemaster.fuelsave.databinding.FragmentCashBinding;
import com.ravemaster.fuelsave.interfaces.CreatePayment;
import com.ravemaster.fuelsave.interfaces.ViewCash;
import com.ravemaster.fuelsave.models.Cash;
import com.ravemaster.fuelsave.models.Pump;

import java.util.ArrayList;
import java.util.List;

public class CashFragment extends Fragment {

    public CashFragment() {
        // Required empty public constructor
    }

    private FragmentCashBinding binding;
    List<Cash> cashList;
    CashAdapter adapter;
    DBHelper helper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new CashAdapter(viewCash,requireContext());
        cashList = new ArrayList<>();
        helper = new DBHelper(requireContext());
        cashList = getPayments();
        if (cashList.isEmpty()){
            helper.insertCash("Bank deposits","0","0");
            helper.insertCash("Credits","0","0");
            helper.insertCash("Till payments","0","0");
            cashList = getPayments();
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCashBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setData(cashList);
        binding.btnCreateCash.setOnClickListener(v->{
            new CreatePaymentFragment(createPayment).show(
                    getChildFragmentManager(),CreatePaymentFragment.TAG
            );
        });
    }

    private List<Cash> getPayments(){
        Cursor cursor = helper.getPayments();
        List<Cash> dummy = new ArrayList<>();
        if (cursor.getCount() == 0){
        } else {
            while (cursor.moveToNext()){
                String name = cursor.getString(1);
                String amount = cursor.getString(2);
                String accumulated = cursor.getString(3);
                dummy.add(new Cash(name, amount, accumulated));
            }
        }
        return dummy;
    }

    private void setData(List<Cash> cashList) {
        adapter.setCash(cashList);
        binding.cashRecycler.setAdapter(adapter);
        binding.cashRecycler.setLayoutManager(new LinearLayoutManager(requireContext()));
    }

    private final ViewCash viewCash = cash -> {
        Intent intent = new Intent(requireContext(), RecordPaymentActivity.class);
        intent.putExtra("cash",cash);
        startActivity(intent);
    };

    private final CreatePayment createPayment = new CreatePayment() {
        @Override
        public void createPayment(String name) {
            helper.insertCash(name,"0","0");
            cashList = getPayments();
            adapter.setCash(cashList);
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        cashList = getPayments();
        setData(cashList);
    }

    private void showToasts(String message){
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }
}