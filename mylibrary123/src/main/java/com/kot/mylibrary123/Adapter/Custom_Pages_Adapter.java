package com.kot.mylibrary123.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kot.mylibrary123.Model.Problem_model;
import com.kot.mylibrary123.R;

import java.util.ArrayList;
import java.util.List;

public class Custom_Pages_Adapter extends RecyclerView.Adapter<Custom_Pages_Adapter.Object> {
    Context context;
    List<Problem_model>list;
    List<String>fields=new ArrayList<>();
    Custom_Fields_Adapter custom_fields_adapter;

    public Custom_Pages_Adapter(Context context, List<Problem_model> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public Custom_Pages_Adapter.Object onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Object(LayoutInflater.from(context).inflate(R.layout.custom_page,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull Custom_Pages_Adapter.Object holder, int position) {
        holder.page_num_text.setText("Page : "+(position+1)+"/"+list.size());
        fields=new ArrayList<>();
        fields.add("");

        custom_fields_adapter=new Custom_Fields_Adapter(context,fields);
        holder.page_recy.setLayoutManager(new LinearLayoutManager(context));
        holder.page_recy.setAdapter(custom_fields_adapter);


        holder.page_open_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(list.get(holder.getAdapterPosition()).isCheck()){
                    holder.page_recy.setVisibility(View.GONE);
                    list.get(holder.getAdapterPosition()).setCheck(false);
                    holder.up_down.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down_24);
                }
                else {
                    holder.page_recy.setVisibility(View.VISIBLE);
                    list.get(holder.getAdapterPosition()).setCheck(true);
                    holder.up_down.setImageResource(R.drawable.ic_baseline_keyboard_arrow_up_24);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Object extends RecyclerView.ViewHolder {
        RelativeLayout page_open_lay;
        TextView page_num_text;
        RecyclerView page_recy;
        ImageView up_down;

        public Object(@NonNull View itemView) {
            super(itemView);

            up_down=itemView.findViewById(R.id.up_down);
            page_open_lay=itemView.findViewById(R.id.page_open_lay);
            page_recy=itemView.findViewById(R.id.page_recy);
            page_num_text=itemView.findViewById(R.id.page_num_text);

        }
    }
}
