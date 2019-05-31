package com.andraganoid.memory_java;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.andraganoid.memory_java.databinding.FieldRowBinding;

import java.util.ArrayList;
import java.util.List;


public class MemoryAdapter extends RecyclerView.Adapter<MemoryAdapter.ViewHolder> {
    private List<Field> fList;
    private ClickHandler click;
    private MemoryViewModel viewModel;

    MemoryAdapter(MemoryViewModel viewModel, ClickHandler click) {
        this.click = click;
        this.fList = new ArrayList<>();
        this.viewModel = viewModel;
    }

    void setFields(List<Field> fList) {
        this.fList = fList;
        notifyDataSetChanged();
    }

    public void setItem(int id) {
        notifyItemChanged(id);
    }

    @Override
    public int getItemCount() {
        return fList.size();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        FieldRowBinding binding =
                DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.field_row,
                        parent,
                        false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(fList.get(position));
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        FieldRowBinding binding;

        ViewHolder(@NonNull FieldRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.setClick(click);
            binding.setViewModel(viewModel);
        }

        void bind(Field field) {
            binding.setField(field);
        }
    }
}
