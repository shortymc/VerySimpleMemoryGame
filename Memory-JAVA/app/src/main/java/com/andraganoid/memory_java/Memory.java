package com.andraganoid.memory_java;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;


import com.andraganoid.memory_java.databinding.MemoryBinding;

import java.util.List;


public class Memory extends AppCompatActivity {

    private MemoryViewModel memoryViewModel;
    MemoryAdapter mAdapter;
    // GridView memoGridView;
    // GridMemoryAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics dm = new DisplayMetrics();
        display.getMetrics(dm);

        memoryViewModel = ViewModelProviders.of(this).get(MemoryViewModel.class);
        memoryViewModel.itemHeight = (float) (dm.heightPixels * .12);
        MemoryBinding binding = DataBindingUtil.setContentView(this, R.layout.memory);

        binding.setResult(memoryViewModel.memoryResult);
        binding.setViewModel(memoryViewModel);

//        memoGridView = findViewById(R.id.fields_grid_view);
//        mAdapter = new GridMemoryAdapter(this, (int) (dm.heightPixels * .12));
//        memoGridView.setAdapter(mAdapter);

        RecyclerView recView = findViewById(R.id.memoryRecView);
        recView.setLayoutManager(new GridLayoutManager(this, 4));
        mAdapter = new MemoryAdapter();
        recView.setAdapter(mAdapter);

        memoryViewModel.getMemoryList().observe(this, new Observer <List <Field>>() {
            @Override
            public void onChanged(List <Field> fields) {
                mAdapter.setFields(fields);
            }
        });


//        memoGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView <?> parent, View view, int position, long id) {
//                memoryViewModel.onFieldClicked(position);
//            }
//        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        memoryViewModel.stopStopwatch();
    }

}
