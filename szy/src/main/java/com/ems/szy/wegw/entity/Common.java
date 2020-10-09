package com.ems.szy.wegw.entity;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "common")
public class Common {


    private String buildingId;

    private String getewayId;

    private String type;

    public String getBuildingId() {
        return buildingId;
    }
    @XmlElement(name = "building_id")
    public void setBuildingId(String buildingId) {
        this.buildingId = buildingId;
    }

    public String getGetewayId() {
        return getewayId;
    }
    @XmlElement(name = "gateway_id")
    public void setGetewayId(String getewayId) {
        this.getewayId = getewayId;
    }

    public String getType() {
        return type;
    }
    @XmlElement(name = "type")
    public void setType(String type) {
        this.type = type;
    }




    @Override
    public String toString() {
        return "Common{" +
                "buildingId='" + buildingId + '\'' +
                ", getewayId='" + getewayId + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
