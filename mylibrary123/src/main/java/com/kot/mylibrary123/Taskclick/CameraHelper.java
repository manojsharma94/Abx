package com.kot.mylibrary123.Taskclick;



import static com.kot.mylibrary123.Helper.removeJSONArray;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.widget.SwitchCompat;
import androidx.core.content.ContextCompat;


import com.kot.mylibrary123.Dash;
import com.kot.mylibrary123.Fragmnt.Pages_Fragment;
import com.kot.mylibrary123.Helper;
import com.kot.mylibrary123.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Deepak on 29-01-2018.
 */

public class CameraHelper {

    private static final String TAG = "CameraHelper";

    public static String storeImage(OnHandleImageListener onHandleImageListener, Bitmap image, String file_name) {
        File pictureFile = Helper.createMediaFile(file_name);

        if (pictureFile == null) {
            onHandleImageListener.errorStoringImage();
            return null;
        }

        try {

            FileOutputStream fos = new FileOutputStream(pictureFile);
            image.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.close();

            if (pictureFile.length() < 1024) {
                Log.i(TAG, "storeImage: Final File Size " + pictureFile.length());
                return null;
            }


            return pictureFile.getAbsolutePath();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
           // LocadApp.getCrashlyticsLogger().recordException(e);
        } catch (IOException e) {
            e.printStackTrace();
           // LocadApp.getCrashlyticsLogger().recordException(e);
        }
        return null;
    }

    public static void createOptions(final String dialogTitle, final Context context, final SwitchCompat aSwitch, final JSONArray rawDataJsonArray, final JSONArray currentJSONArray, final OnUpdateClicked onUpdateClicked) {
        final JSONArray[] dataJsonArray = {currentJSONArray};

        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                    try {

                        final Dialog dialog = new Dialog(context);

                        dialog.setCanceledOnTouchOutside(false);
                        dialog.setContentView(R.layout.dialog_problems);
                        dialog.setTitle(dialogTitle);
                        final LinearLayout linearLayout = dialog.findViewById(R.id.checkBoxesLinearLayout);

                        final LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT);


                        for (int i = 0; i < rawDataJsonArray.length(); i++) {
                            final JSONObject problemsRawJsonObject = rawDataJsonArray.getJSONObject(i);
                            SwitchCompat aSwitch = new SwitchCompat(context);
                            final String title = problemsRawJsonObject.getString("title");
                            aSwitch.setText(title);
                            aSwitch.setLayoutParams(layoutParams);
                            final LinearLayout linearLayout1 = new LinearLayout(context);
                            linearLayout1.setOrientation(LinearLayout.VERTICAL);
                            linearLayout1.setLayoutParams(layoutParams);
                            linearLayout.addView(aSwitch);
                            linearLayout.addView(linearLayout1);
                            aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                @Override
                                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                    if (isChecked) {
                                        try {

                                            String sub_dataType = problemsRawJsonObject.getString("type");
                                            if (sub_dataType.equals("checkbox")) {
                                                for (int j = 0; j < problemsRawJsonObject.getJSONArray("sub_data").length(); j++) {
                                                    String sub_dataValue = problemsRawJsonObject.getJSONArray("sub_data").getString(j);
                                                    CheckBox checkBox = new CheckBox(context);
                                                    checkBox.setLayoutParams(layoutParams);
                                                    checkBox.setText(sub_dataValue);
                                                    for (int k = 0; k < dataJsonArray[0].length(); k++) {
                                                        if (checkBox.getText().equals(dataJsonArray[0].getJSONObject(k).names().getString(0))) {
                                                            checkBox.setChecked(true);
                                                        }
                                                    }

                                                    checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                                        @Override
                                                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                                            try {
                                                                if (isChecked) {
                                                                    dataJsonArray[0].put(new JSONObject().put(buttonView.getText().toString(), true));
                                                                } else {
                                                                    for (int k = 0; k < dataJsonArray[0].length(); k++) {
                                                                        try {
                                                                            dataJsonArray[0].getJSONObject(k).getString(buttonView.getText().toString());
                                                                            dataJsonArray[0] = removeJSONArray(dataJsonArray[0], k);
                                                                        } catch (JSONException ignored) {
                                                                        }
                                                                    }
                                                                }
                                                            } catch (JSONException e) {
                                                                e.printStackTrace();
                                                               // LocadApp.getCrashlyticsLogger().recordException(e);
                                                            }
                                                        }
                                                    });
                                                    linearLayout1.addView(checkBox);
                                                }
                                            }

                                            if (sub_dataType.equals("radio")) {
                                                RadioGroup radioGroup = new RadioGroup(context);
                                                radioGroup.setLayoutParams(layoutParams);
                                                for (int j = 0; j < problemsRawJsonObject.getJSONArray("sub_data").length(); j++) {
                                                    String sub_dataValue = problemsRawJsonObject.getJSONArray("sub_data").getString(j);
                                                    RadioButton radioButton = new RadioButton(context);
                                                    radioButton.setLayoutParams(layoutParams);
                                                    radioButton.setText(sub_dataValue);
                                                    for (int k = 0; k < dataJsonArray[0].length(); k++) {
                                                        if (dataJsonArray[0].getJSONObject(k).has(title)) {
                                                            if (dataJsonArray[0].getJSONObject(k).getString(title).equals(sub_dataValue)) {
                                                                radioButton.setChecked(true);
                                                            }
                                                        }
                                                    }
                                                    radioGroup.addView(radioButton);
                                                }


                                                radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                                                    @Override
                                                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                                                        RadioButton radioButton = linearLayout1.findViewById(group.getCheckedRadioButtonId());
                                                        try {
                                                            for (int k = 0; k < dataJsonArray[0].length(); k++) {
                                                                try {
                                                                    JSONObject radioJsonObject = dataJsonArray[0].getJSONObject(k);
                                                                    if (radioJsonObject.has(title)) {
                                                                        dataJsonArray[0] = removeJSONArray(dataJsonArray[0], k);
                                                                    }
                                                                } catch (JSONException e) {
                                                                    e.printStackTrace();
                                                                   // LocadApp.getCrashlyticsLogger().recordException(e);
                                                                }
                                                            }
                                                            dataJsonArray[0].put(new JSONObject().put(title, radioButton.getText().toString()));

                                                        } catch (JSONException e) {
                                                            e.printStackTrace();
                                                           // LocadApp.getCrashlyticsLogger().recordException(e);
                                                        }
                                                    }
                                                });
                                                linearLayout1.addView(radioGroup);
                                            }

                                            if (sub_dataType.equals("input")) {
                                                final EditText editText = new EditText(context);
                                                editText.setLayoutParams(layoutParams);
                                                editText.setHint("Enter details");


                                                for (int k = 0; k < dataJsonArray[0].length(); k++) {
                                                    if (dataJsonArray[0].getJSONObject(k).has(title)) {
                                                        editText.setText(dataJsonArray[0].getJSONObject(k).getString(title));
                                                    }
                                                }

                                                editText.addTextChangedListener(new TextWatcher() {
                                                    @Override
                                                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                                    }

                                                    @Override
                                                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                                                        if (!s.toString().equals("")) {
                                                            try {
                                                                for (int k = 0; k < dataJsonArray[0].length(); k++) {
                                                                    JSONObject inputJsonObjects = dataJsonArray[0].getJSONObject(k);
                                                                    if (inputJsonObjects.has(title)) {
                                                                        dataJsonArray[0] = removeJSONArray(dataJsonArray[0], k);
                                                                    }
                                                                }
                                                                dataJsonArray[0].put(new JSONObject().put(title, s));
                                                            } catch (JSONException e) {
                                                                e.printStackTrace();
                                                               // LocadApp.getCrashlyticsLogger().recordException(e);
                                                            }
                                                        }
                                                    }

                                                    @Override
                                                    public void afterTextChanged(Editable s) {

                                                    }
                                                });

                                                linearLayout1.addView(editText);
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                           // LocadApp.getCrashlyticsLogger().recordException(e);
                                        }
                                    } else {
                                        linearLayout1.removeAllViews();
                                    }
                                }
                            });
                        }
                        Button cancelButton = dialog.findViewById(R.id.cancel_button);
                        cancelButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                                aSwitch.setChecked(false);
                            }
                        });

                        Button problemsUpdateButton = dialog.findViewById(R.id.update_button);
                        problemsUpdateButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Log.i("problems", "onCreate: " + dataJsonArray[0].toString());
                                onUpdateClicked.updateJSONArray(dataJsonArray[0]);
                                aSwitch.setChecked(true);
                                dialog.dismiss();
                            }
                        });

                        try {
                            dialog.show();
                        } catch (Exception e) {
                            e.printStackTrace();
                          //  LocadApp.getCrashlyticsLogger().recordException(e);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                       // LocadApp.getCrashlyticsLogger().recordException(e);
                    }
                }
            }
        });
//        return returnJsonArray[0];
    }

    public static Bitmap ProcessingBitmapV2(OnHandleImageListener onHandleImageListener, String seal_position, String stamp_position, Bitmap withoutStampPhoto, String stamp_string, String location_time, String site_lat, String site_lng, String lat, String lng, JSONObject siteImageColorsJsonObject) {
        Bitmap stampedPhoto;

        try {

            //Getting photo config of without stamp photo
            Bitmap.Config config = withoutStampPhoto.getConfig();
            //If configuration not present assigning default config
            if (config == null) {
                config = Bitmap.Config.RGB_565;
            }

            //Getting width and height of without stamp photo
            int finalPhotoWidth = withoutStampPhoto.getWidth();

            StampingConfig stampingConfig = new StampingConfig(finalPhotoWidth);

            int finalPhotoHeight = withoutStampPhoto.getHeight() + stampingConfig.getStampHeight();

            //Creating empty bitmap of stamped photo with the configuration of without stamped photo
            stampedPhoto = Bitmap.createBitmap(finalPhotoWidth, finalPhotoHeight, config);
            //Creating canvas with empty bitmap so that seal and stamp be added
            Canvas canvas = new Canvas(stampedPhoto);


            switch (stamp_position) {
                case "bottom": {
                    canvas.drawBitmap(withoutStampPhoto, 0, 0, null);
                    withoutStampPhoto.recycle();

                    Bitmap textTimeStamp = getTextStamp(onHandleImageListener, stampingConfig, stamp_string, location_time, site_lat, site_lng, lat, lng, siteImageColorsJsonObject);
                    canvas.drawBitmap(textTimeStamp, 0, finalPhotoHeight - stampingConfig.getStampHeight(), null);
                    textTimeStamp.recycle();

                    Bitmap logoBitmap = getLogo(stampingConfig);
                    switch (seal_position) {
                        case "top-right": {
                            canvas.drawBitmap(logoBitmap, finalPhotoWidth - stampingConfig.getSealHorizontalPadding(), stampingConfig.getSealVerticalPadding(), null);
                        }
                        break;
                        case "top-left": {
                            canvas.drawBitmap(logoBitmap, stampingConfig.getSealHorizontalPadding() - logoBitmap.getWidth(), stampingConfig.getSealVerticalPadding(), null);
                        }
                        break;
                        case "bottom-left": {
                            canvas.drawBitmap(logoBitmap, stampingConfig.getSealHorizontalPadding() - logoBitmap.getWidth(), finalPhotoHeight - stampingConfig.getSealVerticalPadding() - (2 * stampingConfig.getStampHeight()), null);
                        }
                        break;
                        case "bottom-right": {
                            canvas.drawBitmap(logoBitmap, finalPhotoWidth - stampingConfig.getSealHorizontalPadding(), finalPhotoHeight - stampingConfig.getSealVerticalPadding() - (2 * stampingConfig.getStampHeight()), null);
                        }
                        break;
                    }
                    logoBitmap.recycle();
                }
                break;
                case "top": {
                    canvas.drawBitmap(withoutStampPhoto, 0, stampingConfig.getStampHeight(), null);
                    withoutStampPhoto.recycle();

                    Bitmap textTimeStamp = getTextStamp(onHandleImageListener, stampingConfig, stamp_string, location_time, site_lat, site_lng, lat, lng, siteImageColorsJsonObject);
                    canvas.drawBitmap(textTimeStamp, 0, 0, null);
                    textTimeStamp.recycle();

                    Bitmap logoBitmap = getLogo(stampingConfig);
                    switch (seal_position) {
                        case "top-right": {
                            canvas.drawBitmap(logoBitmap, finalPhotoWidth - stampingConfig.getSealHorizontalPadding(), stampingConfig.getSealVerticalPadding() + stampingConfig.getStampHeight(), null);
                        }
                        break;
                        case "top-left": {
                            canvas.drawBitmap(logoBitmap, stampingConfig.getSealHorizontalPadding() - logoBitmap.getWidth(), stampingConfig.getSealVerticalPadding() + stampingConfig.getStampHeight(), null);
                        }
                        break;
                        case "bottom-left": {
                            canvas.drawBitmap(logoBitmap, stampingConfig.getSealHorizontalPadding() - logoBitmap.getWidth(), finalPhotoHeight - stampingConfig.getSealVerticalPadding() - (stampingConfig.getStampHeight()), null);
                        }
                        break;
                        case "bottom-right": {
                            canvas.drawBitmap(logoBitmap, finalPhotoWidth - stampingConfig.getSealHorizontalPadding(), finalPhotoHeight - stampingConfig.getSealVerticalPadding() - (stampingConfig.getStampHeight()), null);
                        }
                        break;
                    }
                    logoBitmap.recycle();
                }
                break;
            }

        } catch (Exception e) {
            e.printStackTrace();
            //LocadApp.getCrashlyticsLogger().recordException(e);
            return null;
        }

        return stampedPhoto;
    }

    private static Bitmap getLogo(StampingConfig stampingConfig) {
        Bitmap logoBitmap;
        ImageView imageView = new ImageView(Pages_Fragment.getconet());
        imageView.setImageDrawable(ContextCompat.getDrawable(Pages_Fragment.getconet(), R.drawable.book));
        BitmapDrawable bitmapDrawable = (BitmapDrawable) imageView.getDrawable();
        imageView.setImageDrawable(null);
        Bitmap tempBitmap = bitmapDrawable.getBitmap();
        logoBitmap = Bitmap.createScaledBitmap(tempBitmap, tempBitmap.getWidth() / stampingConfig.getSeal_scale(), tempBitmap.getHeight() / stampingConfig.getSeal_scale(), false);
        return logoBitmap;
    }

    private static Bitmap getTextStamp(OnHandleImageListener onHandleImageListener, StampingConfig stampingConfig, String stamp_string, String location_time, String site_lat, String site_lng, String lat, String lng, JSONObject siteImageColorsJsonObject) throws JSONException {

        Bitmap original_timeStampBitmap;

        TextView textView = new TextView(Pages_Fragment.getconet());
        textView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        textView.setText(stamp_string);
        textView.setTypeface(Typeface.create("sans-serif", Typeface.NORMAL));

        textView.setTextColor(Color.WHITE);

        int right_colorColor;
        int wrong_colorColor;

        // Log.i(TAG, "getTextStamp: " + siteImageColorsJsonObject.toString());

        // right_colorColor = Color.parseColor(siteImageColorsJsonObject.getString("right_color"));
        //wrong_colorColor = Color.parseColor(siteImageColorsJsonObject.getString("wrong_color"));
        textView.setTextColor(Color.parseColor("#000000"));

        //        right_colorColor = ColorUtils.setAlphaComponent(right_colorColor, 200);
//        wrong_colorColor = ColorUtils.setAlphaComponent(wrong_colorColor, 200);

        if (site_lat.equals("0.0")) {
            textView.setBackgroundColor(Color.parseColor("#ffffff"));
        } else {

            long location_time_long = Long.parseLong(location_time);
            long current_time_long = System.currentTimeMillis();
            long time_diff = current_time_long - location_time_long;

            float[] dist = new float[1];

            Location.distanceBetween(Double.parseDouble(site_lat)
                    , Double.parseDouble(site_lng)
                    , Double.parseDouble(lat)
                    , Double.parseDouble(lng), dist);

            Log.i(TAG, "ProcessingBitmap: time diff : " + time_diff + " , Distance : " + dist[0]);


            if (time_diff > 120000) {
                textView.setBackgroundColor(Color.parseColor("#FF0000"));
                onHandleImageListener.showLocationPrompt();
            } else {
                if (dist[0] > 100) {
                    textView.setBackgroundColor(Color.parseColor("#FF0000"));
                } else {
                    textView.setBackgroundColor(Color.parseColor("#0000FF"));
                }
            }
        }

        textView.layout(0, 0, stampingConfig.getFinalPhotoWidth(), stampingConfig.getStampHeight());
        textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, stampingConfig.getTextViewFontSize());
        textView.setDrawingCacheEnabled(true);
        original_timeStampBitmap = textView.getDrawingCache(true);

        //Scaling down textView Size for phones having problem
        if (original_timeStampBitmap == null) {
            textView.setDrawingCacheEnabled(false);
            textView.layout(0, 0, stampingConfig.getFinalPhotoWidth() / 2, stampingConfig.getStampHeight() / 2);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, stampingConfig.getTextViewFontSize());
            textView.setDrawingCacheEnabled(true);
            original_timeStampBitmap = textView.getDrawingCache(true);
        }

        return original_timeStampBitmap;
    }
    public static File createMediaFile(String file_name) {
//        Log.d(TAG, "createMediaFile() called with: file_name = [" + file_name + "]");

        File mediaFile;

        File appFolder = new File(Helper.getMediaPath());

//        Log.i(TAG, "createMediaFile: "+appFolder.getAbsolutePath()+" -- "+appFolder.exists());

        if (!appFolder.exists()) {
            if (!appFolder.mkdirs()) {
                appFolder = null;
                Log.e(TAG, "App folder not created");
            }
        }

        /*
         * Generate a unique file name for the requested file type if the app folder
         * exists or was created successfully
         */
        if (appFolder != null) {
            mediaFile = new File(appFolder, file_name);
        } else {
            // return a null file if the appFoler does not exist
            mediaFile = null;

            //FirebaseCrashlytics.getInstance().log("Error Creating Media File");

        }

        if (mediaFile != null) {
            Log.i(TAG, "createMediaFile: media file " + mediaFile.getAbsolutePath());
        }

        return mediaFile;
    }

}
