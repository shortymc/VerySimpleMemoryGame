package com.andraganoid.memory_java;

import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class GridMemoryAdapter extends BaseAdapter {

    Context context;
    List <Field> fList = new ArrayList <>();
    LayoutInflater inflater;
    TextView item;
    ImageView back;
    int itemHeight;
    Field currentField;

    public GridMemoryAdapter(Context context, int itemHeight) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.itemHeight = itemHeight;
        setFields(null);
    }

    @Override
    public int getCount() {
        return fList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        view = inflater.inflate(R.layout.field_row, parent, false);
        view.setLayoutParams(new GridView.LayoutParams(GridView.AUTO_FIT, itemHeight));
        item = view.findViewById(R.id.field_item);
        back = view.findViewById(R.id.field_back_img);
        item.setTextSize(TypedValue.COMPLEX_UNIT_PX, itemHeight / 2);

        currentField = fList.get(position);
        item.setText(String.valueOf(currentField.getItem()));
        if (currentField.isSolved()) {
            item.setBackgroundColor(context.getResources().getColor(R.color.colorAccent));
        }

        if (currentField.isOpen() || currentField.isSolved()) {
            item.setVisibility(View.VISIBLE);
            back.setVisibility(View.GONE);
        } else {
            item.setVisibility(View.GONE);
            back.setVisibility(View.VISIBLE);
        }

        return view;
    }

    public void setFields(List <Field> lf) {
        if (lf != null) {
            fList = lf;
        } else {
            fList.clear();
        }
        notifyDataSetChanged();
    }
}
