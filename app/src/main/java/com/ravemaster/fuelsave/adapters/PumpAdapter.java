package com.ravemaster.fuelsave.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.ravemaster.fuelsave.database.DBHelper;
import com.ravemaster.fuelsave.interfaces.ViewPump;
import com.ravemaster.fuelsave.models.Pump;
import com.ravemaster.fuelsave.R;

import java.util.ArrayList;
import java.util.List;

public class PumpAdapter extends RecyclerView.Adapter<PumpAdapter.PumpViewHolder> {
    Context context;
    List<Pump> pumpList = new ArrayList<>();
    FragmentManager manager;
    ViewPump viewPump;

    public PumpAdapter(Context context, ViewPump viewPump) {
        this.context = context;
        this.viewPump = viewPump;
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
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPump.viewPump(pumpList.get(holder.getAdapterPosition()));
            }
        });
        holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                delete(holder.getAdapterPosition(),pumpList.get(holder.getAdapterPosition()).getName());
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return pumpList.size();
    }

    public void delete(int itemId, String name){
        DBHelper helper = new DBHelper(context);
        boolean checkDelete = helper.deletePump(name);
        if (checkDelete){
            Toast.makeText(context, "Pump deleted successfully", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(context, "unable to delete pump", Toast.LENGTH_SHORT).show();
        }
        pumpList.remove(itemId);
        notifyItemRemoved(itemId);
    }

    public static class PumpViewHolder extends RecyclerView.ViewHolder {
        ImageView pump;
        TextView txtTotalSales, txtTotalLitres, txtName;
        MaterialCardView cardView;
        public PumpViewHolder(@NonNull View itemView) {
            super(itemView);
            pump = itemView.findViewById(R.id.imgPump);
            txtName = itemView.findViewById(R.id.txtPumpName);
            txtTotalSales = itemView.findViewById(R.id.txtTotalSales);
            txtTotalLitres = itemView.findViewById(R.id.txtTotalLitres);
            cardView = itemView.findViewById(R.id.pumpCardView);
        }
    }
}
