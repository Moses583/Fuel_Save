package com.ravemaster.fuelsave.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ravemaster.fuelsave.databinding.StockListItemBinding;
import com.ravemaster.fuelsave.interfaces.ViewStock;
import com.ravemaster.fuelsave.models.Stock;

import java.util.ArrayList;
import java.util.List;

public class StockAdapter extends RecyclerView.Adapter<StockAdapter.StockViewHolder> {

    private ViewStock viewStock;
    private List<Stock> stockList = new ArrayList<>();
    private Context context;

    public StockAdapter(ViewStock viewStock, Context context) {
        this.viewStock = viewStock;
        this.context = context;
    }

    public void setStockList(List<Stock> stockList) {
        this.stockList = stockList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public StockViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        StockListItemBinding binding = StockListItemBinding.inflate(
                LayoutInflater.from(context),
                parent,false);
        return new StockViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull StockViewHolder holder, int position) {
        holder.setStockData(stockList.get(holder.getAdapterPosition()));
        holder.binding.stockCard.setOnClickListener(v->{
            viewStock.viewStock(stockList.get(holder.getAdapterPosition()));
        });
    }

    @Override
    public int getItemCount() {
        return stockList.size();
    }

    class StockViewHolder extends RecyclerView.ViewHolder {
        StockListItemBinding binding;
        StockViewHolder(StockListItemBinding stockListItemBinding) {
            super(stockListItemBinding.getRoot());
            binding = stockListItemBinding;
        }

        void setStockData(Stock stock){
            binding.stockName.setText(stock.stockName);
            binding.stockCurrent.setText(stock.currentStock);
            binding.stockInitial.setText(stock.initialStock);
        }
    }

}
