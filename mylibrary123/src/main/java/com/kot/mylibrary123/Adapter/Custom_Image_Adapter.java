package com.kot.mylibrary123.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kot.mylibrary123.R;

import java.util.List;

public class Custom_Image_Adapter extends RecyclerView.Adapter<Custom_Image_Adapter.Object> {
    List<Bitmap>img_list;
    Context context;

    public Custom_Image_Adapter(List<Bitmap> img_list, Context context) {
        this.img_list = img_list;
        this.context = context;
    }

    @NonNull
    @Override
    public Custom_Image_Adapter.Object onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Object(LayoutInflater.from(context).inflate(R.layout.custom_image,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull Custom_Image_Adapter.Object holder, int position) {
        holder.img.setImageBitmap(img_list.get(position));

    }

    @Override
    public int getItemCount() {
        return img_list.size();
    }

    public class Object extends RecyclerView.ViewHolder {
        ImageView img;
        public Object(@NonNull View itemView) {
            super(itemView);
            img=itemView.findViewById(R.id.img);
        }
    }
}
