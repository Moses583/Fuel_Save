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


public class CreatePumpFragment extends DialogFragment {
    public static String TAG = "CreatePumpFragment";
    String name="";
    String type="";
    ChipGroup chips;
    EditText editText;
    TextInputLayout layout;
    CreatePump createPump;

    public CreatePumpFragment(CreatePump createPump) {
        this.createPump = createPump;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(requireContext());
        View view = requireActivity().getLayoutInflater().inflate(R.layout.create_pump_layout,null);
        chips = view.findViewById(R.id.selectPump);
        chips.setSingleSelection(true);
        layout = view.findViewById(R.id.enterPumpName);
        editText = layout.getEditText();
        builder.setView(view);
        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                name = editText.getText().toString();
                int checkedId = chips.getCheckedChipId();
                if (checkedId != View.NO_ID){
                    Chip chip = chips.findViewById(checkedId);
                    type = chip.getText().toString();
                } else {
                    type = "no chip selected";
                }
                createPump.createPump(type,name);
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
