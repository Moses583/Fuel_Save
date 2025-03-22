package com.ravemaster.fuelsave.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ravemaster.fuelsave.databinding.FuelListItemBinding;
import com.ravemaster.fuelsave.interfaces.ViewFuel;
import com.ravemaster.fuelsave.models.Fuel;

import java.util.ArrayList;
import java.util.List;

public class FuelAdapter extends RecyclerView.Adapter<FuelAdapter.FuelViewHolder> {

    private ViewFuel viewFuel;
    private List<Fuel> fuelList = new ArrayList<>();
    private Context context;

    public FuelAdapter(ViewFuel viewFuel, Context context) {
        this.viewFuel = viewFuel;
        this.context = context;
    }

    public void setFuelList(List<Fuel> cash) {
        this.fuelList = cash;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FuelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        FuelListItemBinding binding =FuelListItemBinding.inflate(
                LayoutInflater.from(context),
                parent,false);
        return new FuelViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull FuelViewHolder holder, int position) {
        holder.setFuelData(fuelList.get(holder.getAdapterPosition()));
        holder.binding.fuelStockCard.setOnClickListener(v->{
            viewFuel.viewFuel(fuelList.get(holder.getAdapterPosition()));
        });
    }

    @Override
    public int getItemCount() {
        return fuelList.size();
    }

    class FuelViewHolder extends RecyclerView.ViewHolder {
        FuelListItemBinding binding;
        FuelViewHolder(FuelListItemBinding fuelListItemBinding) {
            super(fuelListItemBinding.getRoot());
            binding = fuelListItemBinding;
        }

        void setFuelData(Fuel fuel){
            binding.tankName.setText(fuel.fuelName);
            binding.tankCurrent.setText(fuel.currentStock);
            binding.tankInitial.setText(fuel.initialStock);
            binding.tankImage.setBackgroundResource(fuel.image);
        }
    }

}
