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
import com.ravemaster.fuelsave.activities.MakeEntryActivity;
import com.ravemaster.fuelsave.database.DBHelper;
import com.ravemaster.fuelsave.interfaces.MakeEntry;
import com.ravemaster.fuelsave.models.Pump;
import com.ravemaster.fuelsave.R;
import com.ravemaster.fuelsave.adapters.PumpAdapter;
import com.ravemaster.fuelsave.interfaces.CreatePump;

import java.util.ArrayList;
import java.util.List;


public class PumpsFragment extends Fragment {

    RecyclerView recyclerView;
    PumpAdapter pumpAdapter;
    List<Pump> pumpList;
    FloatingActionButton createPump;
    DBHelper helper;

    public PumpsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pumpAdapter = new PumpAdapter(requireContext(), makeEntry);
        pumpList = new ArrayList<>();
        helper = new DBHelper(requireContext());
        pumpList = getPumps();
        if (pumpList.isEmpty()){
            helper.insertPumps("Petrol","0","0","0","0",R.drawable.ic_pump_one);
            helper.insertPumps("Diesel","0","0","0","0",R.drawable.ic_pump_two);
            helper.insertPumps("Premium","0","0","0","0",R.drawable.ic_pump_three);
            pumpList = getPumps();
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pumps, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        showPumps(pumpList);
        createPump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CreatePumpFragment(create).show(
                        getChildFragmentManager(),CreatePumpFragment.TAG
                );
            }
        });
    }

    private final CreatePump create = new CreatePump() {
        @Override
        public void createPump(String type, String name) {
            int image=0;
            switch (type){
                case "Petrol":
                    image = R.drawable.ic_pump_one;
                    break;
                case "Diesel":
                    image = R.drawable.ic_pump_two;
                    break;
                case "Premium":
                    image = R.drawable.ic_pump_three;
                    break;
                case "Kerosene":
                    image = R.drawable.ic_pump_four;
                    break;
            }
            helper.insertPumps(name,"0","0","0","0",image);
            pumpList = getPumps();
            pumpAdapter.setPumpList(pumpList);
        }
    };

    private final MakeEntry makeEntry = new MakeEntry() {
        @Override
        public void makeEntry(Pump pump) {
            Intent intent = new Intent(requireContext(), MakeEntryActivity.class);
            intent.putExtra("pump",pump);
            requireContext().startActivity(intent);
        }
    };

    private List<Pump> getPumps(){
        Cursor cursor = helper.getPumps();
        List<Pump> dummy = new ArrayList<>();
        if (cursor.getCount() == 0){
            Toast.makeText(requireContext(), "No pumps available", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()){
                String name = cursor.getString(1);
                String prevSale = cursor.getString(2);
                String prevLitre = cursor.getString(3);
                String totalSale = cursor.getString(4);
                String totalLitre = cursor.getString(5);
                int image = cursor.getInt(6);
                dummy.add(new Pump(name,prevLitre,prevSale,totalLitre,totalSale,image));
            }
        }
        return dummy;
    }

    @Override
    public void onResume() {
        super.onResume();
        pumpList = getPumps();
        showPumps(pumpList);
    }

    private void showPumps(List<Pump> pumpList) {
        pumpAdapter.setPumpList(pumpList);
        recyclerView.setAdapter(pumpAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setHasFixedSize(true);
    }

    private void initViews(View view) {
        recyclerView = view.findViewById(R.id.pumpsRecycler);
        createPump = view.findViewById(R.id.btnCreatePump);
    }
}