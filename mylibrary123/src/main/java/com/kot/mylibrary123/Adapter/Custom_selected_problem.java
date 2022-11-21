package com.kot.mylibrary123.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kot.mylibrary123.R;

import java.util.List;

public class Custom_selected_problem extends RecyclerView.Adapter<Custom_selected_problem.Obejct> {
    List<String>list;
    Context context;

    public Custom_selected_problem(List<String> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public Custom_selected_problem.Obejct onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Obejct(LayoutInflater.from(context).inflate(R.layout.custom_selected_problem,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull Custom_selected_problem.Obejct holder, int position) {
        holder.name.setText(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Obejct extends RecyclerView.ViewHolder {
        TextView name;
        public Obejct(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.name);
        }
    }
}
