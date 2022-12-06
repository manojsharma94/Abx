package com.kot.mylibrary123.Fragmnt;

import static com.kot.mylibrary123.Dashboard.finaldata;

import android.Manifest;
import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputEditText;
import com.kot.mylibrary123.Adapter.Custom_Image_Adapter;
import com.kot.mylibrary123.Adapter.Custom_Problem_Adapter;
import com.kot.mylibrary123.Adapter.Custom_selected_problem;
import com.kot.mylibrary123.Helper;
import com.kot.mylibrary123.Model.Problem_model;
import com.kot.mylibrary123.Model.Sub_problem;
import com.kot.mylibrary123.R;
import com.kot.mylibrary123.Taskclick.ProcessedTask;
import com.kot.mylibrary123.Taskclick.TaskClickerInteractor;
import com.kot.mylibrary123.Taskclick.TaskClickerMvpPresenter;
import com.kot.mylibrary123.Taskclick.TaskClickerMvpView;
import com.kot.mylibrary123.Taskclick.TaskClickerPresenter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;


public class Pages_Fragment extends Fragment implements TaskClickerMvpView {
    Location currentLocation;
    FusedLocationProviderClient fusedLocationProviderClient;
    private static final int REQUEST_CODE = 101;
    private static final String TAG = "Page";
    LinearLayout img_layout;
    private static final int REQUEST_CAPTURE_MEDIA = 11111;
    private static final int pic_id = 123;
    RecyclerView problem_recy;
    private TaskClickerMvpPresenter taskClickerMvpPresenter;
    Custom_Problem_Adapter adapter;
    List<Problem_model>list1;
    List<Sub_problem>sub_list,sub_list1,sub_list2;
    Custom_selected_problem custom_selected_problem;
    RecyclerView img_recy;
    Custom_Image_Adapter custom_image_adapter;
    List<Bitmap>image_list;
    TextView name;
    RecyclerView selected_recy;
    private JSONObject siteImageColorsJsonObject;
    private JSONArray siteImageHeadersJsonArray;
    private JSONArray sitePreferencesJsonArray;
    private static Context app;
    private String file_name;
    private String mCurrentPhotoPath;
    ImageView location_img;
    TextInputEditText locatio_edittext;
    public static Context getconet(){
        return app;

    }
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
        fusedLocationProviderClient= LocationServices.getFusedLocationProviderClient(requireActivity());

        location_img=view.findViewById(R.id.loction_img);
        locatio_edittext=view.findViewById(R.id.locatio_edittext);
        selected_recy=view.findViewById(R.id.selected_problem_recy);
        name=view.findViewById(R.id.name);
        img_layout=view.findViewById(R.id.img_lay);
        app=getActivity();
        img_recy=view.findViewById(R.id.img_recy);


        image_list=new ArrayList<>();
        custom_image_adapter=new Custom_Image_Adapter(image_list,getActivity());
        img_recy.setLayoutManager(new GridLayoutManager(getActivity(),3));
        img_recy.setAdapter(custom_image_adapter);
        location_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fetchLocation();
            }
        });
        img_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Bundle bundle = new Bundle();
                bundle.putDouble("lat",0.0);
                bundle.putDouble("lng", 0.0);
                bundle.putString("siteCode", "0060");
                bundle.putString("siteId", "NA");
                bundle.putString("timeTask", "t");
                bundle.putString("taskType", "p");
                bundle.putString("campaignId", "1234");
                bundle.putString("monitor_type", "normal_monitor");
                bundle.putString("campaign_name", "campaign_name_json");
                dispatchTakePictureIntent();
              /*  Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(camera_intent, pic_id);*/
            }
        });
        taskClickerMvpPresenter = new TaskClickerPresenter(this, new TaskClickerInteractor());
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
                Objects.requireNonNull(btn).setOnClickListener(new View.OnClickListener() {
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
        if (requestCode == REQUEST_CAPTURE_MEDIA && resultCode == Activity.RESULT_OK) {
            Bundle bundle =new Bundle();
            bundle.putString("file_path", mCurrentPhotoPath);
            bundle.putString("file_name", file_name);
            taskClickerMvpPresenter.handleDoneTask(bundle,"image", siteImageColorsJsonObject, siteImageHeadersJsonArray);
        }
    }

    @Override
    public void showSiteCode(String site_code) {

    }

    @Override
    public void showCampaignName(String campaign_name) {

    }

    @Override
    public void showLocation(String lat_lng) {

    }

    @Override
    public void showError(String error) {

    }

    @Override
    public void showLocationPrompt() {

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void addToArray(String processedTask) {

        File imgFile = new  File(processedTask);
        Log.i("gfg",processedTask);
        if(imgFile.exists()){
            Log.i("gfg",processedTask);
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

            image_list.add(myBitmap);
            custom_image_adapter.notifyDataSetChanged();

        }

    }

    @Override
    public void openMainActivity() {

    }

    @Override
    public void setUploadedByPrefrencesData(JSONArray sitePrefrencesJsonArray, JSONObject siteImageColorsJsonObject, JSONArray siteImageHeadersJsonArray) {
        this.sitePreferencesJsonArray = sitePrefrencesJsonArray;
        this.siteImageColorsJsonObject = siteImageColorsJsonObject;
        this.siteImageHeadersJsonArray = siteImageHeadersJsonArray;

    }


    private void dispatchTakePictureIntent() {
        Intent takePictureIntent;

            takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            file_name = "manoj"+ "_" + System.currentTimeMillis() + "_" + "_un" + ".jpg";

        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile;
            Uri photoURI = null;
            try {
                photoFile = Helper.createMediaFile(file_name);
                mCurrentPhotoPath = photoFile.getAbsolutePath();

                Log.i(TAG, "createImageFile() called" + mCurrentPhotoPath);
                photoURI = FileProvider.getUriForFile(getActivity(), "com.kot.mylibrary123.fileprovider", photoFile);
            } catch (Exception e) {
                e.printStackTrace();
                //LocadApp.getCrashlyticsLogger().recordException(e);
                // Error occurred while creating the File
                Toast.makeText(getActivity(), "Error Taking Picture, Please Choose Locad Camera", Toast.LENGTH_SHORT).show();
            }
            // Continue only if the File was successfully created
            if (photoURI != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP) {
                    takePictureIntent.setClipData(ClipData.newRawUri("", photoURI));
                    takePictureIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
                }
                startActivityForResult(takePictureIntent, REQUEST_CAPTURE_MEDIA);
            }
        }
    }
    private void fetchLocation() {
        if (ActivityCompat.checkSelfPermission(
                getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
            return;
        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        ((Task) task).addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    currentLocation = location;
                    Geocoder geocoder=new Geocoder(getActivity(), Locale.getDefault());
                    try {
                        List<Address>addresses=geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
                        locatio_edittext.setText(addresses.get(0).getAddressLine(0));
                        Log.i("fdsfd",addresses.get(0).getAddressLine(0));

                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                    Toast.makeText(getActivity(), currentLocation.getLatitude() + "" + currentLocation.getLongitude(), Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
}