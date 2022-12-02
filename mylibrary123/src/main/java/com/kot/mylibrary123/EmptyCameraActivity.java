package com.kot.mylibrary123;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import java.io.File;

public class EmptyCameraActivity extends AppCompatActivity {
    private static final String TAG = "EmptyCameraActivity";
    private static final int REQUEST_CAPTURE_MEDIA = 11111;
    private Bundle bundle;
    private String mCurrentPhotoPath;
    private Context context;
    private String media_format;
    private String file_name;
    private final boolean is_already_running = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty_camera);
        context = this;
        bundle = getIntent().getBundleExtra("BUNDLE_EXTRA");
        media_format = bundle.getString("media_format");

        dispatchTakePictureIntent();
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent;
        if (media_format.equals("image")) {
            takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            file_name = bundle.getString("siteCode") + "_" + bundle.getString("timeTask") + "_" + System.currentTimeMillis() + "_" + bundle.getString("campaignId") + "_un" + ".jpg";
        } else {
            takePictureIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
            file_name = bundle.getString("siteCode") + "_" + bundle.getString("timeTask") + "_" + System.currentTimeMillis() + "_" + bundle.getString("campaignId") + "_un" + ".mp4";
        }
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile;
            Uri photoURI = null;
            try {
                photoFile = Helper.createMediaFile(file_name);
                mCurrentPhotoPath = photoFile.getAbsolutePath();
                Log.i(TAG, "createImageFile() called" + mCurrentPhotoPath);
                photoURI = FileProvider.getUriForFile(this, "", photoFile);
            } catch (Exception e) {
                e.printStackTrace();
               // LocadApp.getCrashlyticsLogger().recordException(e);
                // Error occurred while creating the File
                Toast.makeText(context, "Error Taking Picture, Please Choose Locad Camera", Toast.LENGTH_SHORT).show();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "onActivityResult() called with: requestCode = [" + requestCode + "], resultCode = [" + resultCode + "], data = [" + data + "]");
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CAPTURE_MEDIA && resultCode == RESULT_OK && bundle != null) {
            String monitor_type = bundle.getString("monitor_type");

            if (monitor_type != null) {
                Intent intent = new Intent(context, Dash.class);
                bundle.putString("file_path", mCurrentPhotoPath);
                bundle.putString("file_name", file_name);
                Log.i(TAG, "onActivityResult: " + mCurrentPhotoPath + "---" + file_name);
                if (media_format.equals("image")) {
                    bundle.putInt("type", 0);
                }
                if (media_format.equals("video")) {
                    bundle.putInt("type", 1);
                }
                bundle.putString("format", media_format);
                intent.putExtra("BUNDLE_EXTRA", bundle);
                setResult(RESULT_OK, intent);
                finish();
            }
        }
    }
}