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
import com.ravemaster.fuelsave.adapters.FuelAdapter;
import com.ravemaster.fuelsave.database.DBHelper;
import com.ravemaster.fuelsave.interfaces.CreateTank;
import com.ravemaster.fuelsave.interfaces.ViewFuel;
import com.ravemaster.fuelsave.models.Fuel;

import java.util.ArrayList;
import java.util.List;

public class StockFragment extends Fragment {

    RecyclerView recyclerView;
    FuelAdapter fuelAdapter;
    List<Fuel> fuelList;
    FloatingActionButton createPump;
    DBHelper helper;

    public StockFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fuelAdapter = new FuelAdapter(viewFuel,requireContext());
        fuelList = new ArrayList<>();
        helper = new DBHelper(requireContext());
        fuelList = getFuelStock();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_stock, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        showFuelStock(fuelList);
        createPump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CreateTankFragment(create).show(
                        getChildFragmentManager(),CreatePumpFragment.TAG
                );
            }
        });
    }

    private final CreateTank create = new CreateTank() {
        @Override
        public void createTank(String type, String name, String initial) {
            int image=0;
            switch (type){
                case "Petrol":
                    image = R.drawable.ic_tanker_one;
                    break;
                case "Diesel":
                    image = R.drawable.ic_tanker_two;
                    break;
                case "Premium":
                    image = R.drawable.ic_tanker_three;
                    break;
                case "Kerosene":
                    image = R.drawable.ic_tanker_four;
                    break;
            }
            helper.insertFuelStock(name,initial,"0","0",image);
            fuelList = getFuelStock();
            fuelAdapter.setFuelList(fuelList);
        }
    };

    private final ViewFuel viewFuel = new ViewFuel() {
        @Override
        public void viewFuel(Fuel fuel) {
            Intent intent = new Intent(requireContext(), FuelStockActivity.class);
            intent.putExtra("fuel",fuel);
            requireContext().startActivity(intent);
        }
    };

    private List<Fuel> getFuelStock(){
        Cursor cursor = helper.getFuelStocks();
        List<Fuel> dummy = new ArrayList<>();
        if (cursor.getCount() == 0){
            Toast.makeText(requireContext(), "No fuels available", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()){
                String name = cursor.getString(1);
                String initial = cursor.getString(2);
                String current = cursor.getString(3);
                String added = cursor.getString(4);
                int image = cursor.getInt(5);
                dummy.add(new Fuel(name,initial,current,added,image));
            }
        }
        return dummy;
    }

    @Override
    public void onResume() {
        super.onResume();
        fuelList = getFuelStock();
        showFuelStock(fuelList);
    }

    private void showFuelStock(List<Fuel> fuelList) {
        fuelAdapter.setFuelList(fuelList);
        recyclerView.setAdapter(fuelAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setHasFixedSize(true);
    }

    private void initViews(View view) {
        recyclerView = view.findViewById(R.id.fuelRecycler);
        createPump = view.findViewById(R.id.btnCreateFuelStock);
    }

 }