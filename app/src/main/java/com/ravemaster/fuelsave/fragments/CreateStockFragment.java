package com.ravemaster.fuelsave.fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputLayout;
import com.ravemaster.fuelsave.R;
import com.ravemaster.fuelsave.interfaces.CreateStock;
import com.ravemaster.fuelsave.interfaces.CreateTank;


public class CreateStockFragment extends DialogFragment {
    public static String TAG = "CreateStockFragment";
    String name="";
    String initial="";
    EditText editText,editText2;
    TextInputLayout layout,layout2;
    CreateStock createStock;

    public CreateStockFragment(CreateStock createStock) {
        this.createStock = createStock;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(requireContext());
        View view = requireActivity().getLayoutInflater().inflate(R.layout.create_stock_layout,null);
        layout = view.findViewById(R.id.enterStockName);
        layout2 = view.findViewById(R.id.enterInitialStock);
        editText = layout.getEditText();
        editText2 = layout2.getEditText();
        builder.setView(view);
        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                name = editText.getText().toString();
                initial = editText2.getText().toString();
                createStock.createStock(name,initial);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        return builder.create();
    }



}
