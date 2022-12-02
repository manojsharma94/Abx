package com.kot.mylibrary123;

import static android.os.Build.VERSION.SDK_INT;

import android.app.Activity;

import android.content.Context;

import android.content.Intent;
import android.content.pm.PackageManager;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.os.Build;

import android.os.Bundle;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import com.kot.mylibrary123.Fragmnt.Pages_Fragment;
import com.kot.mylibrary123.Taskclick.CameraViewActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Date;

import java.util.Locale;
import java.util.Objects;



public class Helper {

    public static final String ACTION_UPLOADING = "UPLOADING";
    public static final String TO_BE_UPLOADED = "to_be_uploaded";
    public static final String TIME_ZONE = "time_zone";
    public static final String SHOW_LAT_LNG = "show_lat_lng";

    //TODO change version before creating build

    /*public static final String INTERNAL_VERSION = "13.04.2022_release_" + BuildConfig.VERSION_NAME;

    private static final String base_logs_path = LocadApp.getApp()
            .getExternalFilesDir(null)
            .getAbsolutePath() + File.separator + "Locaudit_logs" + File.separator;
*/
 /*   public static final String LOG_PATH = base_logs_path + "logcat";
    public static final String TEMP_LOG_PATH = base_logs_path + "temp_logcat";
*/
    public static final String UPLOADER_TAG = "UPLOADER_TAG";

    public static final String COMPANY = "company";
    static final String MONITOR_ROLE = "monitor";
    private static final String TAG = "Helper";
    public static final String AWS_BUCKET_NAME = "AWS_BUCKET_NAME";
    private static final String AWS_IDENTITY_POOL_ID = "AWS_IDENTITY_POOL_ID";
    private static final String AWS_BUCKET_REGION = "AWS_BUCKET_REGION";
    private static final String LOGGER_TAG = "LOGGER_TAG";
    public static final String LANGUAGE = "LANGUAGE";

   /* public static String getApiUrl() {
        String api_url = "https://rouar8a4c4.execute-api.ap-south-1.amazonaws.com/beta/app/v4";

        String stored_api_url = new Prefs().getData("ip");
        if (!stored_api_url.equals("null")) {
            return stored_api_url;
        }

//        api_url = "http://locauditappdev.locad.net/app/v3";
//        api_url = "http://192.168.1.105:3000/app/v4";

        return api_url;
    }
*/
    private static boolean hasPermissions(Context context, String... permissions) {
        if (SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_DENIED) {
                    return false;
                }
            }
        }
        return true;
    }

  /*  public static void displayLocationSettingsRequest(final Context context) {
        GoogleApiClient googleApiClient = new GoogleApiClient.Builder(context)
                .addApi(LocationServices.API).build();
        googleApiClient.connect();

        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(10000 / 2);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);

        PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(@NonNull LocationSettingsResult result) {
                final Status status = result.getStatus();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        try {
                            status.startResolutionForResult((Activity) context, REQUEST_CHECK_SETTINGS);
                        } catch (IntentSender.SendIntentException ignored) {

                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        break;
                }
            }
        });
    }
*/

  /*  public static void autoUploadService() {
        try {
            Intent intent = new Intent(LocadApp.getContext(), TobeUploadedService.class);
            if (ConnectivityReceiver.isConnected()) {
                if (SDK_INT >= Build.VERSION_CODES.O) {
                    LocadApp.getContext().startForegroundService(intent);
                } else {
                    LocadApp.getContext().startService(intent);
                }
            } else {
                LocadApp.getContext().stopService(intent);
            }
        } catch (Exception e) {
            e.printStackTrace();
            LocadApp.getCrashlyticsLogger().recordException(e);
        }

    }
*/
    public static long unixGenerator(Long offset, String date, String frequencyHours) {

//        Log.d(TAG, "unixGenerator() called with: offset = [" + offset + "], date = [" + date + "]");

        DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH);
        long inputDateUnix;
        DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        long finalTaskUnix;
        try {
            inputDateUnix = df1.parse(date).getTime() + 19800000 + offset;

//            Log.i(TAG, "unixGenerator: inputDateUnix "+inputDateUnix);

            if (frequencyHours.equals("N.A.")) {
                inputDateUnix = inputDateUnix + (12 * 60 * 60 * 1000);
            }

            Date date1 = new Date(inputDateUnix);

            String time1 = df2.format(date1);

//            Log.i(TAG, "unixGenerator: time1 "+time1);

            Date date2 = df2.parse(time1);

            finalTaskUnix = date2.getTime();

//            TimeZone timeZone = Calendar.getInstance().getTimeZone();
//
//            boolean isDayLight = TimeZone.getDefault().inDaylightTime(date1);

//            if (isDayLight) {
//                finalTaskUnix = finalTaskUnix - timeZone.getDSTSavings();
//            }

//            Log.i(TAG, "unixGenerator: finalTaskUnix "+finalTaskUnix);

            return finalTaskUnix;
        } catch (ParseException e) {
            e.printStackTrace();
            //LocadApp.getCrashlyticsLogger().recordException(e);
        }
        return 0;
    }

    public static long todayUnixGenerator() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        c.set(Calendar.AM_PM, 0);

        long todayUnix = c.getTime().getTime();

//        TimeZone timeZone = Calendar.getInstance().getTimeZone();
//
//        boolean isDayLight = TimeZone.getDefault().inDaylightTime(c.getTime());

//        Log.i(TAG, "todayUnixGenerator: "+timeZone.inDaylightTime(c.getTime())+" -- "+timeZone.useDaylightTime()+" -- "+timeZone.getDSTSavings());

//        Log.i(TAG, "todayUnixGenerator: todayUnixBefore "+todayUnix);

//        if (isDayLight) {
//            todayUnix = todayUnix + timeZone.getDSTSavings();
//        }

//        Log.i(TAG, "todayUnixGenerator: todayUnixAfter "+todayUnix);

        return todayUnix;
    }

    @SuppressWarnings("unused")
    public static void longLogs(String veryLongString) {
        int maxLogSize = 1000;
        for (int i = 0; i <= veryLongString.length() / maxLogSize; i++) {
            int start = i * maxLogSize;
            int end = (i + 1) * maxLogSize;
            end = end > veryLongString.length() ? veryLongString.length() : end;
            if(BuildConfig.DEBUG){
                Log.i(TAG, veryLongString.substring(start, end));
            }
        }
    }

    /*public static void getCurrentLocation(final Context context, final OnLocationFetchFinished onLocationFetchFinished) {
        SmartLocation.with(context)
                .location(new LocationGooglePlayServicesWithFallbackProvider(context))
                .oneFix()
                .start(new OnLocationUpdatedListener() {
                    @Override
                    public void onLocationUpdated(Location location) {
                        onLocationFetchFinished.onFinished(String.valueOf(location.getLatitude()), String.valueOf(location.getLongitude()));
                    }
                });
    }*/

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

           // FirebaseCrashlytics.getInstance().log("Error Creating Media File");

        }

        if (mediaFile != null) {
            Log.i(TAG, "createMediaFile: media file " + mediaFile.getAbsolutePath());
        }

        return mediaFile;
    }

    public static String getMediaPath() {

        return Objects.requireNonNull(Pages_Fragment.getconet()
                .getExternalFilesDir(null))
                .getAbsolutePath() + File.separator + "Locaudit_Pictures" + File.separator;
    }

    public static JSONArray removeJSONArray(JSONArray jarray, int pos) {
        JSONArray Njarray = new JSONArray();
        try {
            for (int i = 0; i < jarray.length(); i++) {
                if (i != pos)
                    Njarray.put(jarray.get(i));
            }
        } catch (JSONException e) {
            e.printStackTrace();
           // LocadApp.getCrashlyticsLogger().recordException(e);
        }
        return Njarray;
    }

    public static void checkPermissions(Context context) {
        String[] PERMISSIONS = {android.Manifest.permission.CAMERA,
                android.Manifest.permission.ACCESS_COARSE_LOCATION,
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                android.Manifest.permission.READ_EXTERNAL_STORAGE,
                android.Manifest.permission.RECORD_AUDIO,
                android.Manifest.permission.READ_PHONE_STATE};

        if (!hasPermissions(context, PERMISSIONS)) {
            int PERMISSION_ALL = 1;
            ActivityCompat.requestPermissions((Activity) context, PERMISSIONS, PERMISSION_ALL);
        } else {
          //  displayLocationSettingsRequest(context);
        }
    }

   /* public static void signOut(final Context context) {
        String count = new ToBeUploadedPrefs().getTobeUploadedCount();
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        if (count.equals("0")) {
            builder.setMessage(R.string.sign_out_warning);
            builder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            builder.setNegativeButton("SignOut", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    try {
                        String email = new JSONObject(new LoginPrefs().getData("userdata")).getString("email");
                        FirebaseMessaging.getInstance().unsubscribeFromTopic(email.replace("@", "_at_"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                        LocadApp.getCrashlyticsLogger().recordException(e);
                    }

                    new SiteDetailsPrefs(context).clearAllData();
                    new TaskPrefs(context).clearAllData();
                    new ToBeUploadedPrefs().clearAllData();
                    new InventorySitesDataPrefs(context).clearAllData();
                    new SitesLocationsPrefs(context).clearAllData();
                    new LoginPrefs().clearAllData();
                    new ActiveCompletePrefs(context).clearAllData();
                    new Prefs().clearAllData();

                    Activity activity = (Activity) context;
                    activity.startActivity(new Intent(context, Splash.class));
                    activity.finish();
                }
            });

        } else {
            builder.setMessage(R.string.items_in_to_be_uploaded);
            builder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            builder.setNegativeButton("Upload", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    autoUploadService();
                }
            });
        }

        try {
            builder.create().show();
        } catch (Exception e) {
            e.printStackTrace();
            LocadApp.getCrashlyticsLogger().recordException(e);
        }
    }*/



    public static String generateStartAndEndDate(String unix, String frequencyHours) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        long lognUnix = Long.parseLong(unix);
        if (frequencyHours.equals("N.A.")) {
            lognUnix = lognUnix + (12 * 60 * 60 * 1000);
        }
        Date date = new Date(lognUnix);
        return formatter.format(date);
    }

    /*public static void chooseImageCamera(Context context, Bundle bundle) {

        Activity activity = (Activity) context;

        Prefs prefs = new Prefs();

        Intent intent;

        if (Prefs.PHONE_CAMERA.equals(prefs.getData(Prefs.CHOOSEN_CAMERA))) {
            intent = new Intent(context, EmptyCameraActivity.class);
        } else {
            intent = new Intent(context, CameraViewActivity.class);
        }

        JSONObject dataJsonObject = new JSONObject();

        try {
            dataJsonObject.put("camera_type", prefs.getData(Prefs.CHOOSEN_CAMERA));
            dataJsonObject.put("format", "image");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        analyticsLogger("Task Click Prepare", "", dataJsonObject.toString());

        bundle.putString("media_format", Constants.IMAGE);
        intent.putExtra(Constants.BUNDLE_EXTRA, bundle);
        activity.startActivityForResult(intent, Constants.REQUEST_CODE);
    }
*/

    public static Bitmap compressBitmap(Bitmap original, int camera_quality) {
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            original.compress(Bitmap.CompressFormat.JPEG, camera_quality, out);
            original.recycle();
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(out.toByteArray());
            out.flush();
            out.close();
            original = BitmapFactory.decodeStream(byteArrayInputStream);
            byteArrayInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
            //LocadApp.getCrashlyticsLogger().recordException(e);
        }

        return original;
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (String aChildren : children) {
                boolean success = deleteDir(new File(dir, aChildren));
                if (!success) {
                    return false;
                }
            }
        }

        return dir != null && dir.delete();
    }





    public static void chooseImageCamera(Context context, Bundle bundle) {

        Activity activity = (Activity) context;



        Intent intent;

       /* if (Prefs.PHONE_CAMERA.equals(prefs.getData(Prefs.CHOOSEN_CAMERA))) {
            intent = new Intent(context, EmptyCameraActivity.class);
        } else {*/
            intent = new Intent(context, CameraViewActivity.class);
        //}

        JSONObject dataJsonObject = new JSONObject();

        try {
            dataJsonObject.put("camera_type", "phone_camera");
            dataJsonObject.put("format", "image");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //analyticsLogger("Task Click Prepare", "", dataJsonObject.toString());

        bundle.putString("media_format", Constants.IMAGE);
        intent.putExtra(Constants.BUNDLE_EXTRA, bundle);
        activity.startActivityForResult(intent, Constants.REQUEST_CODE);
    }


}
