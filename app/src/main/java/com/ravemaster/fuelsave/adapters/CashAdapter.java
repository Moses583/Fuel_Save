package com.ravemaster.fuelsave.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ravemaster.fuelsave.databinding.CashListItemBinding;
import com.ravemaster.fuelsave.interfaces.ViewCash;
import com.ravemaster.fuelsave.models.Cash;

import java.util.ArrayList;
import java.util.List;

public class CashAdapter extends RecyclerView.Adapter<CashAdapter.CashViewHolder> {

    private ViewCash viewCash;
    private List<Cash> cashList = new ArrayList<>();
    private Context context;

    public CashAdapter(ViewCash viewCash, Context context) {
        this.viewCash = viewCash;
        this.context = context;
    }

    public void setCash(List<Cash> cash) {
        this.cashList = cash;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CashViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CashListItemBinding binding =CashListItemBinding.inflate(
                LayoutInflater.from(context),
                parent,false);
        return new CashViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CashViewHolder holder, int position) {
        holder.setCashData(cashList.get(holder.getAdapterPosition()));
        holder.binding.cashEntityLayout.setOnClickListener(v->{
            viewCash.viewCash(cashList.get(holder.getAdapterPosition()));
        });
    }

    @Override
    public int getItemCount() {
        return cashList.size();
    }

    class CashViewHolder extends RecyclerView.ViewHolder {
        CashListItemBinding binding;
        CashViewHolder(CashListItemBinding cashListItemBinding) {
            super(cashListItemBinding.getRoot());
            binding = cashListItemBinding;
        }

        void setCashData(Cash cash){
            binding.cashEntityName.setText(cash.name);
            binding.cashAmount.setText("Last payment: "+cash.amount);
            binding.partyInvolved.setText("Made by: "+cash.party);
        }
    }

}
