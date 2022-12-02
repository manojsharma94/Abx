package com.kot.mylibrary123.Taskclick;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TaskClickerInteractor implements TaskClickerMvpInteractor {


    @Override
    public void loadData(Bundle bundle, OnDataLoad onDataLoad) {
        String monitor_type = bundle.getString("monitor_type", "");
        String site_code = "0060";
        String campaign_name = "Mumbai March 2020";
        String lat = "28.54341";
        String lng = "77.2152";

        String siteCode = bundle.getString("siteCode");
        String timeTask = "1669660200000";
        String campaignId = "5e5ca7d915de0427607a659d";

        onDataLoad.showSiteCode(site_code);
        onDataLoad.showCampaignName(campaign_name);
        onDataLoad.showLocation(lat + ", " + lng);

        JSONArray sitePreferencesJsonArray = new JSONArray();
        JSONObject siteImageColorsJsonObject = new JSONObject();
        JSONArray siteImageHeadersJsonArray = new JSONArray();


        try {
            sitePreferencesJsonArray = new JSONArray("[{\"sub_data\":[\"0-5m\",\"6-10m\",\"11-20m\",\"20m+\"],\"type\":\"radio\",\"title\":\"Height\"},{\"sub_data\":[\"None\",\"1-2 sites\",\"3-5 sites\",\"6+ sites\"],\"type\":\"radio\",\"title\":\"Clutter\"},{\"sub_data\":[\"None- 0% obstructed\",\"Slight- 0-20% obstructed\",\"Moderate- 20-50% obstructed\",\"Severe- over 50% obstructed\"],\"type\":\"radio\",\"title\":\"Obstruction\"},{\"sub_data\":[\"None\",\"Slight\",\"Moderate\",\"Severe\"],\"type\":\"radio\",\"title\":\"Visual Clutter\"},{\"sub_data\":[\"None (0-5m)\",\"Slight (5m-10m)\",\"Moderate (10-20m)\",\"Severe (20m+)\"],\"type\":\"radio\",\"title\":\"Eccentricity\"},{\"sub_data\":[\"Single Lane\",\"2 Lane\",\"3 Lane\"],\"type\":\"radio\",\"title\":\"Traffic - Road Type\"},{\"sub_data\":[\"Left Hand Side\",\"Right Hand Side\",\"HeadOn\"],\"type\":\"radio\",\"title\":\"Traffic - Position\"},{\"sub_data\":[\"Slow\",\"Medium\",\"Fast\"],\"type\":\"radio\",\"title\":\"Traffic - Speed\"},{\"sub_data\":[\"0 - 50 m\",\"50 - 100 m\",\"100 - 150 m\",\"200+ m\"],\"type\":\"radio\",\"title\":\"Traffic - Visible Distance\"}]},{\"problems\":[{\"sub_data\":[\"Vandalised\",\"Wrinkled\",\"Poster Damaged\",\"Poster Stolen\",\"Cable Stolen\",\"Roof Damaged\",\"Faculty Meter\",\"Scroller Fault\",\"Not Found\",\"Not Mounted\"],\"type\":\"checkbox\",\"title\":\"Installation Problem\"},{\"sub_data\":[\"Partially Lit\",\"Non Lit\"],\"type\":\"checkbox\",\"title\":\"Lighting Problem\"},{\"sub_data\":[\"Bill Paste\\/Graffiti\",\"Others\"],\"type\":\"checkbox\",\"title\":\"Obstruction Problem\"},{\"sub_data\":[],\"type\":\"input\",\"title\":\"Comments\"}]}]");
            siteImageColorsJsonObject = new JSONObject("{\"right_color\":\"#009ce0\",\"wrong_color\":\"#b21b07\",\"font_color\":\"#ffffff\"}");
            siteImageHeadersJsonArray = new JSONArray("[\"location\",\"locality\"]");

        } catch (JSONException e) {
            e.printStackTrace();
            //LocadApp.getCrashlyticsLogger().recordException(e);
        }

        /*try {
            String siteDetailsSitePreferenceString = "{\"other_preferences\":{}}";

            if (!siteDetailsSitePreferenceString.equals("null") && monitor_type.equals("normal_monitor")) {

                Log.i(TAG, "loadData: " + campaignId + " -- " + site_code);

                JSONObject siteDetailsJsonObject = new JSONObject(siteDetailsPrefs.getData(campaignId + "___" + site_code));

                String uploaded_by = siteDetailsJsonObject.optString(Constants.UPLOADED_BY);

                JSONObject siteDetailsPrefJsonObject = new JSONObject(siteDetailsSitePreferenceString);
                JSONObject otherPreferencesJsonObject = siteDetailsPrefJsonObject.optJSONObject("other_preferences");

                JSONObject uploadedBySiteJsonObject = null;

                if (otherPreferencesJsonObject != null) {
                    uploadedBySiteJsonObject = otherPreferencesJsonObject.optJSONObject(uploaded_by);
                }

                if (uploadedBySiteJsonObject != null) {
                    sitePreferencesJsonArray = uploadedBySiteJsonObject.optJSONArray("preferences");
                    siteImageColorsJsonObject = uploadedBySiteJsonObject.optJSONObject("image_colors");
                    siteImageHeadersJsonArray = uploadedBySiteJsonObject.optJSONArray("image_time_stamp");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            LocadApp.getCrashlyticsLogger().recordException(e);
        }*/

        onDataLoad.setUploadedByPrefrencesData(sitePreferencesJsonArray, siteImageColorsJsonObject, siteImageHeadersJsonArray);

        try {
            String taskClickData = new JSONObject()
                    .put("site_code", site_code)
                    .put("campaign_id", campaignId)
                    .put("task_unix", timeTask)
                    .toString();

            //analyticsLogger("Attempting Task Click", "", taskClickData);

        } catch (Exception e) {
            e.printStackTrace();
           // LocadApp.getCrashlyticsLogger().recordException(e);
        }


    }

    private static final String TAG = "TaskClickerInteractor";

    @Override
    public void handleDoneTask(Bundle bundle,String format, JSONObject siteImageColorsJsonObject, JSONArray siteImageHeadersJsonArray, final OnImageHandled onImageHandled) {

        //Log.i(TAG, "handleDoneTask: chosen color data " + siteImageColorsJsonObject.toString());

        final String clickedAt = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.ENGLISH).format(new Date());
        final String lat = "28.54345";
        final String lng = "77.21521";
        String location_time = "1669708908347";
        String file_name = bundle.getString("file_name");
        final String path = bundle.getString("file_path");
       /* final String path = bundle.getString("file_path");
        int camera_quality = bundle.getInt("camera_quality");
        String site_lat = String.valueOf(bundle.getDouble("lat"));
        String site_lng = String.valueOf(bundle.getDouble("lng"));
        String file_name = bundle.getString("file_name");
        final String site_code = bundle.getString("siteCode");
        final String campaign_id = bundle.getString("campaignId");
        final String task_time_stamp = bundle.getString("timeTask");
        final String site_id = bundle.getString("siteId");
        String sensorData = bundle.getString("sensorData");

        if (sensorData == null) {
            sensorData = "{}";
        }

        final String finalSensorData = sensorData;
        final String monitor_type = bundle.getString("monitor_type", "");
        final String campaign_name = bundle.getString("campaign_name", "");

        JSONObject dataJsonObject = new JSONObject();

        try {
            dataJsonObject.put("format", format);
            dataJsonObject.put("site_code", site_code);
            dataJsonObject.put("campaign_id", campaign_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }*/


        //analyticsLogger("Task Click End", "", dataJsonObject.toString());

        StringBuilder time_stamp_string = new StringBuilder();

        /*if (monitor_type.equals("normal_monitor")) {
            try {
                JSONObject siteDetailsJsonObject = new JSONObject(siteDetailsPrefs.getData(campaign_id + "___" + site_code));

                if (!siteDetailsJsonObject.isNull(DYNAMIC_DATA)) {
                    JSONObject dynamicDataJsonObject = siteDetailsJsonObject.getJSONObject(DYNAMIC_DATA);
//                    Log.i(TAG, "handleDoneTask: "+dynamicDataJsonObject.toString());

                    for (int i = 0; i < siteImageHeadersJsonArray.length(); i++) {
                        String header = siteImageHeadersJsonArray.optString(i);

                        Log.i(TAG, "handleDoneTask header : " + i + " -- " + header);

                        if (dynamicDataJsonObject.has(header)) {


                            time_stamp_string.append(dynamicDataJsonObject.optString(header));
                            if (i != siteImageHeadersJsonArray.length() - 1) {
                                time_stamp_string.append(" | ");
                            }
                        }
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
                LocadApp.getCrashlyticsLogger().recordException(e);
            }
        }*/

        /*if (monitor_type.equals("qr_monitor")) {
            time_stamp_string.append(bundle.getString("time_stamp_string", ""));
        }*/

        if (format.equals("image")) {
            ExecutorService service= Executors.newSingleThreadExecutor();
            service.submit( new HandleImage(clickedAt, time_stamp_string.toString(), lat, lng, path, 100, location_time, "1.000", "2.00", file_name, siteImageColorsJsonObject, new OnHandleImageListener() {

                @Override
                public void showProgress() {

                    onImageHandled.showProgress();
                }

                @Override
                public void imageGeneratedSuccessfully(String path) {

                   /* ProcessedTask processedTask = new ProcessedTask(
                            path, clickedAt, "image", lat, lng, site_code,
                            campaign_id, task_time_stamp, site_id, finalSensorData, monitor_type, campaign_name);

                    JSONObject jsonObject = new JSONObject();

                    try {
                        jsonObject.put("time_stamp", processedTask.getTask_time_stamp());
                        jsonObject.put("clicked_at", processedTask.getClickedAt());
                        jsonObject.put("campaign_id", processedTask.getCampaign_id());
                        jsonObject.put("site_code", processedTask.getSite_code());
                        jsonObject.put("format", "image");
                    } catch (JSONException e) {
                        e.printStackTrace();
                       // LocadApp.getCrashlyticsLogger().recordException(e);
                    }

                   // analyticsLogger("Task Done", "", jsonObject.toString());

                    onImageHandled.addStampedImageToArray(processedTask);*/
                    onImageHandled.addStampedImageToArray(path);

                }

                @Override
                public void errorGeneratingImage(String original_image_path) {
             /*       JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("Stamping Status", "Error Generating Image");
                        jsonObject.put("site_code", site_code);
                        jsonObject.put("campaign_id", campaign_id);
                        jsonObject.put("task_time_stamp", task_time_stamp);
                        jsonObject.put("clickedAt", clickedAt);
                        if (path != null) {
                            jsonObject.put("path", original_image_path);
                        }
                      //  analyticsLogger("Stamping Error", "", jsonObject.toString());
                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }
                    onImageHandled.showError("Error Generating Image");*/
                }

                @Override
                public void errorStoringImage() {
                    /*JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("Stamping Status", "Error Storing Image");
                        jsonObject.put("site_code", site_code);
                        jsonObject.put("campaign_id", campaign_id);
                        jsonObject.put("task_time_stamp", task_time_stamp);
                        jsonObject.put("clickedAt", clickedAt);
                       // analyticsLogger("Stamping Error", "", jsonObject.toString());
                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }
                    onImageHandled.showError("Error Storing Image");*/
                }

                @Override
                public void errorStampingImage() {
                   /* JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("Stamping Status", "Error in image stamping");
                        jsonObject.put("site_code", site_code);
                        jsonObject.put("campaign_id", campaign_id);
                        jsonObject.put("task_time_stamp", task_time_stamp);
                        jsonObject.put("clickedAt", clickedAt);
                        //analyticsLogger("Stamping Error", "", jsonObject.toString());
                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }
                    onImageHandled.showError("Error Stamping Image");*/
                }

                @Override
                public void showLocationPrompt() {
                    onImageHandled.showLocationPrompt();
                }

                @Override
                public void hideProgress() {
                    onImageHandled.hideProgress();
                }

                @Override
                public void sendToTextTextReader(Bitmap originalImage) {


//                    //ML Kit Text Recognition
//
//                    InputImage inputImage = InputImage.fromBitmap(originalImage, 0);
//
//                    TextRecognizer textRecognizer = TextRecognition.getClient();
//
//                    Task<Text> textTask = textRecognizer.process(inputImage);
//
//                    textTask.addOnSuccessListener(new OnSuccessListener<Text>() {
//                        @Override
//                        public void onSuccess(Text text) {
//                            Log.d(TAG, "onSuccess() called with: text = [" + text.getText() + "]");
//                        }
//                    }).addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            Log.d(TAG, "onFailure() called with: e = [" + e + "]");
//                        }
//                    });

                }
            })/*.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR)*/);
                   }
        else {

          /*  ProcessedTask processedTask = new ProcessedTask(
                    path, clickedAt, format, lat, lng, site_code,
                    campaign_id, task_time_stamp, site_id, finalSensorData, monitor_type, campaign_name);


            JSONObject jsonObject = new JSONObject();

            try {
                jsonObject.put("time_stamp", processedTask.getTask_time_stamp());
                jsonObject.put("clicked_at", processedTask.getClickedAt());
                jsonObject.put("campaign_id", processedTask.getCampaign_id());
                jsonObject.put("site_code", processedTask.getSite_code());
                jsonObject.put("format", "video");
            } catch (JSONException e) {
                e.printStackTrace();
               // LocadApp.getCrashlyticsLogger().recordException(e);
            }*/

           // analyticsLogger("Task Done", "", jsonObject.toString());


           // onImageHandled.addStampedImageToArray(processedTask);


//            new HandleVideo(clickedAt, time_stamp_string.toString(),
//                    lat, lng, path, camera_quality,
//                    location_time, site_lat, site_lng, file_name,
//                    siteImageColorsJsonObject, new OnHandleVideoListener(){
//                @Override
//                public void success(String outputFilePath) {
//
//                    ProcessedTask processedTask = new ProcessedTask(
//                            outputFilePath, clickedAt, format, lat, lng, site_code,
//                            campaign_id, task_time_stamp, site_id, finalSensorData, monitor_type, campaign_name);
//
//                    onImageHandled.addStampedImageToArray(processedTask);
//                }
//
//                @Override
//                public void error(String path) {
//
//                    ProcessedTask processedTask = new ProcessedTask(
//                            path, clickedAt, format, lat, lng, site_code,
//                            campaign_id, task_time_stamp, site_id, finalSensorData, monitor_type, campaign_name);
//
//                    onImageHandled.addStampedImageToArray(processedTask);
//                }
//
//                @Override
//                public void showProgress() {
//                    onImageHandled.showProgress();
//                }
//
//                @Override
//                public void hideProgress() {
//                    onImageHandled.hideProgress();
//                }
//            }).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

        }
    }

    @Override
    public void prepareAndUpload(List<ProcessedTask> processedTaskList, JSONArray problemsJsonArray, JSONArray siteScoreJsonArray, OnDone onDone) {
        /*ToBeUploadedPrefs toBeUploadedPrefs = new ToBeUploadedPrefs();
        for (ProcessedTask processedTask : processedTaskList) {
            toBeUploadedPrefs.insertData(processedTask.getSite_code(), processedTask.getSite_id(), processedTask.getTask_time_stamp(),
                    processedTask.getPath(), problemsJsonArray.toString(), siteScoreJsonArray.toString(), processedTask.getFormat(),
                    processedTask.getLat(), processedTask.getLng(), processedTask.getCampaign_id(), processedTask.getClickedAt(),
                    processedTask.getFinalSensorData(), processedTask.getMonitor_type(), processedTask.getCampaign_name());


            JSONObject jsonObject = new JSONObject();

            try {
                jsonObject.put("task_time_stamp", processedTask.getTask_time_stamp());
                jsonObject.put("clicked_at", processedTask.getClickedAt());
                jsonObject.put("campaign_id", processedTask.getCampaign_id());
                jsonObject.put("site_code", processedTask.getSite_code());
            } catch (JSONException e) {
                e.printStackTrace();
               // LocadApp.getCrashlyticsLogger().recordException(e);
            }

            //analyticsLogger("Attempt Task Upload", "", "{}");

        }*/

        onDone.openMainActivity();

    }
}
