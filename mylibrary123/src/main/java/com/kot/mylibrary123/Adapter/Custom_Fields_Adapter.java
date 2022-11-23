package com.kot.mylibrary123.Adapter;

import static com.kot.mylibrary123.Dashboard.finaldata;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.kot.mylibrary123.Model.Problem_model;
import com.kot.mylibrary123.Model.Sub_problem;
import com.kot.mylibrary123.R;

import java.util.ArrayList;
import java.util.List;

public class Custom_Fields_Adapter extends RecyclerView.Adapter<Custom_Fields_Adapter.Object> {
    Context context;
    List<String>list;
    RecyclerView problem_recy;
    Custom_Problem_Adapter adapter;
    List<Problem_model>list1;
    List<Sub_problem>sub_list,sub_list1,sub_list2;
    Custom_selected_problem custom_selected_problem;
    public Custom_Fields_Adapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public Custom_Fields_Adapter.Object onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Object(LayoutInflater.from(context).inflate(R.layout.custom_fields,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull Custom_Fields_Adapter.Object holder, int position) {

        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheetDialog bottomSheet = new BottomSheetDialog(context);
                bottomSheet.setContentView(R.layout.problem_popup);
                bottomSheet.show();

                problem_recy=bottomSheet.findViewById(R.id.problem_recy);

                list1=new ArrayList<>();
                sub_list=new ArrayList<>();
                sub_list.add(new Sub_problem("13267546723"));
                sub_list.add(new Sub_problem("2348899"));
                sub_list.add(new Sub_problem("343u45"));
                sub_list.add(new Sub_problem("42435"));
                sub_list.add(new Sub_problem("554"));
                sub_list1=new ArrayList<>();
                sub_list1.add(new Sub_problem("adsg"));
                sub_list1.add(new Sub_problem("bdsg"));
                sub_list1.add(new Sub_problem("cgsgfg"));
                sub_list1.add(new Sub_problem("dgsfg"));
                sub_list1.add(new Sub_problem("eggds"));
                sub_list2=new ArrayList<>();
                sub_list2.add(new Sub_problem("ADFSJHK"));
                sub_list2.add(new Sub_problem("BKJDFH"));
                sub_list2.add(new Sub_problem("CKJDSF"));
                sub_list2.add(new Sub_problem("DKJDF"));
                sub_list2.add(new Sub_problem("EJKHFDS"));
                problem_recy.setLayoutManager(new LinearLayoutManager(context));
                list1.add(new Problem_model("first",sub_list));
                list1.add(new Problem_model("Second",sub_list1));
                list1.add(new Problem_model("Third",sub_list2));
                adapter=new Custom_Problem_Adapter(context,list1);
                problem_recy.setAdapter(adapter);
                Button btn=bottomSheet.findViewById(R.id.btn);
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        holder.selected_recy.setLayoutManager(new StaggeredGridLayoutManager(3, LinearLayoutManager.VERTICAL));
                        custom_selected_problem=new Custom_selected_problem(finaldata, context);
                        holder.selected_recy.setAdapter(custom_selected_problem);
                        bottomSheet.dismiss();
                        Log.i("SAdsfsf", finaldata.toString());
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Object extends RecyclerView.ViewHolder {
        RecyclerView selected_recy;
        TextView name;
        public Object(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.name);
            selected_recy=itemView.findViewById(R.id.selected_problem_recy);
        }
    }
}
