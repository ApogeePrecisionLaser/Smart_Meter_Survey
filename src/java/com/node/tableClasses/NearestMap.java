/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.node.tableClasses;

/**
 *
 * @author Shubham
 */
public class NearestMap
{
    private int overHeadTankId,cityId,waterTreatmentPlantId,capacityLTR;
    private float capacityHeight,height;
    private String overHeadTankName,waterTreatmentPlantName,cityName,address1,address2,remark,dateTime;
    private String latitude;
    private String longitude;

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public float getCapacityHeight() {
        return capacityHeight;
    }

    public void setCapacityHeight(float capacityHeight) {
        this.capacityHeight = capacityHeight;
    }

    public int getCapacityLTR() {
        return capacityLTR;
    }

    public void setCapacityLTR(int capacityLTR) {
        this.capacityLTR = capacityLTR;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public int getOverHeadTankId() {
        return overHeadTankId;
    }

    public void setOverHeadTankId(int overHeadTankId) {
        this.overHeadTankId = overHeadTankId;
    }

    public String getOverHeadTankName() {
        return overHeadTankName;
    }

    public void setOverHeadTankName(String overHeadTankName) {
        this.overHeadTankName = overHeadTankName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getWaterTreatmentPlantId() {
        return waterTreatmentPlantId;
    }

    public void setWaterTreatmentPlantId(int waterTreatmentPlantId) {
        this.waterTreatmentPlantId = waterTreatmentPlantId;
    }

    public String getWaterTreatmentPlantName() {
        return waterTreatmentPlantName;
    }

    public void setWaterTreatmentPlantName(String waterTreatmentPlantName) {
        this.waterTreatmentPlantName = waterTreatmentPlantName;
    }
    
}
