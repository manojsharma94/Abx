package com.kot.mylibrary123.Taskclick;

interface OnImageHandled {
    void showError(String error);

    void showLocationPrompt();

    void showProgress();

    void hideProgress();

    void addStampedImageToArray(String processedTask);
}
