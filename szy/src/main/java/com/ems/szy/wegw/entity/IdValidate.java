package com.ems.szy.wegw.entity;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "id_validate")
public class IdValidate {
    private String operation;

    private String md5;

    private String sequence;

    private String result;


    @Override
    public String toString() {
        return "IdValidate{" +
                "operation='" + operation + '\'' +
                ", md5='" + md5 + '\'' +
                ", sequence='" + sequence + '\'' +
                ", result='" + result + '\'' +
                '}';
    }

    public String getOperation() {
        return operation;
    }

    @XmlAttribute(name = "operation")
    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getMd5() {
        return md5;
    }

    @XmlElement(name = "md5")
    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public String getSequence() {
        return sequence;
    }

    @XmlElement(name = "sequence")
    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    public String getResult() {
        return result;
    }

    @XmlElement(name = "result")
    public void setResult(String result) {
        this.result = result;
    }
}
