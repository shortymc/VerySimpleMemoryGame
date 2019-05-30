package com.andraganoid.memory_java;

import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.BindingAdapter;

public class Field {

    private int item;
    private boolean solved;
    private boolean open;
    private int index;
    private int itemHeight;


    public Field(int item, int index, int itemHeight) {
        this.item = item;
        this.solved = false;
        this.open = false;
        this.index = index;
        this.itemHeight = itemHeight;
    }


    public int getItem() {
        return item;
    }

    public boolean isSolved() {
        return solved;
    }

    public void setSolved(boolean solved) {
        this.solved = solved;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public int getIndex() {
        return index;
    }

    public int getItemHeight() {
        return itemHeight;

    }

    public void setItemHeight(int itemHeight) {
        this.itemHeight = itemHeight;
    }

    @BindingAdapter("my_layout_height")
    public static void setLayoutHeight(View view, int height) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = height;
        view.setLayoutParams(layoutParams);
    }
}
