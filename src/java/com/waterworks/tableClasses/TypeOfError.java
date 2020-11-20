/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.waterworks.tableClasses;

/**
 *
 * @author Com7_2
 */
public class TypeOfError {

    private String priority_name;
    private String designation;
    private String error_name;
    private String remark;
    private String value;
    private String errorType_id;
    private int priority_id;
    private int designation_id;
    int node_id;

    public int getNode_id() {
        return node_id;
    }

    public void setNode_id(int node_id) {
        this.node_id = node_id;
    }



    public int getDesignation_id() {
        return designation_id;
    }

    public void setDesignation_id(int designation_id) {
        this.designation_id = designation_id;
    }

    public String getErrorType_id() {
        return errorType_id;
    }

    public void setErrorType_id(String errorType_id) {
        this.errorType_id = errorType_id;
    }

    public int getPriority_id() {
        return priority_id;
    }

    public void setPriority_id(int priority_id) {
        this.priority_id = priority_id;
    }




    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getError_name() {
        return error_name;
    }

    public void setError_name(String error_name) {
        this.error_name = error_name;
    }

    public String getPriority_name() {
        return priority_name;
    }

    public void setPriority_name(String priority_name) {
        this.priority_name = priority_name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }



}
