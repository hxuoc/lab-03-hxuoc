package com.example.listycitylab3;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class EditCityFragment extends DialogFragment {
    private EditText editCityName;
    private EditText editProvinceName;
    private OnFragmentInteractionListener listener;
    private City selectedCity;

    public interface OnFragmentInteractionListener {
        void onEditCityConfirmPressed(City city, String newName, String newProvince);
    }

    public static EditCityFragment newInstance(City city) {
        EditCityFragment fragment = new EditCityFragment();
        Bundle args = new Bundle();
        args.putSerializable("city", city);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            listener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context + " must implement OnFragmentInteractionListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_add_city, null);
        editCityName = view.findViewById(R.id.editText_city_name);
        editProvinceName = view.findViewById(R.id.editText_province_name);

        Bundle args = getArguments();
        if (args != null) {
            selectedCity = (City) args.getSerializable("city");
            if (selectedCity != null) {
                editCityName.setText(selectedCity.getName());
                editProvinceName.setText(selectedCity.getProvince());
            }
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        return builder
                .setView(view)
                .setTitle("Edit City")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("OK", (dialog, which) -> {
                    String cityName = editCityName.getText().toString();
                    String provinceName = editProvinceName.getText().toString();
                    if (!cityName.isEmpty() && !provinceName.isEmpty() && selectedCity != null) {
                        listener.onEditCityConfirmPressed(selectedCity, cityName, provinceName);
                    }
                }).create();
    }
}
