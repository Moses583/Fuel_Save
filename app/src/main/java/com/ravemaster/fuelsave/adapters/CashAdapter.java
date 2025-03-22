package com.ravemaster.fuelsave.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ravemaster.fuelsave.database.DBHelper;
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
        holder.binding.cashEntityLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                delete(holder.getAdapterPosition(), cashList.get(holder.getAdapterPosition()).name);
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return cashList.size();
    }

    public void delete(int itemId, String name){
        DBHelper helper = new DBHelper(context);
        boolean checkDelete = helper.deleteCash(name);
        if (checkDelete){
            Toast.makeText(context, "Payment deleted successfully", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(context, "unable to delete payment", Toast.LENGTH_SHORT).show();
        }
        cashList.remove(itemId);
        notifyItemRemoved(itemId);
    }

    class CashViewHolder extends RecyclerView.ViewHolder {
        CashListItemBinding binding;
        CashViewHolder(CashListItemBinding cashListItemBinding) {
            super(cashListItemBinding.getRoot());
            binding = cashListItemBinding;
        }

        void setCashData(Cash cash){
            binding.cashEntityName.setText(cash.name);
            binding.partyInvolved.setText(cash.accumulated);
            binding.cashAmount.setText(cash.amount);
        }
    }

}
