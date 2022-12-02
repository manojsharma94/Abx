package com.kot.mylibrary123.Taskclick;

import android.os.Bundle;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class TaskClickerPresenter implements TaskClickerMvpPresenter {
    private final TaskClickerMvpView taskClickerMvpView;
    private final TaskClickerMvpInteractor taskClickerMvpInteractor;

    public TaskClickerPresenter(TaskClickerMvpView taskClickerMvpView, TaskClickerMvpInteractor taskClickerMvpInteractor) {
        this.taskClickerMvpView = taskClickerMvpView;
        this.taskClickerMvpInteractor = taskClickerMvpInteractor;
    }

    @Override
    public void loadData(Bundle bundle) {
        taskClickerMvpInteractor.loadData(bundle, new OnDataLoad() {

            @Override
            public void showSiteCode(String site_code) {
                taskClickerMvpView.showSiteCode(site_code);
            }

            @Override
            public void showCampaignName(String campaign_name) {
                taskClickerMvpView.showCampaignName(campaign_name);
            }

            @Override
            public void showLocation(String lat_lng) {
                taskClickerMvpView.showLocation(lat_lng);
            }

            @Override
            public void setUploadedByPrefrencesData(JSONArray sitePrefrencesJsonArray, JSONObject siteImageColorsJsonObject, JSONArray siteImageHeadersJsonArray) {
                taskClickerMvpView.setUploadedByPrefrencesData(sitePrefrencesJsonArray, siteImageColorsJsonObject, siteImageHeadersJsonArray);
            }
        });
    }

    @Override
    public void handleDoneTask(Bundle bundle, String format, JSONObject siteImageColorsJsonObject, JSONArray siteImageHeadersJsonArray) {
        taskClickerMvpInteractor.handleDoneTask( bundle,format, siteImageColorsJsonObject, siteImageHeadersJsonArray, new OnImageHandled() {

            @Override
            public void showError(String error) {
                taskClickerMvpView.showError(error);
            }

            @Override
            public void showLocationPrompt() {
                taskClickerMvpView.showLocationPrompt();
            }

            @Override
            public void showProgress() {

                taskClickerMvpView.showProgress();
            }

            @Override
            public void addStampedImageToArray(String processedTask) {
                taskClickerMvpView.addToArray(processedTask);
            }

            @Override
            public void hideProgress() {
                taskClickerMvpView.hideProgress();
            }
        });
    }

    @Override
    public void sendToUpload(List<ProcessedTask> processedTaskList, JSONArray problemsJsonArray, JSONArray siteScoreJsonArray) {
        taskClickerMvpInteractor.prepareAndUpload(processedTaskList, problemsJsonArray, siteScoreJsonArray, new OnDone() {

            @Override
            public void openMainActivity() {
                taskClickerMvpView.openMainActivity();
            }
        });
    }
}
