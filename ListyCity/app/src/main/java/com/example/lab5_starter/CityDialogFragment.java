package com.example.lab5_starter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Objects;

public class CityDialogFragment extends DialogFragment {
    interface CityDialogListener {
        void updateCity(City oldCity, City newCity);
        void addCity(City city);
        void deleteCity(City city);
    }
    private CityDialogListener listener;

    public static CityDialogFragment newInstance(City city){
        Bundle args = new Bundle();
        args.putSerializable("City", city);

        CityDialogFragment fragment = new CityDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof CityDialogListener){
            listener = (CityDialogListener) context;
        }
        else {
            throw new RuntimeException("Implement listener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        EditText editMovieName;
        EditText editMovieYear;

        TextView delProvinceName;
        TextView delCityName;

        View view;


        String tag = getTag();
        Bundle bundle = getArguments();
        City city = null;
        if (bundle !=null) {
            city = (City) bundle.getSerializable("City");
        }

        if (Objects.equals(tag, "City Details") && bundle != null){
            view = getLayoutInflater().inflate(R.layout.fragment_city_details, null);
            editMovieName = view.findViewById(R.id.edit_city_name);
            editMovieYear = view.findViewById(R.id.edit_province);


//            assert city != null;
            editMovieName.setText(city.getName());
            editMovieYear.setText(city.getProvince());
        } else if
        (Objects.equals(tag, "Delete City")) {
            editMovieName = null;
            editMovieYear = null;
            view = getLayoutInflater().inflate(R.layout.fragment_city_confirm_delete, null);
            delProvinceName = view.findViewById(R.id.province_name_del);
            delCityName = view.findViewById(R.id.city_name_del);

            delProvinceName.setText(city.getName());
            delCityName.setText(city.getProvince());
        }
        else {
            view = getLayoutInflater().inflate(R.layout.fragment_city_details, null);
            editMovieName = view.findViewById(R.id.edit_city_name);
            editMovieYear = view.findViewById(R.id.edit_province);

            city = null;}

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        City finalCity = city;
        return builder
                .setView(view)
                .setTitle("City Details")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Continue", (dialog, which) -> {
                    if (Objects.equals(tag, "City Details")) {
                        String title = editMovieName.getText().toString();
                        String year = editMovieYear.getText().toString();

                        listener.updateCity(finalCity, new City( title, year));
                    } else if (Objects.equals(tag, "Delete City")) {
                        listener.deleteCity(finalCity);
                    } else {
                        String title = editMovieName.getText().toString();
                        String year = editMovieYear.getText().toString();

                        listener.addCity(new City(title, year));
                    }
                })
                .create();
    }
}
