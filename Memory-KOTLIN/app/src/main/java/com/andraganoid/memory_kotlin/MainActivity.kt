package com.andraganoid.memory_kotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.Display
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var memoryViewModel :MemoryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        newGameButton.visibility = View.VISIBLE;
        var display = getWindowManager().getDefaultDisplay()
        var dm = DisplayMetrics();
        display.getMetrics(dm);

        val mAdapter = GridMemoryAdapter(this, (dm.heightPixels * .12).toInt())
        fieldsGridView.adapter = mAdapter

        fieldsGridView.setOnItemClickListener { parent, view, position, id ->
                        memoryViewModel.fieldClicked(position);
          //  Toast.makeText(this, "ItemClicked", Toast.LENGTH_SHORT).show();

        }

        memoryViewModel = ViewModelProviders.of(this).get(MemoryViewModel::class.java)
        memoryViewModel.memoryList.observe(this,
           Observer<List<Field>> {
                fields -> mAdapter.setFields(fields)
          //  fields -> if(fields==null){ Toast.makeText(this, "null", Toast.LENGTH_SHORT).show();}else{Toast.makeText(this, "NOTnull", Toast.LENGTH_SHORT).show();}
            })

        memoryViewModel.memoryResults.observe(this,
            Observer<Any> { result -> setResults(result as Result) })

        newGameButton.setOnClickListener {
            // it.visibility = View.INVISIBLE
             memoryViewModel.newGame();


           // Toast.makeText(this, "Clicked", Toast.LENGTH_SHORT).show();
            // setResult(Result(33, bestTime = 12345))

        }

    }

    override fun onPause() {
        super.onPause()
        //    memoryViewModel.stopTimer();

    }

    fun setResults(result: Result) {
        bestMovesValue.setText(result.bestMoves.toString())
        bestTimeValue.setText(result.bestTimeInSeconds())
        currentMovesValue.setText(result.currentMoves.toString())
        currentTimeValue.setText(result.currentTimeInSeconds())
    }

}


//MemoryViewModel memoryViewModel;
//GridView memoGridView;
//GridMemoryAdapter mAdapter;
//TextView bestMoves, bestTime, currentMoves, currentTime;
//static Button newGameBtn;
//
//@Override
//protected void onCreate(Bundle savedInstanceState) {
//    super.onCreate(savedInstanceState);
//    setContentView(R.layout.activity_main);
//
//    bestMoves = findViewById(R.id.best_moves_value);
//    bestTime = findViewById(R.id.best_time_value);
//    currentMoves = findViewById(R.id.current_moves_value);
//    currentTime = findViewById(R.id.current_time_value);
//    newGameBtn = findViewById(R.id.btn);
//    newGameBtn.setVisibility(View.VISIBLE);
//
//    Display display = getWindowManager().getDefaultDisplay();
//    DisplayMetrics dm = new DisplayMetrics();
//    display.getMetrics(dm);
//
//    memoGridView = findViewById(R.id.fields_grid_view);
//    mAdapter = new GridMemoryAdapter(this, (int) (dm.heightPixels * .12));
//    memoGridView.setAdapter(mAdapter);
//
//    memoryViewModel = ViewModelProviders.of(this).get(MemoryViewModel.class);
//    memoryViewModel.getMemoryList().observe(this, new Observer <List <Field>>() {
//        @Override
//        public void onChanged(List <Field> fields) {
//            mAdapter.setFields(fields);
//        }
//    });
//
//    memoryViewModel.getMemoryResults().observe(this, new Observer <Result>() {
//        @Override
//        public void onChanged(Result result) {
//            setResults(result);
//        }
//    });
//
//    memoGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//        @Override
//        public void onItemClick(AdapterView <?> parent, View view, int position, long id) {
//            memoryViewModel.fieldClicked(position);
//        }
//    });
//}
//
//@Override
//protected void onPause() {
//    super.onPause();
//    memoryViewModel.stopTimer();
//
//}
//
//void setResults(Result result) {
//
//    bestMoves.setText(String.valueOf(result.getBestMoves()));
//    bestTime.setText(result.getBestTimeInSeconds());
//    currentMoves.setText(String.valueOf(result.getCurrentMoves()));
//    currentTime.setText(result.getCurrentTimeInSeconds());
//}
//
//public void setNewGame(View v) {
//    memoryViewModel.newGame();
//    newGameBtn.setVisibility(View.INVISIBLE);
//}
