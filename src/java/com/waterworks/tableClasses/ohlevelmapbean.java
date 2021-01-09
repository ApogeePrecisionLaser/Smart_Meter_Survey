 
package com.waterworks.tableClasses;

public class ohlevelmapbean {
    private int ohLevelId,overHeadTankId,waterTreatmentPlantId;
    private byte level_a;
     private byte level_b;
    private String waterTreatmentPlantName,overHeadTankName,dateTime,remark;
    private String level_datetime;
    private String latitude;
    private String longitude;
    private int hours;
    private int minutes;
    private String value_of_ab;
    private String value_of_34;
    private String level_of_34;
    private byte step,level1,level2,level3,level4;
    private String command,feedback;
    private int leveldifference;
    private String device_id;
    private String overheadtank_name;
    private int device_junction_map_id;
    
    public int getLeveldifference() {
        return leveldifference;
    }

    public void setLeveldifference(int leveldifference) {
        this.leveldifference = leveldifference;
    }    

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getLevel_of_34() {
        return level_of_34;
    }

    public void setLevel_of_34(String level_of_34) {
        this.level_of_34 = level_of_34;
    }

    public String getValue_of_34() {
        return value_of_34;
    }

    public void setValue_of_34(String value_of_34) {
        this.value_of_34 = value_of_34;
    }

    public byte getLevel1() {
        return level1;
    }

    public void setLevel1(byte level1) {
        this.level1 = level1;
    }

    public byte getLevel2() {
        return level2;
    }

    public void setLevel2(byte level2) {
        this.level2 = level2;
    }

    public byte getLevel3() {
        return level3;
    }

    public void setLevel3(byte level3) {
        this.level3 = level3;
    }

    public byte getLevel4() {
        return level4;
    }

    public void setLevel4(byte level4) {
        this.level4 = level4;
    }

    public byte getStep() {
        return step;
    }

    public void setStep(byte step) {
        this.step = step;
    }


    public String getValue_of_ab() {
        return value_of_ab;
    }

    public void setValue_of_ab(String value_of_ab) {
        this.value_of_ab = value_of_ab;
    }

    public byte getLevel_a() {
        return level_a;
    }

    public void setLevel_a(byte level_a) {
        this.level_a = level_a;
    }

    public byte getLevel_b() {
        return level_b;
    }

    public void setLevel_b(byte level_b) {
        this.level_b = level_b;
    }

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
     * @return the ohLevelId
     */
    public int getOhLevelId() {
        return ohLevelId;
    }

    /**
     * @param ohLevelId the ohLevelId to set
     */
    public void setOhLevelId(int ohLevelId) {
        this.ohLevelId = ohLevelId;
    }

    /**
     * @return the overHeadTankId
     */
    public int getOverHeadTankId() {
        return overHeadTankId;
    }

    /**
     * @param overHeadTankId the overHeadTankId to set
     */
    public void setOverHeadTankId(int overHeadTankId) {
        this.overHeadTankId = overHeadTankId;
    }


    public String getOverHeadTankName() {
        return overHeadTankName;
    }

    /**
     * @param overHeadTankName the overHeadTankName to set
     */
    public void setOverHeadTankName(String overHeadTankName) {
        this.overHeadTankName = overHeadTankName;
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

    /**
     * @return the waterTreatmentPlantId
     */
    public int getWaterTreatmentPlantId() {
        return waterTreatmentPlantId;
    }

    /**
     * @param waterTreatmentPlantId the waterTreatmentPlantId to set
     */
    public void setWaterTreatmentPlantId(int waterTreatmentPlantId) {
        this.waterTreatmentPlantId = waterTreatmentPlantId;
    }

    /**
     * @return the waterTreatmentPlantName
     */
    public String getWaterTreatmentPlantName() {
        return waterTreatmentPlantName;
    }

    /**
     * @param waterTreatmentPlantName the waterTreatmentPlantName to set
     */
    public void setWaterTreatmentPlantName(String waterTreatmentPlantName) {
        this.waterTreatmentPlantName = waterTreatmentPlantName;
    }

    /**
     * @return the device_id
     */
    public String getDevice_id() {
        return device_id;
    }

    /**
     * @param device_id the device_id to set
     */
    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    /**
     * @return the overheadtank_name
     */
    public String getOverheadtank_name() {
        return overheadtank_name;
    }

    /**
     * @param overheadtank_name the overheadtank_name to set
     */
    public void setOverheadtank_name(String overheadtank_name) {
        this.overheadtank_name = overheadtank_name;
    }

    /**
     * @return the device_junction_map_id
     */
    public int getDevice_junction_map_id() {
        return device_junction_map_id;
    }

    /**
     * @param device_junction_map_id the device_junction_map_id to set
     */
    public void setDevice_junction_map_id(int device_junction_map_id) {
        this.device_junction_map_id = device_junction_map_id;
    }

    
}
