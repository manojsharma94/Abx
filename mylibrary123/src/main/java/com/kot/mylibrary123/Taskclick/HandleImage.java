package com.kot.mylibrary123.Taskclick;



import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.core.content.FileProvider;


import com.kot.mylibrary123.Fragmnt.Pages_Fragment;
import com.kot.mylibrary123.R;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;


public class HandleImage implements Runnable {
    private static final String TAG = "HandleImage";
    private final String clickedAt;
    private final String time_stamp_string;
    private final String lat;
    private final String lng;
    private final int camera_quality;
    private final String location_time;
    private final String site_lat;
    private final String site_lng;
    private final String original_file_name;
    private final JSONObject siteImageColorsJsonObject;
    private final OnHandleImageListener onHandleImageListener;

    private String path, original_image_path, stamped_image_path;
    private String s = "";
    private Bitmap originalImage;

    public HandleImage(String clickedAt, String time_stamp_string, String lat, String lng, String path, int camera_quality, String location_time, String site_lat, String site_lng, String original_file_name, JSONObject siteImageColorsJsonObject, OnHandleImageListener onHandleImageListener) {
        this.clickedAt = clickedAt;
        this.time_stamp_string = time_stamp_string;
        this.lat = lat;
        this.lng = lng;
        this.path = path;
        this.camera_quality = camera_quality;
        this.location_time = location_time;
        this.site_lat = site_lat;
        this.site_lng = site_lng;
        this.original_file_name = original_file_name;
        this.siteImageColorsJsonObject = siteImageColorsJsonObject;
        this.onHandleImageListener = onHandleImageListener;
    }

    private static Bitmap rotateImageIfRequired(Bitmap img, Uri selectedImage) throws IOException {

        InputStream input = Pages_Fragment.getconet().getContentResolver().openInputStream(selectedImage);
        ExifInterface ei;
        if (Build.VERSION.SDK_INT > 23)
            ei = new ExifInterface(input);
        else
            ei = new ExifInterface(selectedImage.getPath());

        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                return rotateImage(img, 90);
            case ExifInterface.ORIENTATION_ROTATE_180:
                return rotateImage(img, 180);
            case ExifInterface.ORIENTATION_ROTATE_270:
                return rotateImage(img, 270);
            default:
                return img;
        }
    }

    private static Bitmap rotateImage(Bitmap img, int degree) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        Bitmap rotatedImg = Bitmap.createBitmap(img, 0, 0, img.getWidth(), img.getHeight(), matrix, true);
        img.recycle();
        return rotatedImg;
    }

   /* @Override
    protected void onPreExecute() {
        super.onPreExecute();
        onHandleImageListener.showProgress();
    }

    @Override
    protected Boolean doInBackground(Void... voids) {


        if (loginPrefs.getData(SHOW_LAT_LNG).equals("false")) {
            s = "Time: " + clickedAt + "\n" + time_stamp_string;
        } else {
            s = "Time: " + clickedAt + " Lat : " + lat + " Lng : " + lng + "\n" + time_stamp_string;
        }

        if (path != null) {

            original_image_path = path;


            //Trying First Decoding
            try {
                originalImage = BitmapFactory.decodeFile(path);
            } catch (Exception e) {
                e.printStackTrace();
            }

            //If it fails trying second decoding
            if (originalImage == null) {
                try {
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inSampleSize = 2;
                    originalImage = BitmapFactory.decodeFile(path, options);
                } catch (Exception e) {
                    e.printStackTrace();
                    LocadApp.getCrashlyticsLogger().recordException(e);
                }
            } else {
                Log.i(TAG, "doInBackground: Decoding 1 Failed");
            }

            //If second fails trying third decoding
            if (originalImage == null) {
                try {
                    int i = 0;
                    while (originalImage == null && i < 10) {
                        originalImage = BitmapFactory.decodeFile(path);
                        i++;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    LocadApp.getCrashlyticsLogger().recordException(e);
                }
            } else {
                Log.i(TAG, "doInBackground: Decoding 2 Failed");
            }


        } else {
            Log.i(TAG, "doInBackground: path is null");
            onHandleImageListener.errorGeneratingImage(original_image_path);
        }


        if (originalImage != null) {

            onHandleImageListener.sendToTextTextReader(originalImage);

//            Bitmap compressedImage = Helper.compressBitmap(originalImage, camera_quality);

            //Now rotating image
            try {
                File imageFile = new File(path);
                Uri uri = FileProvider.getUriForFile(LocadApp.getContext(), LocadApp.getContext().getString(R.string.file_provider_authority), imageFile);
                originalImage = rotateImageIfRequired(originalImage, uri);

            } catch (Exception e) {
                e.printStackTrace();
                LocadApp.getCrashlyticsLogger().recordException(e);
            }

            try {
                String seal_position = loginPrefs.getSealPosition();
                String stamp_position = loginPrefs.getStampPosition();
                Bitmap bitmap = CameraHelper.ProcessingBitmapV2(onHandleImageListener, seal_position, stamp_position, originalImage, s, location_time, site_lat, site_lng, lat, lng, siteImageColorsJsonObject);
                originalImage.recycle();

                if (bitmap != null) {
                    stamped_image_path = CameraHelper.storeImage(onHandleImageListener, bitmap, original_file_name.replace("_un.jpg", ".jpg"));

                    try {
                        String dimensionJson = new JSONObject()
                                .put("width", bitmap.getWidth())
                                .put("height", bitmap.getHeight())
                                .put("size", bitmap.getByteCount())
                                .toString();

                        ElasticLoggerUtility.analyticsLogger("Stamped Image Dimensions", "", dimensionJson);

                    } catch (Exception e) {
                        e.printStackTrace();
                        LocadApp.getCrashlyticsLogger().recordException(e);
                    }

                    bitmap.recycle();
                    //Got zero byte image , setting original image to path
                    if (path == null) {
                        path = original_image_path;
                        //Error generating stamped image
                        onHandleImageListener.errorStampingImage();
                    }
                } else {
                    //Image not stamped working on backup solution
                    onHandleImageListener.errorStampingImage();
                }


            } catch (Exception e) {
                e.printStackTrace();
                LocadApp.getCrashlyticsLogger().recordException(e);
                //Image not stamped sending to analytics
                onHandleImageListener.errorStampingImage();
            }
        } else {
            Log.i(TAG, "doInBackground: original_image is null");
            onHandleImageListener.errorGeneratingImage(original_image_path);
        }

        return false;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        onHandleImageListener.hideProgress();

        if (originalImage != null && stamped_image_path != null) {
            onHandleImageListener.imageGeneratedSuccessfully(stamped_image_path);
        } else {
            onHandleImageListener.errorGeneratingImage(original_image_path);
        }
    }*/

    @Override
    public void run() {
        /**
         * on pre execute
         */

        onHandleImageListener.showProgress();
        /**
         * in backgorund
         */
        s = "Time: " + clickedAt + "\n" + time_stamp_string;
      /*  if (loginPrefs.getData(SHOW_LAT_LNG).equals("false")) {
            s = "Time: " + clickedAt + "\n" + time_stamp_string;
        } else {
            s = "Time: " + clickedAt + " Lat : " + lat + " Lng : " + lng + "\n" + time_stamp_string;
        }*/

        if (path != null) {

            original_image_path = path;


            //Trying First Decoding
            try {
                originalImage = BitmapFactory.decodeFile(path);
            } catch (Exception e) {
                e.printStackTrace();
            }

            //If it fails trying second decoding
            if (originalImage == null) {
                try {
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inSampleSize = 2;
                    originalImage = BitmapFactory.decodeFile(path, options);
                } catch (Exception e) {
                    e.printStackTrace();
                   // LocadApp.getCrashlyticsLogger().recordException(e);
                }
            } else {
                Log.i(TAG, "doInBackground: Decoding 1 Failed");
            }

            //If second fails trying third decoding
            if (originalImage == null) {
                try {
                    int i = 0;
                    while (originalImage == null && i < 10) {
                        originalImage = BitmapFactory.decodeFile(path);
                        i++;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                  //  LocadApp.getCrashlyticsLogger().recordException(e);
                }
            } else {
                Log.i(TAG, "doInBackground: Decoding 2 Failed");
            }


        } else {
            Log.i(TAG, "doInBackground: path is null");
            onHandleImageListener.errorGeneratingImage(original_image_path);
        }


        if (originalImage != null) {

            onHandleImageListener.sendToTextTextReader(originalImage);

//            Bitmap compressedImage = Helper.compressBitmap(originalImage, camera_quality);

            //Now rotating image
            try {
                File imageFile = new File(path);
                Uri uri = FileProvider.getUriForFile(Pages_Fragment.getconet(), "com.kot.mylibrary123.fileprovider", imageFile);
                originalImage = rotateImageIfRequired(originalImage, uri);

            } catch (Exception e) {
                e.printStackTrace();
               // LocadApp.getCrashlyticsLogger().recordException(e);
            }

            try {
                String seal_position = "top-right";
                String stamp_position = "bottom";
                Bitmap bitmap = CameraHelper.ProcessingBitmapV2(onHandleImageListener, seal_position, stamp_position, originalImage, s, location_time, site_lat, site_lng, lat, lng, siteImageColorsJsonObject);
                originalImage.recycle();

                if (bitmap != null) {
                    stamped_image_path = CameraHelper.storeImage(onHandleImageListener, bitmap, original_file_name.replace("_un.jpg", ".jpg"));

                    try {
                        String dimensionJson = new JSONObject()
                                .put("width", bitmap.getWidth())
                                .put("height", bitmap.getHeight())
                                .put("size", bitmap.getByteCount())
                                .toString();

                       // ElasticLoggerUtility.analyticsLogger("Stamped Image Dimensions", "", dimensionJson);

                    } catch (Exception e) {
                        e.printStackTrace();
                      //  LocadApp.getCrashlyticsLogger().recordException(e);
                    }

                    bitmap.recycle();
                    //Got zero byte image , setting original image to path
                    if (path == null) {
                        path = original_image_path;
                        //Error generating stamped image
                        onHandleImageListener.errorStampingImage();
                    }
                } else {
                    //Image not stamped working on backup solution
                    onHandleImageListener.errorStampingImage();
                }


            } catch (Exception e) {
                e.printStackTrace();
               // LocadApp.getCrashlyticsLogger().recordException(e);
                //Image not stamped sending to analytics
                onHandleImageListener.errorStampingImage();
            }
        } else {
            Log.i(TAG, "doInBackground: original_image is null");
            onHandleImageListener.errorGeneratingImage(original_image_path);
        }

        /**
         * post execute
         */
        onHandleImageListener.hideProgress();

        if (originalImage != null && stamped_image_path != null) {
            onHandleImageListener.imageGeneratedSuccessfully(stamped_image_path);
        } else {
            onHandleImageListener.errorGeneratingImage(original_image_path);
        }


    }
}
