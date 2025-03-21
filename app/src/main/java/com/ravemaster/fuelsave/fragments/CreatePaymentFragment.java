package com.ravemaster.fuelsave.fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;


import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputLayout;
import com.ravemaster.fuelsave.R;
import com.ravemaster.fuelsave.interfaces.CreatePayment;

public class CreatePaymentFragment extends DialogFragment {
    public static String TAG = "CreatePaymentFragment";
    String name="";
    EditText editText;
    TextInputLayout layout;
    CreatePayment createPayment;

    public CreatePaymentFragment(CreatePayment createPayment) {
        this.createPayment = createPayment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(requireContext());
        View view = requireActivity().getLayoutInflater().inflate(R.layout.create_payment_layout,null);
        layout = view.findViewById(R.id.enterPaymentName);
        editText = layout.getEditText();
        builder.setView(view);
        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                name = editText.getText().toString();
                createPayment.createPayment(name);
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
