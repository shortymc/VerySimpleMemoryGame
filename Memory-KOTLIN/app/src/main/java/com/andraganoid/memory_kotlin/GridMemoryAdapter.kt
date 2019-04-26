package com.andraganoid.memory_kotlin

import android.content.Context
import android.graphics.Color
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.field_row.view.*
import java.util.ArrayList



class GridMemoryAdapter(internal val context: Context, internal val itemHeight: Int) : BaseAdapter() {
    internal var fList: List<Field> = listOf()
  //  internal var inflater: LayoutInflater
//    internal var item: TextView
//    internal var back: ImageView
//    internal var currentField: Field

    init {

       // inflater = LayoutInflater.from(context)
       setFields(fList)
    }

    override fun getCount(): Int {

       //  return  fList?.size ?: 0;
        return fList.size
    }

    override fun getItem(position: Int): Any? {
        return null
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(position: Int, view: View?, parent: ViewGroup?): View {




//
//
//
  //  var
//
//      //  var inflater :LayoutInflater
//        var inflator = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
//        var view = inflator.inflate(R.layout.field_row, null)
//
          val  gView  = LayoutInflater.from(context).inflate(R.layout.field_row, parent, false)
        gView.layoutParams = AbsListView.LayoutParams(GridView.AUTO_FIT, itemHeight)
        val item = gView.fieldItem;
        val back = gView.fieldBackImg;
        item.setTextSize(TypedValue.COMPLEX_UNIT_PX, (itemHeight / 2).toFloat())

        val currentField = fList[position]
        item.setText(currentField.item.toString())
        if (currentField.isSolved) {
            item.setBackgroundColor(ContextCompat.getColor(context, R.color.colorAccent));
        }

        if (currentField.isOpen || currentField.isSolved) {
            item.visibility = View.VISIBLE
            back.visibility = View.GONE
        } else {
            item.visibility = View.GONE
            back.visibility = View.VISIBLE
        }

        return gView
    }

    fun setFields(lf: List<Field>) {
//        fList = if (lf != null) lf else {
//            listOf()
//        }

        fList=lf;
notifyDataSetChanged()

//        if (lf != null) {
//            fList = lf
//        } else {
//            null
//        }
    }
}