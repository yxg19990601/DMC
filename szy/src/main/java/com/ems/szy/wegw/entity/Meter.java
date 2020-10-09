package com.ems.szy.wegw.entity;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "meter")
public class Meter {

    private String id;
    private String conn;



    List<Function> list ;


    public String getId() {
        return id;
    }

    @XmlAttribute
    public void setId(String id) {
        this.id = id;
    }

    public String getConn() {
        return conn;
    }

    @XmlAttribute
    public void setConn(String conn) {
        this.conn = conn;
    }

    public List<Function> getList() {
        return list;
    }

    @XmlElement(name = "function")
    public void setList(List<Function> list) {
        this.list = list;
    }


    @Override
    public String toString() {
        return "Meter{" +
                "id='" + id + '\'' +
                ", conn='" + conn + '\'' +
                ", list=" + list +
                '}';
    }
}
