package com.kot.mylibrary123.Taskclick;

import android.os.Bundle;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

interface TaskClickerMvpInteractor {
    void loadData(Bundle bundle, OnDataLoad onDataLoad);

    void handleDoneTask( Bundle bundle,String format, JSONObject siteImageColorsJsonObject, JSONArray siteImageHeadersJsonArray, OnImageHandled onImageHandled);

    void prepareAndUpload(List<ProcessedTask> processedTaskList, JSONArray problemsJsonArray, JSONArray siteScoreJsonArray, OnDone onDone);
}
