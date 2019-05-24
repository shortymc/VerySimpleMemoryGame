package com.andraganoid.memory_java;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;


import com.andraganoid.memory_java.databinding.ActivityMainBinding;

import java.util.List;


public class MainActivity extends AppCompatActivity {

    MemoryViewModel memoryViewModel;
    GridView memoGridView;
    GridMemoryAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics dm = new DisplayMetrics();
        display.getMetrics(dm);

        memoryViewModel = ViewModelProviders.of(this).get(MemoryViewModel.class);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);//.vmodel = myWeatherViewModel

        binding.setResult(memoryViewModel.memoryResult);
        binding.setViewModel(memoryViewModel);

        memoGridView = findViewById(R.id.fields_grid_view);
        mAdapter = new GridMemoryAdapter(this, (int) (dm.heightPixels * .12));
        memoGridView.setAdapter(mAdapter);

        memoryViewModel.getMemoryList().observe(this, new Observer <List <Field>>() {
            @Override
            public void onChanged(List <Field> fields) {
                mAdapter.setFields(fields);
            }
        });


        memoGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView <?> parent, View view, int position, long id) {
                memoryViewModel.fieldClicked(position);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        memoryViewModel.stopStopwatch();
    }

}
