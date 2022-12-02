package com.kot.mylibrary123.Taskclick;

import static com.kot.mylibrary123.Constants.IMAGE;
import static com.kot.mylibrary123.Constants.VIDEO;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.kot.mylibrary123.Constants;
import com.kot.mylibrary123.Dash;
import com.kot.mylibrary123.Helper;
import com.kot.mylibrary123.R;
import com.otaliastudios.cameraview.CameraListener;
import com.otaliastudios.cameraview.CameraView;
import com.otaliastudios.cameraview.PictureResult;
import com.otaliastudios.cameraview.VideoResult;
import com.otaliastudios.cameraview.controls.Mode;
import com.otaliastudios.cameraview.size.SizeSelector;
import com.otaliastudios.cameraview.size.SizeSelectors;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class CameraViewActivity extends AppCompatActivity {

    private static final String TAG = "CameraViewActivity";
    private ImageButton pictureImageButton, videoImageButton;
    private long captureTime;
    private Bundle bundle;
    private TextView videoTimer;
    private int time = 0;
    private CameraView camera;

    public static String formatSeconds(int timeInSeconds) {
        int secondsLeft = timeInSeconds % 3600 % 60;
        int minutes = (int) Math.floor(timeInSeconds % 3600 / 60);
        int hours = (int) Math.floor(timeInSeconds / 3600);

        String HH = ((hours < 10) ? "0" : "") + hours;
        String MM = ((minutes < 10) ? "0" : "") + minutes;
        String SS = ((secondsLeft < 10) ? "0" : "") + secondsLeft;

        return HH + ":" + MM + ":" + SS;
    }

    // For activities
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_view);
        pictureImageButton = findViewById(R.id.capturePicture);
        videoImageButton = findViewById(R.id.captureVideo);
        videoTimer = findViewById(R.id.video_timer);
        camera = findViewById(R.id.camera);
        camera.setLifecycleOwner(this);

        bundle = getIntent().getBundleExtra(Constants.BUNDLE_EXTRA);

        String media_format = bundle.getString("media_format");

        if (media_format.equals(IMAGE)) {
            bundle.putInt("type", 0);
        }
        if (media_format.equals(VIDEO)) {
            bundle.putInt("type", 1);
        }

        bundle.putString("format", media_format);

//        SizeSelector dimensions = SizeSelectors.and(SizeSelectors.maxWidth(3000), SizeSelectors.maxHeight(5000));
        SizeSelector dimensions = SizeSelectors.maxArea(25000000);
//        camera.setSnapshotMaxWidth(2000);
//        camera.setSnapshotMaxHeight(2000);

        if (media_format.equals(IMAGE)) {
            videoImageButton.setVisibility(View.GONE);
            camera.setMode(Mode.PICTURE);
            camera.setPictureSize(dimensions);
            videoTimer.setVisibility(View.GONE);
        }

        if (media_format.equals(VIDEO)) {
            pictureImageButton.setVisibility(View.GONE);
            camera.setMode(Mode.VIDEO);
            camera.setVideoSize(dimensions);
            videoTimer.setVisibility(View.VISIBLE);
        }

        pictureImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (camera.getMode() == Mode.VIDEO) {
                    Toast.makeText(CameraViewActivity.this, "Cannot Take Picture in VIDEO mode", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (camera.isTakingPicture()) return;
                captureTime = System.currentTimeMillis();
                Toast.makeText(CameraViewActivity.this, "Capturing Picture...", Toast.LENGTH_SHORT).show();

                JSONObject dataJsonObject = new JSONObject();

                try {
                    dataJsonObject.put("format", "image");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

             //   ElasticLoggerUtility.analyticsLogger("Task Click Start", "", "{}");

                camera.takePicture();
            }
        });

        videoImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (camera.getMode() == Mode.PICTURE) {
                    Toast.makeText(CameraViewActivity.this, "Can't record HQ videos while in PICTURE mode.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (camera.isTakingPicture() || camera.isTakingVideo()) {
                    camera.stopVideo();
                    return;
                }

                Toast.makeText(CameraViewActivity.this, "Recording ", Toast.LENGTH_SHORT).show();
                String file_name = bundle.getString("siteCode") + "_" + bundle.getString("timeTask") + "_" + System.currentTimeMillis() + "_" + bundle.getString("campaignId") + "_un" + ".mp4";
                bundle.putString("file_name", file_name);

                File media_file = Helper.createMediaFile(file_name);

                if (media_file != null) {

                    JSONObject dataJsonObject = new JSONObject();

                    try {
                        dataJsonObject.put("format", "video");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                  //  ElasticLoggerUtility.analyticsLogger("Task Click Start", "", "{}");

                    camera.takeVideo(media_file);
                } else {
                    Toast.makeText(CameraViewActivity.this, "Image Capture File Error Try changing camera", Toast.LENGTH_SHORT).show();
                }
            }
        });

        camera.addCameraListener(new CameraListener() {
            private Timer timer;

            @Override
            public void onPictureTaken(@NonNull PictureResult result) {
                super.onPictureTaken(result);
                Log.d(TAG, "onPictureTaken() called with: result = [" + result + "]");
                Toast.makeText(CameraViewActivity.this, "Picture Taken " + result.getSize(), Toast.LENGTH_SHORT).show();

                String siteCode = bundle.getString("siteCode");
                String timeTask = bundle.getString("timeTask");
                String campaignId = bundle.getString("campaignId");

                String file_name = siteCode + "_" + timeTask + "_" + System.currentTimeMillis() + "_" + campaignId + "_un" + ".jpg";

                bundle.putString("file_name", file_name);

                File media_file = Helper.createMediaFile(file_name);

                if (media_file != null) {
                    try {
                        FileOutputStream fout = new FileOutputStream(media_file, true);
                        fout.write(result.getData());
                        fout.flush();
                        fout.close();
                        Toast.makeText(CameraViewActivity.this, media_file.getAbsolutePath(), Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(CameraViewActivity.this, Dash.class);
                        bundle.putString("file_path", media_file.getAbsolutePath());

                        intent.putExtra(Constants.BUNDLE_EXTRA, bundle);
                        setResult(RESULT_OK, intent);
                        finish();

                    } catch (IOException e) {
                        e.printStackTrace();
                       // LocadApp.getCrashlyticsLogger().recordException(e);
                    }
                } else {
                    Toast.makeText(CameraViewActivity.this, "Image Capture File Error Try changing camera", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onVideoRecordingStart() {
                super.onVideoRecordingStart();
                Log.d(TAG, "onVideoRecordingStart() called");
                /*videoImageButton.setImageResource(R.drawable.ic_baseline_stop_24);
                timer = new Timer();
                timer.scheduleAtFixedRate(new TimerTask() {
                    @Override
                    public void run() {
                        time = time + 1;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                videoTimer.setText(formatSeconds(time));
                            }
                        });
                    }
                }, 0, 1000);*/
            }

            @Override
            public void onVideoRecordingEnd() {
                super.onVideoRecordingEnd();
                Log.d(TAG, "onVideoRecordingEnd() called");
                /*videoImageButton.setImageResource(R.drawable.ic_videocam_black_24dp);
                if (timer != null) {
                    timer.cancel();
                }*/
            }

            @Override
            public void onVideoTaken(@NonNull VideoResult result) {
                super.onVideoTaken(result);
                Log.d(TAG, "onVideoTaken() called with: result = [" + result + "]");
                Toast.makeText(CameraViewActivity.this, result.getFile().getAbsolutePath(), Toast.LENGTH_SHORT).show();

               /* Intent intent = new Intent(CameraViewActivity.this, TaskClickerActivity.class);
                bundle.putString("file_path", result.getFile().getAbsolutePath());
                intent.putExtra(Constants.BUNDLE_EXTRA, bundle);
                setResult(RESULT_OK, intent);
                finish();*/
            }


        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        camera.clearCameraListeners();
    }
}