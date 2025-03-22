package com.ravemaster.fuelsave.fragments;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ravemaster.fuelsave.R;
import com.ravemaster.fuelsave.activities.FuelStockActivity;
import com.ravemaster.fuelsave.activities.InventoryActivity;
import com.ravemaster.fuelsave.adapters.StockAdapter;
import com.ravemaster.fuelsave.database.DBHelper;
import com.ravemaster.fuelsave.interfaces.CreateStock;
import com.ravemaster.fuelsave.interfaces.CreateTank;
import com.ravemaster.fuelsave.interfaces.ViewFuel;
import com.ravemaster.fuelsave.interfaces.ViewStock;
import com.ravemaster.fuelsave.models.Fuel;
import com.ravemaster.fuelsave.models.Stock;

import java.util.ArrayList;
import java.util.List;

public class InventoryFragment extends Fragment {

    RecyclerView recyclerView;
    StockAdapter stockAdapter;
    List<Stock> stockList;
    FloatingActionButton createPump;
    DBHelper helper;

    public InventoryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        stockAdapter = new StockAdapter(viewStock,requireContext());
        stockList = new ArrayList<>();
        helper = new DBHelper(requireContext());
        stockList = getStock();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_inventory, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        showStock(stockList);
        createPump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CreateStockFragment(create).show(
                        getChildFragmentManager(),CreateStockFragment.TAG
                );
            }
        });
    }
    private final CreateStock create = new CreateStock() {
        @Override
        public void createStock(String name,String initial) {
            helper.insertStock(name,initial,"0","0");
            stockList = getStock();
            stockAdapter.setStockList(stockList);
        }
    };

    private final ViewStock viewStock = new ViewStock() {
        @Override
        public void viewStock(Stock stock) {
            Intent intent = new Intent(requireContext(), InventoryActivity.class);
            intent.putExtra("Stock",stock);
            requireContext().startActivity(intent);
        }
    };

    private List<Stock> getStock(){
        Cursor cursor = helper.getStocks();
        List<Stock> dummy = new ArrayList<>();
        if (cursor.getCount() == 0){
        } else {
            while (cursor.moveToNext()){
                String name = cursor.getString(1);
                String initial = cursor.getString(2);
                String current = cursor.getString(3);
                String added = cursor.getString(4);
                dummy.add(new Stock(name,initial,current,added));
            }
        }
        return dummy;
    }

    @Override
    public void onResume() {
        super.onResume();
        stockList = getStock();
        showStock(stockList);
    }

    private void showStock(List<Stock> stockList) {
        stockAdapter.setStockList(stockList);
        recyclerView.setAdapter(stockAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setHasFixedSize(true);
    }

    private void initViews(View view) {
        recyclerView = view.findViewById(R.id.inventoryRecycler);
        createPump = view.findViewById(R.id.btnCreateInventoryStock);
    }
}