package com.queuedodger.kevin.queuedodger.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.GridLayout;

import com.queuedodger.kevin.queuedodger.R;

public class ChampionPicker extends DialogFragment {
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        builder.setTitle(R.string.selectionDialog_message);




        return builder.create();
    }

}

