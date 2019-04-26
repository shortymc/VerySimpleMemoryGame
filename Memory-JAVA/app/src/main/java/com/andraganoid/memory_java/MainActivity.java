package com.andraganoid.memory_java;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import java.util.List;

import static com.andraganoid.memory_java.MemoryGame.ALL_SOLVED;

public class MainActivity extends AppCompatActivity {

    MemoryViewModel memoryViewModel;
    GridView memoGridView;
    GridMemoryAdapter mAdapter;
    TextView bestMoves, bestTime, currentMoves, currentTime;
    static Button newGameBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bestMoves = findViewById(R.id.best_moves_value);
        bestTime = findViewById(R.id.best_time_value);
        currentMoves = findViewById(R.id.current_moves_value);
        currentTime = findViewById(R.id.current_time_value);
        newGameBtn = findViewById(R.id.btn);
        newGameBtn.setVisibility(View.VISIBLE);

        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics dm = new DisplayMetrics();
        display.getMetrics(dm);

        memoGridView = findViewById(R.id.fields_grid_view);
        mAdapter = new GridMemoryAdapter(this, (int) (dm.heightPixels * .12));
        memoGridView.setAdapter(mAdapter);

        memoryViewModel = ViewModelProviders.of(this).get(MemoryViewModel.class);
        memoryViewModel.getMemoryList().observe(this, new Observer<List<Field>>() {
            @Override
            public void onChanged(List<Field> fields) {
                mAdapter.setFields(fields);
            }
        });

        memoryViewModel.getMemoryResults().observe(this, new Observer<Result>() {
            @Override
            public void onChanged(Result result) {
                setResults(result);
            }
        });

        memoGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                memoryViewModel.fieldClicked(position);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        memoryViewModel.stopTimer();

    }

    void setResults(Result result) {

        bestMoves.setText(String.valueOf(result.getBestMoves()));
        bestTime.setText(result.getBestTimeInSeconds());
        currentMoves.setText(String.valueOf(result.getCurrentMoves()));
        currentTime.setText(result.getCurrentTimeInSeconds());
        if (result.getSolved() == ALL_SOLVED) {
            newGameBtn.setVisibility(View.VISIBLE);
        }
    }

    public void setNewGame(View v) {
        memoryViewModel.newGame();
        newGameBtn.setVisibility(View.INVISIBLE);
    }

}
