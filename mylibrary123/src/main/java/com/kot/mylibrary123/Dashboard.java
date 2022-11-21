package com.kot.mylibrary123;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.kot.mylibrary123.Adapter.Custom_Problem_Adapter;
import com.kot.mylibrary123.Adapter.Custom_selected_problem;
import com.kot.mylibrary123.Model.Problem_model;
import com.kot.mylibrary123.Model.Sub_problem;

import java.util.ArrayList;
import java.util.List;

public class Dashboard extends AppCompatActivity {
    RecyclerView problem_recy;
    Custom_Problem_Adapter adapter;
    List<Problem_model>list;
    List<Sub_problem>sub_list,sub_list1,sub_list2;
    TextView name;
    RecyclerView selected_recy;
    Custom_selected_problem custom_selected_problem;
   // List<String>final_data;
    public static List<String>finaldata=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        getSupportActionBar().setTitle("Audit");
        name=findViewById(R.id.name);
        selected_recy=findViewById(R.id.selected_problem_recy);
        selected_recy.setLayoutManager(new StaggeredGridLayoutManager(3, LinearLayoutManager.VERTICAL));
        custom_selected_problem=new Custom_selected_problem(finaldata,Dashboard.this);
        selected_recy.setAdapter(custom_selected_problem);
        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             //   View view1= LayoutInflater.from(Dashboard.this).
                BottomSheetDialog bottomSheet = new BottomSheetDialog(Dashboard.this);
                bottomSheet.setContentView(R.layout.problem_popup);
                bottomSheet.show();

                problem_recy=bottomSheet.findViewById(R.id.problem_recy);

                list=new ArrayList<>();
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
                problem_recy.setLayoutManager(new LinearLayoutManager(Dashboard.this));
                list.add(new Problem_model("first",sub_list));
                list.add(new Problem_model("Second",sub_list1));
                list.add(new Problem_model("Third",sub_list2));
                adapter=new Custom_Problem_Adapter(Dashboard.this,list);
                problem_recy.setAdapter(adapter);
                Button btn=bottomSheet.findViewById(R.id.btn);
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        /*final_data=new ArrayList<>();
                        for(int i =0;i<list.size();i++){
                            for(int j=0;j<list.get(i).getProblem_list().size();j++){
                                Log.i("SAdsfsf",list.get(i).getProblem_list().get(j).isCheck()+"");

                                if(list.get(i).getProblem_list().get(j).isCheck()){
                                    final_data.add(list.get(i).getProblem_list().get(j).getName());
                                    Log.i("SAdsfsf",list.get(i).getProblem_list().get(j).getName());
                                }
                            }
                        }*/
                        bottomSheet.dismiss();
                        custom_selected_problem.notifyDataSetChanged();
                        Log.i("SAdsfsf",Dashboard.finaldata.toString());
                    }
                });
            }
        });
    }
}