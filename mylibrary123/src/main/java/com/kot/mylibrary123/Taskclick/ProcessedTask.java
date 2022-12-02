package com.kot.mylibrary123.Taskclick;

public class ProcessedTask {
    private final String path;
    private final String clickedAt;
    private final String lat;
    private final String lng;
    private final String site_code;
    private final String campaign_id;
    private final String task_time_stamp;
    private final String site_id;
    private final String finalSensorData;
    private final String monitor_type;
    private final String campaign_name;
    private final String format;

    public ProcessedTask(String path, String clickedAt, String format, String lat,
                         String lng, String site_code, String campaign_id, String task_time_stamp,
                         String site_id, String finalSensorData, String monitor_type, String campaign_name) {

        this.path = path;
        this.clickedAt = clickedAt;
        this.format = format;
        this.lat = lat;
        this.lng = lng;
        this.site_code = site_code;
        this.campaign_id = campaign_id;
        this.task_time_stamp = task_time_stamp;
        this.site_id = site_id;
        this.finalSensorData = finalSensorData;
        this.monitor_type = monitor_type;
        this.campaign_name = campaign_name;
    }

    public String getPath() {
        return path;
    }

    public String getClickedAt() {
        return clickedAt;
    }

    public String getFormat() {
        return format;
    }

    public String getLat() {
        return lat;
    }

    public String getLng() {
        return lng;
    }

    public String getSite_code() {
        return site_code;
    }

    public String getCampaign_id() {
        return campaign_id;
    }

    public String getTask_time_stamp() {
        return task_time_stamp;
    }

    public String getSite_id() {
        return site_id;
    }

    public String getFinalSensorData() {
        return finalSensorData;
    }

    public String getMonitor_type() {
        return monitor_type;
    }

    public String getCampaign_name() {
        return campaign_name;
    }
}
