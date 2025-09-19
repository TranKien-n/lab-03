package com.example.listycitylab3;

import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements CityEditDialogFragment.Listener {

    private ArrayList<City> dataList;
    private ListView cityList;
    private CityArrayAdapter cityAdapter;

    public MainActivity() {
        super();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] cities = { "Edmonton", "Vancouver", "Toronto" };
        String[] provinces = { "AB", "BC", "ON" };

        dataList = new ArrayList<>();
        for (int i = 0; i < Math.min(cities.length, provinces.length); i++) {
            dataList.add(new City(cities[i], provinces[i]));
        }

        cityList = findViewById(R.id.city_list);
        cityAdapter = new CityArrayAdapter(this, R.layout.content, dataList);
        cityList.setAdapter(cityAdapter);

        // hook up the click listener for each item in the list of cities n provinces
        cityList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override public void onItemClick(AdapterView<?> parent, android.view.View view, int position, long id) {
                showEditDialog(position);
            }
        });
    }

    // automated method to show the edit dialog from android studio
    private void showEditDialog(int position) {
        CityEditDialogFragment dialog = CityEditDialogFragment.newInstance(dataList.get(position), position);
        dialog.show(getSupportFragmentManager(), "edit_city");
    }

    // editing a city in the list
    @Override
    public void onCityEdited(int position, City updated) {
        if (position >= 0 && position < dataList.size() && updated != null) {
            dataList.set(position, updated);            // replace old city with new one
            cityAdapter.notifyDataSetChanged();         // refresh the list
        }
    }
}
