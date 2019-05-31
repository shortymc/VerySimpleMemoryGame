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
import android.widget.Toast;


import com.andraganoid.memory_java.databinding.MemoryBinding;


public class Memory extends AppCompatActivity implements ClickHandler {

    private MemoryViewModel memoryViewModel;
    private MemoryAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics dm = new DisplayMetrics();
        display.getMetrics(dm);

        memoryViewModel = ViewModelProviders.of(this).get(MemoryViewModel.class);
        memoryViewModel.itemHeight.set((int) (dm.heightPixels * .12));
        MemoryBinding binding = DataBindingUtil.setContentView(this, R.layout.memory);

        binding.setResult(memoryViewModel.memoryResult);
        binding.setViewModel(memoryViewModel);

        RecyclerView recView = findViewById(R.id.memoryRecView);
        recView.setLayoutManager(new GridLayoutManager(this, 4));
        mAdapter = new MemoryAdapter(memoryViewModel, this);
        recView.setAdapter(mAdapter);

        memoryViewModel.getItemIndex().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer itemId) {
                mAdapter.setItem(itemId);
            }
        });


        memoryViewModel.getValueSet().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isSet) {
                if (isSet) {
                    mAdapter.setFields(memoryViewModel.getMemoryList());
                }
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        memoryViewModel.stopStopwatch();
    }


    @Override
    public void onFieldClicked(int id) {
        memoryViewModel.onFieldClicked(id);
    }
}
