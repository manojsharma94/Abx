package com.kot.mylibrary123.Taskclick;

import android.graphics.Bitmap;

public interface OnHandleImageListener {
    void showProgress();

    void imageGeneratedSuccessfully(String path);

    void errorGeneratingImage(String original_image_path);

    void errorStoringImage();

    void errorStampingImage();

    void showLocationPrompt();

    void hideProgress();

    void sendToTextTextReader(Bitmap originalImage);
}
