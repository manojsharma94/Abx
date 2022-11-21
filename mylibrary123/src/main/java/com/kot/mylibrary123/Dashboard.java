package com.kot.mylibrary123;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.kot.mylibrary123.Adapter.Custom_Problem_Adapter;
import com.kot.mylibrary123.Model.Problem_model;
import com.kot.mylibrary123.Model.Sub_problem;

import java.util.ArrayList;
import java.util.List;

public class Dashboard extends AppCompatActivity {
    RecyclerView problem_recy;
    Custom_Problem_Adapter adapter;
    List<Problem_model>list;
    List<Sub_problem>sub_list,sub_list1,sub_list2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        getSupportActionBar().setTitle("Audit");
        problem_recy=findViewById(R.id.problem_recy);
        list=new ArrayList<>();
        sub_list=new ArrayList<>();
        sub_list.add(new Sub_problem("1"));
        sub_list.add(new Sub_problem("2"));
        sub_list.add(new Sub_problem("3"));
        sub_list.add(new Sub_problem("4"));
        sub_list.add(new Sub_problem("5"));
        sub_list1=new ArrayList<>();
        sub_list1.add(new Sub_problem("a"));
        sub_list1.add(new Sub_problem("b"));
        sub_list1.add(new Sub_problem("c"));
        sub_list1.add(new Sub_problem("d"));
        sub_list1.add(new Sub_problem("e"));
        sub_list2=new ArrayList<>();
        sub_list2.add(new Sub_problem("A"));
        sub_list2.add(new Sub_problem("B"));
        sub_list2.add(new Sub_problem("C"));
        sub_list2.add(new Sub_problem("D"));
        sub_list2.add(new Sub_problem("E"));
        problem_recy.setLayoutManager(new LinearLayoutManager(Dashboard.this));
        list.add(new Problem_model("first",sub_list));
        list.add(new Problem_model("Second",sub_list1));
        list.add(new Problem_model("Third",sub_list2));
        adapter=new Custom_Problem_Adapter(Dashboard.this,list);
        problem_recy.setAdapter(adapter);
    }
}