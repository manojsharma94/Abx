package com.kot.mylibrary123.Fragmnt;

import static com.kot.mylibrary123.Dashboard.finaldata;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.kot.mylibrary123.Adapter.Custom_Image_Adapter;
import com.kot.mylibrary123.Adapter.Custom_Problem_Adapter;
import com.kot.mylibrary123.Adapter.Custom_selected_problem;
import com.kot.mylibrary123.Model.Problem_model;
import com.kot.mylibrary123.Model.Sub_problem;
import com.kot.mylibrary123.R;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class Pages_Fragment extends Fragment {
    LinearLayout img_layout;
    private static final int pic_id = 123;
    RecyclerView problem_recy;
    Custom_Problem_Adapter adapter;
    List<Problem_model>list1;
    List<Sub_problem>sub_list,sub_list1,sub_list2;
    Custom_selected_problem custom_selected_problem;
    RecyclerView img_recy;
    Custom_Image_Adapter custom_image_adapter;
    List<Bitmap>image_list;
    TextView name;
    RecyclerView selected_recy;
    public static Pages_Fragment newInstance() {
        return new Pages_Fragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_pages_, container, false);
        selected_recy=view.findViewById(R.id.selected_problem_recy);
        name=view.findViewById(R.id.name);
        img_layout=view.findViewById(R.id.img_lay);

        img_recy=view.findViewById(R.id.img_recy);


        image_list=new ArrayList<>();
        custom_image_adapter=new Custom_Image_Adapter(image_list,getActivity());
        img_recy.setLayoutManager(new GridLayoutManager(getActivity(),3));
        img_recy.setAdapter(custom_image_adapter);
        img_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(camera_intent, pic_id);
            }
        });
        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheetDialog bottomSheet = new BottomSheetDialog(getActivity());
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
                problem_recy.setLayoutManager(new LinearLayoutManager(getActivity()));
                list1.add(new Problem_model("first",sub_list));
                list1.add(new Problem_model("Second",sub_list1));
                list1.add(new Problem_model("Third",sub_list2));
                adapter=new Custom_Problem_Adapter(getActivity(),list1);
                problem_recy.setAdapter(adapter);
                Button btn=bottomSheet.findViewById(R.id.btn);
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        selected_recy.setLayoutManager(new StaggeredGridLayoutManager(3, LinearLayoutManager.VERTICAL));
                        custom_selected_problem=new Custom_selected_problem(finaldata, getActivity());
                        selected_recy.setAdapter(custom_selected_problem);
                        bottomSheet.dismiss();
                        Log.i("SAdsfsf", finaldata.toString());
                    }
                });
            }
        });
        return view;
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == pic_id && resultCode == Activity.RESULT_OK) {
            // BitMap is data structure of image file which store the image in memory
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            // Set the image in imageview for display
            image_list.add(photo);
            custom_image_adapter.notifyDataSetChanged();
        }
    }
}