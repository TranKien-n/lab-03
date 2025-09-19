package com.example.listycitylab3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CityArrayAdapter extends ArrayAdapter<City> {

    private final int rowLayoutResId;              // to know which layout used

    public CityArrayAdapter(Context context, int rowLayoutResId, ArrayList<City> data) {
        super(context, rowLayoutResId, data);
        this.rowLayoutResId = rowLayoutResId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // inflate a new row if it doesn't exist
        View row = convertView;
        if (row == null) {
            row = LayoutInflater.from(getContext()).inflate(rowLayoutResId, parent, false);
        }

        TextView cityText = row.findViewById(R.id.city_text);
        TextView provinceText = row.findViewById(R.id.province_text);

        City city = getItem(position);
        if (city != null) {
            cityText.setText(city.getName());
            provinceText.setText(city.getProvince());
        } else {
            cityText.setText("");
            provinceText.setText("");
        }

        return row;
    }
}
