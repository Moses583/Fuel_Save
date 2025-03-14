package com.ravemaster.fuelsave.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ravemaster.fuelsave.activities.MakeEntryActivity;
import com.ravemaster.fuelsave.interfaces.MakeEntry;
import com.ravemaster.fuelsave.models.Pump;
import com.ravemaster.fuelsave.R;

import java.util.ArrayList;
import java.util.List;

public class PumpAdapter extends RecyclerView.Adapter<PumpAdapter.PumpViewHolder> {
    Context context;
    List<Pump> pumpList = new ArrayList<>();
    FragmentManager manager;
    MakeEntry makeEntry;

    public PumpAdapter(Context context, MakeEntry makeEntry) {
        this.context = context;
        this.makeEntry = makeEntry;
    }

    public void setPumpList(List<Pump> pumpList) {
        this.pumpList = pumpList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PumpViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PumpViewHolder(LayoutInflater.from(context).inflate(R.layout.pump_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PumpViewHolder holder, int position) {
        int image = pumpList.get(position).getImage();
        String name = pumpList.get(position).getName();
        String totalSales = pumpList.get(position).getTotalSale();
        String totalLitres = pumpList.get(position).getTotalLitre();

        holder.pump.setBackgroundResource(image);
        holder.txtName.setText(name);
        holder.txtName.setSelected(true);
        holder.txtTotalSales.setText(totalSales);
        holder.txtTotalLitres.setText(totalLitres);
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeEntry.makeEntry(pumpList.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return pumpList.size();
    }

    public static class PumpViewHolder extends RecyclerView.ViewHolder {
        ImageView pump;
        TextView txtTotalSales, txtTotalLitres, txtName;
        Button button;
        public PumpViewHolder(@NonNull View itemView) {
            super(itemView);
            pump = itemView.findViewById(R.id.imgPump);
            txtName = itemView.findViewById(R.id.txtPumpName);
            txtTotalSales = itemView.findViewById(R.id.txtTotalSales);
            txtTotalLitres = itemView.findViewById(R.id.txtTotalLitres);
            button = itemView.findViewById(R.id.btnLog);
        }
    }
}
