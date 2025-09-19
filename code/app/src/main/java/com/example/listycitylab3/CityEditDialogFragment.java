package com.example.listycitylab3;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

// pop up dialog to edit a city
public class CityEditDialogFragment extends DialogFragment {

    // an interface to communicate with the activity
    public interface Listener {
        void onCityEdited(int position, City updated);
    }

    // arguments to pass to the dialog
    private static final String ARG_CITY = "arg_city";
    private static final String ARG_POSITION = "arg_position";

    // factory method to create a new instance of the dialog
    public static CityEditDialogFragment newInstance(City city, int position) {
        CityEditDialogFragment f = new CityEditDialogFragment();
        Bundle b = new Bundle();
        b.putSerializable(ARG_CITY, city);
        b.putInt(ARG_POSITION, position);
        f.setArguments(b);
        return f;
    }

    private EditText nameInput;
    private EditText provinceInput;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View v = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_edit_city, null, false);

        nameInput = v.findViewById(R.id.edit_city_name);
        provinceInput = v.findViewById(R.id.edit_city_province);

        Bundle args = getArguments();
        City city = args != null ? (City) args.getSerializable(ARG_CITY) : null;
        final int pos = args != null ? args.getInt(ARG_POSITION, -1) : -1;

        if (city != null) {
            nameInput.setText(city.getName());
            provinceInput.setText(city.getProvince());
        }

        return new AlertDialog.Builder(requireContext())
                .setTitle("Edit City")
                .setView(v)
                .setPositiveButton("Save", (d, which) -> {
                    String newName = nameInput.getText().toString().trim();
                    String newProv = provinceInput.getText().toString().trim();
                    if (newName.isEmpty() || newProv.isEmpty()) return;

                    // Create a new City (simplest, avoids mutability assumptions)
                    City updated = new City(newName, newProv);

                    if (getActivity() instanceof Listener) {
                        ((Listener) getActivity()).onCityEdited(pos, updated);
                    }
                })
                .setNegativeButton("Cancel", null)
                .create();
    }
}
