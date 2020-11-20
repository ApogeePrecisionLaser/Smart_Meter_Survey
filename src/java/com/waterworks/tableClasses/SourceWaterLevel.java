package com.waterworks.tableClasses;

public class SourceWaterLevel {
    private int sourceWaterLevelId,sourceWateId;
    private float level;
    private String sourceWaterName,dateTime,remark;
    private String level_datetime;
    private String latitude;
    private String longitude;
    private int hours;
    private int minutes;

    public int getHours() {
        return hours;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLevel_datetime() {
        return level_datetime;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public void setLevel_datetime(String level_datetime) {
        this.level_datetime = level_datetime;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    /**
     * @return the sourceWaterLevelId
     */
    public int getSourceWaterLevelId() {
        return sourceWaterLevelId;
    }

    /**
     * @param sourceWaterLevelId the sourceWaterLevelId to set
     */
    public void setSourceWaterLevelId(int sourceWaterLevelId) {
        this.sourceWaterLevelId = sourceWaterLevelId;
    }

    /**
     * @return the sourceWateId
     */
    public int getSourceWaterId() {
        return sourceWateId;
    }

    /**
     * @param sourceWateId the sourceWateId to set
     */
    public void setSourceWaterId(int sourceWateId) {
        this.sourceWateId = sourceWateId;
    }

    /**
     * @return the level
     */
    public float getLevel() {
        return level;
    }

    /**
     * @param level the level to set
     */
    public void setLevel(float level) {
        this.level = level;
    }

    /**
     * @return the sourceWateName
     */
    public String getSourceWaterName() {
        return sourceWaterName;
    }

    /**
     * @param sourceWateName the sourceWateName to set
     */
    public void setSourceWaterName(String sourceWateName) {
        this.sourceWaterName = sourceWateName;
    }

    /**
     * @return the dateTime
     */
    public String getDateTime() {
        return dateTime;
    }

    /**
     * @param dateTime the dateTime to set
     */
    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    /**
     * @return the remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * @param remark the remark to set
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    
}
