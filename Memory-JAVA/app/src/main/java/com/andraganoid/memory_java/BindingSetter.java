package com.andraganoid.memory_java;

import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.BindingAdapter;

public class BindingSetter {

    @BindingAdapter("my_layout_height")
    public static void setLayoutHeight(View view, int height) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = height;
        view.setLayoutParams(layoutParams);
    }
}
