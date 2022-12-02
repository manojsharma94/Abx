package com.kot.mylibrary123.Taskclick;

import android.os.Bundle;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public interface TaskClickerMvpPresenter {
    void loadData(Bundle bundle);

    void handleDoneTask(Bundle bundle, String format, JSONObject siteImageColorsJsonObject, JSONArray siteImageHeadersJsonArray);

    void sendToUpload(List<ProcessedTask> processedTaskList, JSONArray problemsJsonArray, JSONArray siteScoreJsonArray);
}
