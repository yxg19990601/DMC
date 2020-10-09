package com.ems.szy.wegw.entity;


import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "supcon")
public class Supcon {
    private String operation;

    private String type;


    public String getOperation() {
        return operation;
    }

    @XmlAttribute(name = "operation")
    public void setOperation(String operation) {
        this.operation = operation;
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
        return "Supcon{" +
                "operation='" + operation + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
