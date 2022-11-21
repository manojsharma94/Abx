package com.kot.mylibrary123.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kot.mylibrary123.Model.Problem_model;
import com.kot.mylibrary123.Model.Sub_problem;
import com.kot.mylibrary123.R;

import java.util.ArrayList;
import java.util.List;

public class Custom_Problem_Adapter extends RecyclerView.Adapter<Custom_Problem_Adapter.Object> {
    Context context;
    List<Problem_model>list;
    List<Sub_problem>sub_problemList=new ArrayList<>();
    Sub_problem_dapter sub_problem_dapter;


    public Custom_Problem_Adapter(Context context, List<Problem_model> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public Custom_Problem_Adapter.Object onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Object(LayoutInflater.from(context).inflate(R.layout.custom_problem_main,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull Custom_Problem_Adapter.Object holder, int position) {
        holder.name.setText(list.get(position).getName());
        sub_problemList=new ArrayList<>();
        holder.sub_problem_recy.setLayoutManager(new LinearLayoutManager(context));
        sub_problem_dapter=new Sub_problem_dapter(context,sub_problemList);
        holder.sub_problem_recy.setAdapter(sub_problem_dapter);

        for(int i=0;i<list.get(position).getProblem_list().size();i++){
            sub_problemList.add(new Sub_problem(list.get(position).getProblem_list().get(i).getName()));
            sub_problem_dapter.notifyDataSetChanged();
            }
        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(list.get(position).isCheck()){
                    holder.sub_problem_recy.setVisibility(View.GONE);
                    list.get(position).setCheck(false);
                }
                else {
                    holder.sub_problem_recy.setVisibility(View.VISIBLE);
                    list.get(position).setCheck(true);
                }
            }
        });





    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Object extends RecyclerView.ViewHolder {
        TextView name;
        RecyclerView sub_problem_recy;

        public Object(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.name);
            sub_problem_recy=itemView.findViewById(R.id.sub_problem_recy);
        }
    }
}
