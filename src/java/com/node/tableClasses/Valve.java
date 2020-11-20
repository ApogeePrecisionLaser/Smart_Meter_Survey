/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.node.tableClasses;

/**
 *
 * @author Navitus1
 */
public class Valve {
private int valve_id;
private double diameter;
private String remark;
private String valve_name;

    public double getDiameter() {
        return diameter;
    }

    public void setDiameter(double diameter) {
        this.diameter = diameter;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getValve_id() {
        return valve_id;
    }

    public void setValve_id(int valve_id) {
        this.valve_id = valve_id;
    }

    public String getValve_name() {
        return valve_name;
    }

    public void setValve_name(String valve_name) {
        this.valve_name = valve_name;
    }


}
