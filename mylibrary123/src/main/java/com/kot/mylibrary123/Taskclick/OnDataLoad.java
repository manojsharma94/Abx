package com.kot.mylibrary123.Taskclick;

import org.json.JSONArray;
import org.json.JSONObject;

interface OnDataLoad {
    void showSiteCode(String site_code);

    void showCampaignName(String campaign_name);

    void showLocation(String lat_lng);

    void setUploadedByPrefrencesData(JSONArray sitePrefrencesJsonArray, JSONObject siteImageColorsJsonObject, JSONArray siteImageHeadersJsonArray);
}
