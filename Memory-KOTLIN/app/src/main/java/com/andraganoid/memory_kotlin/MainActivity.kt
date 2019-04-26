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

    lateinit var memoryViewModel: MemoryViewModel

    val ALL_SOLVED = 8

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
            Observer<List<Field>> { fields ->
                mAdapter.setFields(fields)
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
        if (result.solved == ALL_SOLVED) {
            newGameButton.visibility = View.VISIBLE
        }
    }

}


