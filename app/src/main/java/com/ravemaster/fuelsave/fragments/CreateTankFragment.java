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
import com.ravemaster.fuelsave.interfaces.CreatePump;
import com.ravemaster.fuelsave.interfaces.CreateTank;


public class CreateTankFragment extends DialogFragment {
    public static String TAG = "CreateTankFragment";
    String name="";
    String type="";
    String initial="";
    ChipGroup chips;
    EditText editText,editText2;
    TextInputLayout layout,layout2;
    CreateTank createTank;

    public CreateTankFragment(CreateTank createTank) {
        this.createTank = createTank;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(requireContext());
        View view = requireActivity().getLayoutInflater().inflate(R.layout.create_tank_layout,null);
        chips = view.findViewById(R.id.selectTank);
        chips.setSingleSelection(true);
        layout = view.findViewById(R.id.enterTankName);
        layout2 = view.findViewById(R.id.enterTankInitialStock);
        editText = layout.getEditText();
        editText2 = layout2.getEditText();
        builder.setView(view);
        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                name = editText.getText().toString();
                initial = editText2.getText().toString();
                int checkedId = chips.getCheckedChipId();
                if (checkedId != View.NO_ID){
                    Chip chip = chips.findViewById(checkedId);
                    type = chip.getText().toString();
                } else {
                    type = "no chip selected";
                }
                createTank.createTank(type,name,initial);
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
