package com.ems.szy.wegw.entity;

import com.ems.szy.wegw.entity.adapter.DateXmlAdapter;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Date;

/**
 * ������
 */

@XmlRootElement(name = "heat_beat")
public class HeatBeat {
    private String operation;


    // ��ʽ�� yyyyMMhhHHmmss
    private Date time;


    public String getOperation() {
        return operation;
    }

    @XmlAttribute
    public void setOperation(String operation) {
        this.operation = operation;
    }

    @XmlJavaTypeAdapter(DateXmlAdapter.class)
    public Date getTime() {
        return time;
    }

    @XmlElement
    public void setTime(Date time) {
        this.time = time;
    }
}
