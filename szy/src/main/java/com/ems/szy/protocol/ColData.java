package com.ems.szy.protocol;

import java.util.Date;

public class ColData {

    public ColData() {

    }

    public ColData(Date colData, int value,int simulationVoltage,int batteryVoltage) {
        this.colData = colData;
        this.value = value;
        this.simulationVoltage = simulationVoltage;
        this.batteryVoltage = batteryVoltage;
    }

    // 采集时间
    private Date colData;

    // 采集值
    private int value;

    // 模拟I0 电压（单位mV）
    private int simulationVoltage;

    //电池电压（单位mV）
    private int batteryVoltage;


    public int getSimulationVoltage() {
        return simulationVoltage;
    }

    public void setSimulationVoltage(int simulationVoltage) {
        this.simulationVoltage = simulationVoltage;
    }

    public int getBatteryVoltage() {
        return batteryVoltage;
    }

    public void setBatteryVoltage(int batteryVoltage) {
        this.batteryVoltage = batteryVoltage;
    }

    public Date getColData() {
        return colData;
    }

    public void setColData(Date colData) {
        this.colData = colData;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "ColData{" +
                "colData=" + colData +
                ", value=" + value +
                ", simulationVoltage=" + simulationVoltage +
                ", batteryVoltage=" + batteryVoltage +
                '}';
    }
}
