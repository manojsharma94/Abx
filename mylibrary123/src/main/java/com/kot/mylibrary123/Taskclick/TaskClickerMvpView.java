package com.kot.mylibrary123.Taskclick;

import org.json.JSONArray;
import org.json.JSONObject;

public interface TaskClickerMvpView {
    void showSiteCode(String site_code);

    void showCampaignName(String campaign_name);

    void showLocation(String lat_lng);

    void showError(String error);

    void showLocationPrompt();

    void showProgress();

    void hideProgress();

    void addToArray(String processedTask);

    void openMainActivity();

    void setUploadedByPrefrencesData(JSONArray sitePrefrencesJsonArray, JSONObject siteImageColorsJsonObject, JSONArray siteImageHeadersJsonArray);

}
