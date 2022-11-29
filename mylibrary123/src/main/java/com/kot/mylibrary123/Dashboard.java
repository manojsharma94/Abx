package com.kot.mylibrary123;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission_group.CAMERA;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.kot.mylibrary123.Adapter.Custom_Pages_Adapter;
import com.kot.mylibrary123.Interface.Checkiner;
import com.kot.mylibrary123.Model.Problem_model;
import com.kot.mylibrary123.Model.Sub_problem;

import java.util.ArrayList;
import java.util.List;

public class Dashboard extends AppCompatActivity implements  Checkiner{
   /* RecyclerView problem_recy;
    Custom_Problem_Adapter adapter;
    List<Problem_model>list;
    List<Sub_problem>sub_list,sub_list1,sub_list2;
    TextView name;
    RecyclerView selected_recy;
    Custom_selected_problem custom_selected_problem;*/
   // List<String>final_data;
    public  static MutableLiveData<String>liveData=new MutableLiveData<>();

    String[] PERMISSIONS = {android.Manifest.permission.CAMERA,
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.RECORD_AUDIO,
            android.Manifest.permission.READ_PHONE_STATE};
    List<Sub_problem>data1;
    RecyclerView page_recy;
    Custom_Pages_Adapter custom_pages_adapter;
    public static List<String>finaldata=new ArrayList<>();
    List<Problem_model>data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        getSupportActionBar().setTitle("Audit");
        Dexter.withContext(Dashboard.this)
                .withPermissions(PERMISSIONS)
                .withListener(new MultiplePermissionsListener() {
                    @SuppressLint("MissingPermission")
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                        if (multiplePermissionsReport.areAllPermissionsGranted()) {

                        } else {
                            Toast.makeText(Dashboard.this, "Check Permissions in Settings", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {

                    }
                }).check();
        page_recy=findViewById(R.id.page_recy);
        getdata("safdsfdsf");
        page_recy.setLayoutManager(new LinearLayoutManager(Dashboard.this));
        data=new ArrayList<>();
        data1=new ArrayList<>();
        data1.add(new Sub_problem(""));
        data.add(new Problem_model("abc",data1));
        data.add(new Problem_model("abc",data1));
        data.add(new Problem_model("abc",data1));
        custom_pages_adapter=new Custom_Pages_Adapter(Dashboard.this,data);
        page_recy.setAdapter(custom_pages_adapter);
        liveData.setValue("sadshdsfkjh");

      /*  name=findViewById(R.id.name);
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
                        bottomSheet.dismiss();
                        custom_selected_problem.notifyDataSetChanged();
                        Log.i("SAdsfsf",Dashboard.finaldata.toString());
                    }
                });
            }
        });*/

    }
    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }
    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(), ACCESS_FINE_LOCATION);
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(), CAMERA);

        return result1 == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED;
    }



    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(Dashboard.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }


    @Override
    public void getdata(String name) {

    }


    @Override
    protected void onDestroy() {
        getdata("manoj");
       // liveData.setValue("hello manoj");
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri selectedImageURI = data.getData();
        Log.i("fasdfs",selectedImageURI.toString());


    }
}