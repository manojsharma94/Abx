package com.kot.mylibrary123.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kot.mylibrary123.Model.Sub_problem;
import com.kot.mylibrary123.R;

import java.util.List;

public class Sub_problem_dapter extends RecyclerView.Adapter<Sub_problem_dapter.Object> {
    Context context;
    List<Sub_problem>list;

    public Sub_problem_dapter(Context context, List<Sub_problem> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public Sub_problem_dapter.Object onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Object(LayoutInflater.from(context).inflate(R.layout.custom_sub_problem,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull Sub_problem_dapter.Object holder, int position) {
        holder.name.setText(list.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Object extends RecyclerView.ViewHolder {
        CheckBox name;
        public Object(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.name);
        }
    }
}
