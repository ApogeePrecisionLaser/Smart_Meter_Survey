/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mis.tableClasses;

/**
 *
 * @author Com7_2
 */
public class ErrorLogMsg {

    private String wtpName;
    private String name;
    private String key_person_name;
    private String error_name;
    private String remark;
    private String error_status_name;
    private int error_log_msg_id;
    private String message_status;

    public String getMessage_status() {
        return message_status;
    }

    public void setMessage_status(String message_status) {
        this.message_status = message_status;
    }




    public int getError_log_msg_id() {
        return error_log_msg_id;
    }

    public void setError_log_msg_id(int error_log_msg_id) {
        this.error_log_msg_id = error_log_msg_id;
    }

    public String getError_name() {
        return error_name;
    }

    public void setError_name(String error_name) {
        this.error_name = error_name;
    }

    public String getKey_person_name() {
        return key_person_name;
    }

    public void setKey_person_name(String key_person_name) {
        this.key_person_name = key_person_name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getError_status_name() {
        return error_status_name;
    }

    public void setError_status_name(String error_status_name) {
        this.error_status_name = error_status_name;
    }

    public String getWtpName() {
        return wtpName;
    }

    public void setWtpName(String wtpName) {
        this.wtpName = wtpName;
    }






}
