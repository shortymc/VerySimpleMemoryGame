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

//
//class GridMemoryAdapter: BaseAdapter() {
//    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//
//    override fun getItem(position: Int): Any {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//
//    override fun getItemId(position: Int): Long {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//
//    override fun getCount(): Int {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//}

class GridMemoryAdapter(internal val context: Context, internal val itemHeight: Int) : BaseAdapter() {
    internal var fList: List<Field> = listOf();
    internal var inflater: LayoutInflater
//    internal var item: TextView
//    internal var back: ImageView
//    internal var currentField: Field

    init {
        inflater = LayoutInflater.from(context)
        setFields(null)
    }

    override fun getCount(): Int {

        // return  fList?.size ?: 0;
        return fList.size
    }

    override fun getItem(position: Int): Any? {
        return null
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(position: Int, view: View, parent: ViewGroup): View {
        // var view = view
        var view = inflater.inflate(R.layout.field_row, parent, false)
        view.layoutParams = AbsListView.LayoutParams(GridView.AUTO_FIT, itemHeight)
        val item = view.fieldItem;
        val back = view.fieldBackImg;
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

        return view
    }

    fun setFields(lf: List<Field>?) {

        fList = if (lf != null) lf else {
            listOf()
        }


//        if (lf != null) {
//            fList = lf
//        } else {
//            fList.clear()
//        }
        notifyDataSetChanged()
    }
}