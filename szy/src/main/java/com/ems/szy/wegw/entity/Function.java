package com.ems.szy.wegw.entity;

import com.ems.szy.wegw.entity.adapter.BigDecimalXmlAdapter;
import com.ems.szy.wegw.entity.adapter.DateXmlAdapter;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.math.BigDecimal;
import java.util.Date;

@XmlRootElement(name = "funcation")
public class Function {
    private String id;
    private String coding;
    private String error;
    private Date sampleTime;
    private BigDecimal value;


    public String getId() {
        return id;
    }

    @XmlAttribute
    public void setId(String id) {
        this.id = id;
    }

    public String getCoding() {
        return coding;
    }
    @XmlAttribute
    public void setCoding(String coding) {
        this.coding = coding;
    }

    public String getError() {
        return error;
    }
    @XmlAttribute
    public void setError(String error) {
        this.error = error;
    }
    @XmlJavaTypeAdapter(DateXmlAdapter.class)
    public Date getSampleTime() {
        return sampleTime;
    }
    @XmlAttribute(name = "sample_time")
    public void setSampleTime(Date sampleTime) {
        this.sampleTime = sampleTime;
    }


    @XmlJavaTypeAdapter(BigDecimalXmlAdapter.class)
    public BigDecimal getValue() {
        return value;
    }

    @XmlValue
    public void setValue(BigDecimal value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Function{" +
                "id='" + id + '\'' +
                ", coding='" + coding + '\'' +
                ", error='" + error + '\'' +
                ", sampleTime='" + sampleTime + '\'' +
                ", value=" + value +
                '}';
    }
}
